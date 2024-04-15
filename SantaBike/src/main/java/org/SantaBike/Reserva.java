package org.SantaBike;

import java.util.Scanner;

public class Reserva {
    
    private static void cancelarReserva(Scanner scanner, String cpf_cliente){
        int escolha = userUtils.getUserInt("Digite o ID da reserva: ", scanner);
        String[] reserva = Vendas.puxarReserva(escolha);
        if (reserva!=null && reserva[1].equals(cpf_cliente)){
            Vendas.revogarReserva(escolha);
        } else
            userUtils.Exception("Reserva inexistente!", scanner);
    }

    private static void listarReservasCliente(Scanner scanner, String cpf_cliente){ // Para cancelar
        int escolha;
        boolean sair = false;
        while(!sair){
            if (Vendas.imprimirReservas(cpf_cliente)){
                userUtils.drawMenu("= Escolha uma ação =", new String[] {"Cancelar uma Reserva","Voltar"}, false);
                escolha = userUtils.getUserChoice(scanner, 1, 2);
                switch (escolha) {
                    case 1:
                        cancelarReserva(scanner, cpf_cliente);                    
                        break;
                    case 2:
                        sair = true;
                        break;
                }
            } else {
                sair = true;
                userUtils.Exception("Não há reservas!", scanner);
            }
        }
    }

    private static void reservaProduto(Scanner scanner, int produto_id, String cpf_cliente){
        // Impedir quando já houver produto do mesmo id reservado pro cliente específico
        if (!Vendas.produtoJaReservado(cpf_cliente, produto_id)){
            int quantidade = userUtils.getUserInt("Quantos desse produto deseja reservar? (0 para voltar) ", scanner);
            if (quantidade==0)
                return;
            if (Estoque.consumirEstoque(produto_id, quantidade)){
                System.out.println("Produto reservado.");
                // Adicionar às reservas
                DataBase.updateDB(String.format("INSERT INTO Vendas (cpf_cliente,id_produto,quantidade) VALUES ('%s','%d','%d')",cpf_cliente,produto_id,quantidade));
                // Adicionar situação onde reserva já existe, então a tarefa é incrementer a venda ao invés de criar uma nova
            } else {
                userUtils.Exception("Impossível de realizar essa ação..", scanner);
            }
        } else {
            userUtils.Exception("Produto já reservado pelo cliente.", scanner);
        }
    }

    private static void produtoMenu(Scanner scanner, int produto_id, String cpf_cliente){
        int escolha;
        String[] menuString = {
            "Reservar",
            "Sair"
        };
        userUtils.drawMenu("Produto", menuString,false);
        escolha = userUtils.getUserChoice(scanner, 1, 2);
        switch (escolha) {
            case 1:
                reservaProduto(scanner, produto_id, cpf_cliente);
                break;
            case 2:
                break;
        }

    }

    private static void operarProduto(Scanner scanner, String cpf_cliente){
        boolean sair = false;
        while(!sair){
            int escolha = userUtils.getUserInt("Digite um ID: ", scanner);
            userUtils.clearConsole();
            if (Estoque.apresentaItem(escolha,0)){
                sair = true;
                produtoMenu(scanner, escolha, cpf_cliente);
            }  else {
                System.out.println("Produto não existe!");
            }
        }
    }

    private static void listarParaCliente(Scanner scanner, String cpf){
        int escolha, paginas = 1;
        boolean sair = false;
        while (!sair) {
            Estoque.listarItens(paginas,0);
            userUtils.drawMenu("Páginas", new String[] {"Avançar","Escolher","Retroceder","Sair"}, false);
            escolha = userUtils.getUserChoice(scanner, 1, 4);
            switch (escolha) {
                case 1:
                    paginas++;
                    break;
                case 2:
                    operarProduto(scanner, cpf);
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

    public static void drawReservaMenuCliente(Scanner scanner, String cpf){
        int escolha; 
        boolean sair = false;
        String[] menuString = {
            "Listar produtos",
            "Listar reservas",
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Reservas - Cliente ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 3);
            switch (escolha) {
                case 1:
                    listarParaCliente(scanner, cpf);
                    break;
                case 2:
                    listarReservasCliente(scanner, cpf);
                    break;
                case 3:
                    sair = true;  
                    break;
            }
        }
    }
    
    // !! Gerente Side !! \\

    private static void cancelarReservaGerente(Scanner scanner){
        int escolha = userUtils.getUserInt("Digite o ID da reserva: ", scanner);
        String[] reserva = Vendas.puxarReserva(escolha);
        if (reserva!=null){
            Vendas.revogarReserva(escolha);
        } else
            userUtils.Exception("Reserva inexistente!", scanner);
    }
    private static void confirmarReserva(Scanner scanner){
        int escolha = userUtils.getUserInt("Digite o ID da reserva: ", scanner);
        String[] reserva = Vendas.puxarReserva(escolha);
        if (reserva!=null){
            Vendas.terminarReserva(escolha);
        } else
            userUtils.Exception("Reserva inexistente.", scanner);
    }

    private static void listarReservasGerente(Scanner scanner){ // Para cancelar
        int escolha;
        boolean sair = false;
        while(!sair){
            if (Vendas.imprimirReservasGerente()){
                userUtils.drawMenu("= Escolha uma ação =", new String[] {"Confirmar Coleta de Reserva","Cancelar uma Reserva","Voltar"}, false);
                escolha = userUtils.getUserChoice(scanner, 1, 3);
                switch (escolha) {
                    case 1:
                        confirmarReserva(scanner);
                        break;
                    case 2:
                        cancelarReservaGerente(scanner);                    
                        break;
                    case 3:
                        sair = true;
                        break;
                }
            } else {
                sair = true;
                userUtils.Exception("Não há reservas!", scanner);
            }
        }
    }
    public static void drawReservaMenuGerente(Scanner scanner){
        int escolha; 
        boolean sair = false;
        String[] menuString = {
            "Listar reservas",
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Reservas - Gerente ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 3);
            switch (escolha) {
                case 1:
                    listarReservasGerente(scanner);
                    break;
                case 2:
                    sair = true;  
                    break;
            }
        }
    }
}
