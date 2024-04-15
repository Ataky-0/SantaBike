package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Agendamentos {
    public static String[] puxarAgendamento(int id){
        try {
            ResultSet agendamento = DataBase.consultarResulta(String.format("SELECT * FROM Agendamentos WHERE id = '%d'", id));
            if (agendamento.next()){
                String[] resultado = {
                    String.valueOf(agendamento.getInt("id")), // 0
                    agendamento.getString("cpf_cliente"),
                    String.valueOf(agendamento.getTime("hora")), // 2
                    String.valueOf(agendamento.getDate("data_marcada")),
                    String.valueOf(agendamento.getInt("id_servico")), // 4
                    agendamento.getString("status")};
                return(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean cancelarAgendamento(int id){
        boolean status = false;
        String[] agendamento = puxarAgendamento(id);
        if (agendamento != null){
            if (!agendamento[5].equals("Pendente")){
                System.out.println("Agendamento não pode ser cancelado.");
            } else {
                status = true;
                DataBase.updateDB(String.format("DELETE FROM Agendamentos WHERE id = '%d'",id)); // Remover reserva
            }
        }
        return status;
    }
    public static boolean imprimirAgendados(String cpf_cliente){
        ResultSet agendamentos = DataBase.consultarResulta(String.format("SELECT * FROM Agendamentos WHERE cpf_cliente = '%s'", cpf_cliente));
        boolean hasAgendamentos = false;
        userUtils.clearConsole();
        try {
            while(agendamentos.next()){
                hasAgendamentos = true;
                Estoque.apresentaItem(agendamentos.getInt("id_servico"),1);
                System.out.printf("ID do Agendamento: %d\n",agendamentos.getInt("id"));
                System.out.printf("Data Marcada: %tF\n",agendamentos.getDate("data_marcada"));
                System.out.printf("Hora Marcada: %tT\n",agendamentos.getTime("hora"));
                System.out.printf("Status: %s\n",agendamentos.getString("status"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasAgendamentos;
    }

    public static boolean servicoJaAgendado(String cpf_cliente, int id_servico){
        try {
            ResultSet agendado = DataBase.consultarResulta(String.format("SELECT * FROM Agendamentos WHERE cpf_cliente = '%s' AND id_servico = '%d'",cpf_cliente, id_servico));
            if (agendado.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // !! Gerente Side !! \\

    public static void deletarAgendamento(int id){ // Forçadamente apague agendamento 
        DataBase.updateDB(String.format("DELETE FROM Agendamentos WHERE id = '%d'",id));
    }

    public static boolean iniciarServico(int id){
        String[] agendamento = puxarAgendamento(id);
        boolean status = false;
        if (agendamento == null){
            System.out.println("Serviço não existe.");
        } else if (agendamento[5].equals("Em Andamento")){
            System.out.println("Serviço já em andamento."); // Permitir que gerente retroceda para Em Andamento.
        } else {
            status = true;
            DataBase.updateDB(String.format("UPDATE Agendamentos SET status = 'Em Andamento' WHERE id = '%d'",id));
        }
        return status;
    }

    public static boolean completarServico(int id){
        String[] agendamento = puxarAgendamento(id);
        boolean status = false;
        if (agendamento == null){
            System.out.println("Serviço não existe.");
        } else if (agendamento[5].equals("Completado")){
            System.out.println("Serviço já está completado.");
        } else if (agendamento[5].equals("Em Andamento")) {
            status = true;
            DataBase.updateDB(String.format("UPDATE Agendamentos SET status = 'Completado' WHERE id = '%d'",id));
        } else {
            System.out.println("Serviço deve estar em andamento primeiro. (Inicie-o antes)");
        }
        return status;
    }

    public static boolean imprimirAgendadosGerente(){
        ResultSet agendamentos = DataBase.consultarResulta(String.format("SELECT * FROM Agendamentos"));
        boolean hasAgendamentos = false;
        userUtils.clearConsole();
        try {
            while(agendamentos.next()){
                hasAgendamentos = true;
                String[] Servico = Estoque.puxarItem(agendamentos.getInt("id_servico"));
                System.out.printf("CPF do Cliente: %s\n",agendamentos.getString("cpf_cliente"));
                System.out.printf("Serviço: %s\n",Servico[1]);
                System.out.printf("Preço: %s R$\n\n",Servico[4]);
                //
                System.out.printf("ID do Agendamento: %d\n",agendamentos.getInt("id"));
                System.out.printf("Data Marcada: %tF\n",agendamentos.getDate("data_marcada"));
                System.out.printf("Hora Marcada: %tT\n",agendamentos.getTime("hora"));
                System.out.printf("Status: %s\n",agendamentos.getString("status"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasAgendamentos;
    }
}