package org.SantaBike;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

// Fortis Animas

public class userUtils {
    public static void Exception(String text, Scanner scanner){
        System.out.printf("%s\nPressione qualquer tecla...",text);
        scanner.nextLine();
    }

    public static void Exception(Scanner scanner){
        System.out.printf("Pressione qualquer tecla...");
        scanner.nextLine();
    }

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

    public static Time getUserTimeSql(String text, Scanner scanner, String horaMin, String horaMax){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Time horaMinima = new Time(0), horaMaxima = new Time(0);
        try {
            horaMinima = new Time(dateFormat.parse(horaMin).getTime());
            horaMaxima = new Time(dateFormat.parse(horaMax).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (text!=null)
                    System.out.print("\n"+text);
                String holder = scanner.nextLine();
                java.util.Date utilDate = dateFormat.parse(holder); // Confirma se está formatado corretamente
                Time hora = new Time(utilDate.getTime());
                if (hora.before(horaMinima) || hora.after(horaMaxima)) {
                    System.out.println("Hora fora do intervalo permitido.");
                } else return hora; // Retorna convertido de volta em java.sql.Time :)
            } catch (ParseException e) {
                System.out.println("Formato de horário inválido. Tente novamente.");
            }
        }
    }

    public static Time getUserTimeSql(String text, Scanner scanner){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        while(true){
            try {
                if (text!=null)
                    System.out.print(text);
                String holder = scanner.nextLine();
                java.util.Date utilDate = dateFormat.parse(holder); // Confirma se está formatado corretamente
                return new Time(utilDate.getTime()); // Retorna convertido de volta em java.sql.Time :)
            } catch (ParseException e) {
                System.out.println("Formato de horário inválido. Tente novamente.");
            }
        }
    }

    public static Date getUserDateSql(String text, Scanner scanner){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendarioAtual = Calendar.getInstance();
        Date dataAtual = new Date(calendarioAtual.getTime().getTime());

        while(true){
            try {
                if (text!=null)
                    System.out.print(text);
                String holder = scanner.nextLine();
                java.util.Date utilDate = dateFormat.parse(holder); // Confirma se está formatado corretamente
                
                Date data = new Date(utilDate.getTime()); // Retorna convertido de volta em java.sql.Date :)
                if (data.before(dataAtual)){
                    System.out.println("Por favor, informe uma data posterior ao presente.");
                } else return data;
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        }
    }

    public static void clearConsole(){
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            System.out.println("Erro ao limpar o console: " + e.getMessage());
        }
    }
    

    public static void drawMenu(String title, String[] opcoesMenu) { // Desenha estrutura menu no console
        clearConsole();
        //
        if (title != null)
            System.out.printf("<-     | %s |     ->\n", title);
        if (opcoesMenu != null) {
            for (int i = 0; i < opcoesMenu.length; i++)
                System.out.println((i + 1) + ". " + opcoesMenu[i]);
            System.out.printf("Escolha uma opção:\n");
        }
    }

    public static void drawMenu(String title, String[] opcoesMenu, boolean Clear) { // Com controle sobre limpar
        if (Clear)
            clearConsole();
        //
        if (title != null)
            System.out.printf("<-\t | %s | \t->\n", title);
        if (opcoesMenu != null) {
            for (int i = 0; i < opcoesMenu.length; i++)
                System.out.println((i + 1) + ". " + opcoesMenu[i]);
            System.out.printf("Escolha uma opção:\n");
        }
    }
}
