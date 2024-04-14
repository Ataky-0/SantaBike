package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Estoque {

    public static String[] puxarItem(int id){
        try {
            ResultSet item = DataBase.consultarResulta(String.format("SELECT * FROM Estoque WHERE id = '%d'",id));
            if (item.next()){
                String[] resultado = {
                    String.valueOf(item.getInt("id")),
                    item.getString("nome"),
                    item.getString("descricao"),
                    String.valueOf(item.getInt("quantidade")),
                    String.valueOf(item.getFloat("preço"))};
                return(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean consumirEstoque(int id, int qnt_consumir){
        ResultSet produto = DataBase.consultarResulta(String.format("SELECT * FROM Estoque WHERE id = '%d'",id));
        try {
            if (produto.next()) {
                int quantidade = produto.getInt("quantidade");
                if (quantidade-qnt_consumir>0) {
                    DataBase.updateDB(String.format("UPDATE Estoque SET quantidade = '%d' WHERE id = '%d'",quantidade-qnt_consumir,id));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean apresentaProduto(int id){
        ResultSet produto = DataBase.consultarResulta(String.format("SELECT * FROM Estoque WHERE id = '%d'",id));
        try {
            if (produto.next()) {
                String nome = produto.getString("nome"), descricao = produto.getString("descricao");
                int quantidade = produto.getInt("quantidade"); 
                double preco = produto.getDouble("preço");
                System.out.printf("Nome do Produto: %s\nDescrição: %s\nDisponível: %d\nPreço: %.2f\n\n",nome,descricao,quantidade,preco);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String[]> buscarProduto(ResultSet estoqueBanco, int indiceInicio, int indiceFim, int target) {
        List<String[]> resultados = new ArrayList<>();
        try {
            // target = 0 (produtos apenas), 1 (serviços apenas)
            // Itera sobre os resultados e adiciona cada linha à lista
            int offset = 0;
            while (estoqueBanco.next()) {
                if (target == 0 && estoqueBanco.getInt("quantidade") <= 0) // aproveita pra esconder os indisponíveis
                    continue;
                if (target == 1 && estoqueBanco.getInt("quantidade") > 0)
                    continue;
                if (offset >= indiceInicio && offset <= indiceFim) {
                    String[] linha = new String[5]; // 5 colunas na tabela Estoque
                    linha[0] = Integer.toString(estoqueBanco.getInt("id"));
                    linha[1] = estoqueBanco.getString("nome");
                    linha[2] = estoqueBanco.getString("descricao");
                    linha[3] = Integer.toString(estoqueBanco.getInt("quantidade"));
                    // vv
                    linha[3] = (linha[3].equals("-1")) ? "Serviço" : linha[3]; // operador ternário
                    // ^^ Quantidades "-1" serão tratadas como serviços.
                    linha[4] = Double.toString(estoqueBanco.getDouble("preço"));
                    resultados.add(linha);
                }
                offset++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha os recursos
            try {
                if (estoqueBanco != null) estoqueBanco.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultados;
    }
    
    public static void listarProdutos(int pagina){
        int itemsPorPagina = 8;
        int indiceInicio = (pagina - 1) * itemsPorPagina;
        int indiceFim = pagina * itemsPorPagina - 1;

        ResultSet estoque = DataBase.consultarResulta("SELECT * FROM Estoque ORDER BY id");
        List<String[]> resultados = buscarProduto(estoque, indiceInicio, indiceFim, 0);
        userUtils.clearConsole();
        System.out.println("ID | Nome | Descrição | Qunt. | Preço");
        for (String[] linha : resultados) {
            for (String coluna : linha) {
                System.out.print(coluna + " | \t");
            }
            System.out.println();
        }
        System.out.println("Página: "+pagina);
    }
}