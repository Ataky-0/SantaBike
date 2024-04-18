package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Estoque {

    public static void eliminarItem(int id){
        ResultSet holder;
        try {
            // Agendado check
            holder = DataBase.consultarResulta(String.format("SELECT * FROM Agendamentos WHERE id_servico = '%d'",id));
            if (holder.next())
                Agendamentos.deletarAgendamento(holder.getInt("id"));
            // Reservado check
            holder = DataBase.consultarResulta(String.format("SELECT * FROM Vendas WHERE id_produto = '%d'",id));
            if (holder.next())
                Vendas.terminarReserva(holder.getInt("id"));
            // Remover enfim item
            DataBase.updateDB(String.format("DELETE FROM Estoque WHERE id = '%d'",id)); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                if (quantidade-qnt_consumir>=0) {
                    DataBase.updateDB(String.format("UPDATE Estoque SET quantidade = '%d' WHERE id = '%d'",quantidade-qnt_consumir,id));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean apresentaItem(int id, int tipo){
        ResultSet produto = DataBase.consultarResulta(String.format("SELECT * FROM Estoque WHERE id = '%d'",id));
        try {
            if (produto.next()) {
                String nome = produto.getString("nome"), descricao = produto.getString("descricao");
                int quantidade = produto.getInt("quantidade"); 
                double preco = produto.getDouble("preço");
                String conteudo = "";
                if (tipo == 0){ // produto
                    if (quantidade<=0) return false; // para evitar que retorne um serviço
                    conteudo = String.format("Nome do Produto: %s\nDescrição: %s\nDisponível: %d\nPreço: %.2f R$\n\n",nome,descricao,quantidade,preco);
                } else if(tipo == 1){ // serviço
                    if (quantidade>-1) return false; // para evitar que retorne um produto
                    conteudo = String.format("Nome do Serviço: %s\nDescrição: %s\nPreço: %.2f R$\n\n",nome,descricao,preco);
                } else { // generalizado
                    String disponibilidade = "";
                    if (quantidade==-1)
                        disponibilidade = "Serviço";
                    else disponibilidade = String.valueOf(quantidade);
                    conteudo = String.format("Nome do Item: %s\nDescrição: %s\nDisponibilidade: %s\nPreço: %.2f R$\n\n",nome,descricao, disponibilidade,preco);
                }
                System.out.printf(conteudo);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String[]> buscarItens(ResultSet estoqueBanco, int indiceInicio, int indiceFim, int target) {
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
                    linha[4] = Double.toString(estoqueBanco.getDouble("preço"))+" R$";
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
    
    public static boolean listarItens(int pagina, int tipo){
        int itemsPorPagina = 8;
        int indiceInicio = (pagina - 1) * itemsPorPagina;
        int indiceFim = pagina * itemsPorPagina - 1;

        ResultSet estoque = DataBase.consultarResulta("SELECT * FROM Estoque ORDER BY id");
        List<String[]> resultados = buscarItens(estoque, indiceInicio, indiceFim, tipo);
        userUtils.clearConsole();
        if (resultados.isEmpty()){
            System.out.println("Página: "+pagina);
            return false;
        }
        String cabecario = "";
        if (tipo == 1){
            cabecario = "ID |       Nome       |     Descrição    | Preço";
        } else
            cabecario = "ID |       Nome       |     Descrição    |     Quantidade   | Preço";
        System.out.println(cabecario);
        for (String[] linha : resultados) {
            for (String coluna : linha){
                if (coluna.length()>18)
                    coluna = String.format("%.15s...",coluna);
                System.out.printf("%.18s | ",coluna);
            }
            System.out.println();
        }
        System.out.println("Página: "+pagina);
        return true;
    }
}