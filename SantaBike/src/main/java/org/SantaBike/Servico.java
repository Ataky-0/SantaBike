package org.SantaBike;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class Servico {

    private static void remarcarAgendamento(Scanner scanner, String cpf_cliente){ //FIX
        int escolha = userUtils.getUserInt("Digite o ID do Agendamento: ", scanner);
        String[] agendamento = Agendamentos.puxarAgendamento(escolha);
        if (agendamento!=null && agendamento[1].equals(cpf_cliente)){
            if (agendamento[5].equals("Pendente")){
                Date data_marcada = userUtils.getUserDateSql("Digite a data para remarcar:\n(DIA/MÊS/ANO): ",scanner);
                Time hora_marcada = userUtils.getUserTimeSql("Digite a hora para remarcar:\n(HH:MM): ",scanner,"07:00","20:30");
                Object[] parametros = { hora_marcada, data_marcada };
                DataBase.updateDB(String.format("UPDATE Agendamentos SET hora = ?, data_marcada = ? WHERE id = '%d'",escolha),parametros);
            } else userUtils.Exception("Só pode remarcar quando o status está pendente.",scanner);
        } else userUtils.Exception("Agendamento inexistente!",scanner);
    }

    private static void cancelarAgendamento(Scanner scanner, String cpf_cliente){
        int escolha = userUtils.getUserInt("Digite o ID do Agendamento: ", scanner);
        String[] agendamento = Agendamentos.puxarAgendamento(escolha);
        if (agendamento!=null && agendamento[1].equals(cpf_cliente)){
            if (!Agendamentos.cancelarAgendamento(escolha))
                userUtils.Exception(scanner);
        } else
            userUtils.Exception("Agendamento inexistente!", scanner);
    }

    private static void listarAgendamentosCliente(Scanner scanner, String cpf_cliente){ // Para cancelar
        int escolha;
        boolean sair = false;
        while(!sair){
            if (Agendamentos.imprimirAgendados(cpf_cliente)){
                userUtils.drawMenu("= Escolha uma ação =", new String[] {"Remarcar Data/Hora","Cancelar um Agendamento","Voltar"}, false);
                escolha = userUtils.getUserChoice(scanner, 1, 3);
                switch (escolha) {
                    case 1:
                        remarcarAgendamento(scanner, cpf_cliente);
                        break;
                    case 2:
                        cancelarAgendamento(scanner, cpf_cliente);                    
                        break;
                    case 3:
                        sair = true;
                        break;
                }
            } else {
                sair = true;
                userUtils.Exception("Não há agendamentos!", scanner);
            }
        }
    }

    private static void agendaServico(Scanner scanner, int id_servico, String cpf_cliente){
        // Impedir quando já houver serviço do mesmo id agendado pro cliente específico
        if (!Agendamentos.servicoJaAgendado(cpf_cliente, id_servico)){
            Date data_marcada = userUtils.getUserDateSql("Digite a data que deseja agendar:\n(DIA/MÊS/ANO): ",scanner);
            Time hora_marcada = userUtils.getUserTimeSql("Digite o horário para este dia:\n(HH:MM): ",scanner,"07:00","20:30");
            Object[] parametros = { cpf_cliente, hora_marcada, data_marcada, id_servico };
            DataBase.updateDB("INSERT INTO Agendamentos (cpf_cliente,hora,data_marcada,id_servico) VALUES (?,?,?,?)",parametros);
        } else {
            userUtils.Exception("Serviço já agendado pelo cliente.", scanner);
        }
    }
    
    private static void servicoMenu(Scanner scanner, int id_servico, String cpf_cliente){
        int escolha;
        String[] menuString = {
            "Agendar",
            "Sair"
        };
        userUtils.drawMenu("Serviço",menuString,false);
        escolha = userUtils.getUserChoice(scanner, 1, 2);
        switch (escolha) {
            case 1:
                agendaServico(scanner, id_servico, cpf_cliente);
                break;
            case 2:
                break;
        }

    }
    private static void operarServico(Scanner scanner, String cpf_cliente){
        boolean sair = false;
        while(!sair){
            int escolha = userUtils.getUserInt("Digite um ID: ", scanner);
            userUtils.clearConsole();
            if (Estoque.apresentaItem(escolha,1)){
                sair = true;
                servicoMenu(scanner, escolha, cpf_cliente);
            }  else {
                System.out.println("Serviço não existe!");
            }
        }
    }
    private static void listarParaCliente(Scanner scanner, String cpf){ // Lista serviços
        int escolha, paginas = 1;
        boolean sair = false;
        while (!sair) {
            Estoque.listarItens(paginas,1);
            userUtils.drawMenu("Páginas", new String[] {"Avançar","Escolher","Retroceder","Sair"}, false);
            escolha = userUtils.getUserChoice(scanner, 1, 4);
            switch (escolha) {
                case 1:
                    paginas++;
                    break;
                case 2:
                    operarServico(scanner, cpf);
                    break;
                case 3:
                    paginas = (paginas-1>0) ? paginas-1: 1;
                    break;
                case 4:
                    sair = true;
                    break;
            }
        }

    }
    public static void drawAgendamentoMenuCliente(Scanner scanner, String cpf){
        int escolha; 
        boolean sair = false;
        String[] menuString = {
            "Listar Serviços",
            "Listar Agendamentos",
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Agendamentos - Cliente ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 3);
            switch (escolha) {
                case 1:
                    listarParaCliente(scanner, cpf);
                    break;
                case 2:
                    listarAgendamentosCliente(scanner, cpf);
                    break;
                case 3:
                    sair = true;  
                    break;
            }
        }
    }

    // !! Gerente Side !! \\

    private static void deletarAgendamento(Scanner scanner){
        int escolha = userUtils.getUserInt("Escolha um serviço para remover: ", scanner);
        if (Agendamentos.puxarAgendamento(escolha)!=null){
            userUtils.drawMenu("Tem certeza?", new String[] {"Sim","Não"}, false);
            int confirmar = userUtils.getUserChoice(scanner, 1, 2);
            if (confirmar==1)
                Agendamentos.deletarAgendamento(escolha);
        } else
            System.out.println("Agendamento inexistente.");
    }

    private static void iniciarServico(Scanner scanner){
        int escolha = userUtils.getUserInt("Escolha um serviço para inicializar: ", scanner);
        if (Agendamentos.puxarAgendamento(escolha)!=null){
            if (!Agendamentos.iniciarServico(escolha))
                userUtils.Exception(scanner);
        } else
            System.out.println("Agendamento inexistente.");
    }

    private static void concluirServico(Scanner scanner){
        int escolha = userUtils.getUserInt("Escolha um serviço para concluir: ", scanner);
        if (Agendamentos.puxarAgendamento(escolha)!=null){
            if (!Agendamentos.completarServico(escolha))
                userUtils.Exception(scanner);
        } else
            System.out.println("Agendamento inexistente.");
    }

    private static void remarcarAgendamentoGerente(Scanner scanner){
        int escolha = userUtils.getUserInt("Digite o ID do Agendamento: ", scanner);
        String[] agendamento = Agendamentos.puxarAgendamento(escolha);
        if (agendamento!=null){
            if (agendamento[5].equals("Pendente")){
                Date data_marcada = userUtils.getUserDateSql("Digite a data para remarcar:\n(DIA/MÊS/ANO): ",scanner);
                Time hora_marcada = userUtils.getUserTimeSql("Digite a hora para remarcar:\n(HH:MM): ",scanner,"07:00","20:30");
                Object[] parametros = { hora_marcada, data_marcada };
                DataBase.updateDB(String.format("UPDATE Agendamentos SET hora = ?, data_marcada = ? WHERE id = '%d'",escolha),parametros);
            } else userUtils.Exception("Só pode remarcar quando o status está pendente.",scanner);
        } else userUtils.Exception("Agendamento inexistente!",scanner);
    }
    private static void listarAgendamentosGerente(Scanner scanner){ // Para cancelar
        int escolha;
        boolean sair = false;
        while(!sair){
            if (Agendamentos.imprimirAgendadosGerente()){
                userUtils.drawMenu("= Escolha uma ação =", new String[] {"Iniciar Serviço (Sobrescreve)","Concluir Serviço","Deletar um Agendamento","Remarcar Data/Hora","Voltar"}, false);
                escolha = userUtils.getUserChoice(scanner, 1, 5);
                switch (escolha) {
                    case 1:
                        iniciarServico(scanner);
                        break;
                    case 2:
                        concluirServico(scanner);
                        break;
                    case 3:
                        deletarAgendamento(scanner);                    
                        break;
                    case 4:
                        remarcarAgendamentoGerente(scanner);
                        break;
                    case 5:
                        sair = true;
                        break;
                }
            } else {
                sair = true;
                userUtils.Exception("Não há agendamentos!", scanner);
            }
        }
    }

    public static void drawAgendamentoMenuGerente(Scanner scanner){
        int escolha; 
        boolean sair = false;
        String[] menuString = {
            "Listar Agendamentos",
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Agendamentos - Gerente ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 2);
            switch (escolha) {
                case 1:
                    listarAgendamentosGerente(scanner);
                    break;
                case 2:
                    sair = true;  
                    break;
            }
        }
    }

}
