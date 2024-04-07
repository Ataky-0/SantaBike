package org.SantaBike;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream("db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String server = properties.getProperty("db.server");
        String database = properties.getProperty("db.database");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        String url = String.format("jdbc:postgresql://%s/%s",server,database);
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            // Faça operações com o banco de dados aqui
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }
    public DataBase(){
        System.out.println("Teste!!!");
    }
}
