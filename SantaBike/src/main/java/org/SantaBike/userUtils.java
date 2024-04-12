package org.SantaBike;

import java.util.Scanner;

// Fortis Animas

public class userUtils {
    public static int getUserChoice(Scanner scanner,int minOpcoes, int numOpcoes) { // Obtém entrada do console, com segurança
        while (true){
            System.out.print("-> ");
            String escolhaString = scanner.nextLine();
            try {
                int escolha = Integer.parseInt(escolhaString);
                if (escolha >= minOpcoes && escolha <= numOpcoes)
                    return escolha;
            } catch (NumberFormatException e){}
            
            System.out.println("Valor inválido.");
        }
    }

    public static String getUserString(String text, Scanner scanner) { // Obtém uma string
        if (text != null)
            System.out.print(text);
        String escolhaString = scanner.nextLine();
        return escolhaString;
    }

    public static int getUserInt(String text, Scanner scanner) { // Obtém um inteiro
        if (text != null)
            System.out.print(text);
        while (true){
            String escolhaString = scanner.nextLine();
            try {
                int escolha = Integer.parseInt(escolhaString); //  Garante que é um inteiro
                return escolha;
            } catch (NumberFormatException e){}
            
            System.out.println("Valor inválido.");
        }
    }

    public static void drawMenu(String title, String[] opcoesMenu) { // Desenha estrutura menu no console
        if (title != null)
            System.out.printf("<-\t | %s | \t->\n", title);
        if (opcoesMenu != null) {
            for (int i = 0; i < opcoesMenu.length; i++)
                System.out.println((i + 1) + ". " + opcoesMenu[i]);
            System.out.printf("Escolha uma opção:\n");
        }
    }
}
