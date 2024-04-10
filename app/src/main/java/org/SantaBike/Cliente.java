package org.SantaBike;

import java.util.Scanner;

class Cliente {
    private String nome;
    private String rua;
    private int num_casa;
    private String telefone;

    public Cliente(String nome,  String rua, int num_casa, String telefone){
        this.nome = nome;
        this.rua = rua;
        this.num_casa = num_casa;
        this.telefone = telefone;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getrua() {
        return rua;
    }

    public void setrua(String rua) {
        this.rua = rua;
    }

     public int getnum_casa() {
        return num_casa;
    }

    public void setnum_casa(int num_casa) {
        this.num_casa = num_casa;
    }
    

     public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
 
    private static void initRegistrar(Scanner scanner){
        String cpf, nome, telefone, rua, senha;
        int num_casa;

        System.out.print("Nome: ");
        System.out.print("Telefone: \n");
        System.out.print("CPF: \n");
        System.out.print("Rua: \n");
        System.out.print("Número da Casa: \n");
        System.out.print("Senha: \n");
    }

    public static void registrarClienteMenu(Scanner scanner){ // Menu para Clientes.
        int escolha;
        boolean sair = false;
        
        while (!sair) {
            String menuString[] = {
                "Sim, Prosseguir.",
                "Não, Voltar para menu principal."
            }; 
            userUtils.drawMenu("Deseja prosseguir com criação de conta?", menuString);
            escolha = userUtils.getUserChoice(scanner,1,3);
            switch (escolha) {
                case 1:
                    initRegistrar(scanner);                    
                    sair = true;
                    break;
                case 2:
                    sair = true;
                    break;
            }
        }
    }
}
