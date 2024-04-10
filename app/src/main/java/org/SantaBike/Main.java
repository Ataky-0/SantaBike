/*
 * This source file was generated by the Gradle 'init' task
 */
package org.SantaBike;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    static Scanner scanner;
    
    public static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static void mainMenu(){
        int escolha;
        boolean sair = false;
        
        while (!sair) {
            String menuString[] = {
                "Registrar",
                "Logar",
                "Sair"
            }; 
            userUtils.drawMenu("===== Bem-vindo ao SantaBike =====", menuString);
            escolha = userUtils.getUserChoice(scanner,1,3);
            
            switch (escolha) {
                case 1:
                    Cliente.registrarClienteMenu(scanner);
                    break;
                case 2:
                    // logar();
                    break;
                case 3:
                    sair = true;
                    System.out.println("Obrigado por usar o SantaBike. Até mais!");
                    break;
            }
        }
        
    }
    public static void main(String[] args) {
        DataBase.getConnection();
        
        scanner = new Scanner(System.in);
        mainMenu();
        scanner.close();
    }
}
