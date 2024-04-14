package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vendas {

    // public static int[] produtosDeCliente(String cpf_cliente){ // retorna o id dos produtos que o cliente ter reservado
    //     int[] prodts = {};
    //     try {
    //         ResultSet produtos = DataBase.consultarResulta(String.format("SELECT * FROM Vendas WHERE cpf_cliente = '%s'",cpf_cliente));
    //         while (produtos.next())
    //             prodts[prodts.length]=produtos.getInt("id_produto");
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return prodts;
    // }
    public static boolean produtoJaReservado(String cpf_cliente, int id_produto){
        try {
            ResultSet reserva = DataBase.consultarResulta(String.format("SELECT * FROM Vendas WHERE cpf_cliente = '%s' AND id_produto = '%d'",cpf_cliente, id_produto));
            if (reserva.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String[] puxarReserva(int id){
        try {
            ResultSet reserva = DataBase.consultarResulta(String.format("SELECT * FROM Vendas WHERE id = '%d'", id));
            if (reserva.next()){
                String[] resultado = {
                    String.valueOf(reserva.getInt("id")),
                    reserva.getString("cpf_cliente"),
                    String.valueOf(reserva.getInt("id_produto")),
                    String.valueOf(reserva.getInt("quantidade"))};
                return(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void revogarReserva(int id){
        String[] reserva = puxarReserva(id);
        String[] produto = Estoque.puxarItem(Integer.parseInt(reserva[2]));
        if (reserva != null){
            // Devolver disponibilidade vv
            DataBase.updateDB(String.format("UPDATE Estoque SET quantidade = '%d' WHERE id = '%d'",Integer.parseInt(produto[3])+Integer.parseInt(reserva[3]),Integer.parseInt(produto[0])));
            DataBase.updateDB(String.format("DELETE FROM Vendas WHERE id = '%d'",id)); // Remover reserva
        }
    }

    public static boolean imprimirReservas(String cpf_cliente){
        ResultSet vendas = DataBase.consultarResulta(String.format("SELECT * FROM Vendas WHERE cpf_cliente = '%s'", cpf_cliente));
        boolean hasVendas = false;
        userUtils.clearConsole();
        try {
            while(vendas.next()){
                hasVendas = true;
                Estoque.apresentaProduto(vendas.getInt("id_produto"));
                System.out.printf("ID da Reserva: %d\n",vendas.getInt("id"));
                System.out.printf("Quantidade Reservado: %d\n",vendas.getInt("quantidade"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasVendas;
    }

    // !! Gerente Side !! \\

    public static void terminarReserva(int id){
        DataBase.updateDB(String.format("DELETE FROM Vendas WHERE id = '%d'",id));
    }

    public static boolean imprimirReservasGerente(){
        ResultSet vendas = DataBase.consultarResulta(String.format("SELECT * FROM Vendas"));
        boolean hasVendas = false;
        userUtils.clearConsole();
        try {
            while(vendas.next()){
                hasVendas = true;
                Estoque.apresentaProduto(vendas.getInt("id_produto"));
                System.out.printf("CPF do Cliente: %s\n",vendas.getString("cpf_cliente"));
                System.out.printf("ID da Reserva: %d\n",vendas.getInt("id"));
                System.out.printf("Quantidade Reservado: %d\n",vendas.getInt("quantidade"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasVendas;
    }
}
