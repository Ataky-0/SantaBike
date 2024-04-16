package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Gerente {
    public static void drawGerenteMenu(Scanner scanner){ // Menu para Gerentes.
        int escolha;
        boolean sair = false;
        String[] menuString = {
            "Agendamentos",
            "Reservas",
            "Estoque",
            "Relatórios",
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Menu Administrativo ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 5);
            switch (escolha) {
                case 1:
                    Servico.drawAgendamentoMenuGerente(scanner);
                    break;
                case 2:
                    Reserva.drawReservaMenuGerente(scanner);
                    break;
                case 3:
                    Produto.gerenciarEstoque(scanner);
                    break;
                case 5:
                    sair = true;
                    break;
                default:
                    System.out.println("Não implementado ainda!");
                    break;
            }
        }
    }

    private static Boolean confirmaConta(String Nome, String Senha) {
        Boolean existe = false;

        ResultSet result = DataBase.consultarResulta(String.format("SELECT EXISTS (SELECT 1 FROM Gerente WHERE nome = '%s' AND senha = '%s')",Nome, Senha));
        try {
            if (result.next())
                existe = result.getBoolean(1);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    public static void initLogin(Scanner scanner){ // Menu para logar Gerentes.
        String nome, senha;
        boolean sair = false;        
        while(!sair) {
            nome = userUtils.getUserString("Nome: ", scanner);
            senha = userUtils.getUserString("Senha: ", scanner);
            if (confirmaConta(nome, senha)) {
                System.out.println("Logando como Gerente...");
                drawGerenteMenu(scanner);
                // Leva ao menu principal do Gerente...
                sair = true;
            } else {
                userUtils.clearConsole();
                if (!userUtils.yesOrNo("Nome ou Senha incorreto..\nDeseja tentar novamente?",scanner))
                    sair = true;
            }
        }
    }
}
