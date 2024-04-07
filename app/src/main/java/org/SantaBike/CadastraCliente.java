package org.SantaBike;

public class CadastraCliente {
   public void cadastrarCliente(String nome, String endereco, String telefone) {
        Cliente cliente = new Cliente(nome, endereco, telefone);
        System.out.println("Cliente cadastrado com sucesso!");
    }
}
