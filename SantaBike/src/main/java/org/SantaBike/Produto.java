package org.SantaBike;

import java.math.BigDecimal;
import java.util.Scanner;

public class Produto { // Estoque

    public static void alterarPreco(Scanner scanner, int id, String itemInfo){
        userUtils.clearConsole();
        System.out.println("Atual: "+itemInfo);
        BigDecimal newPreco = userUtils.getUserBigDecimal("Digite um novo preço para o item: ", scanner);
        DataBase.updateDB("UPDATE Estoque SET preço = ? WHERE id = ?", new Object[] {newPreco,id});
    }

    public static void alterarQuantidade(Scanner scanner, int id, String itemInfo){
        userUtils.clearConsole();
        System.out.println("Atual: "+itemInfo);
        System.out.println("Note que: valor -1 indica que o item é um serviço | valor 0 indica que está indisponível");
        int newQuantidade = userUtils.getUserInt("Digite uma nova quantidade para o item: ", scanner, -1,1000);
        DataBase.updateDB(String.format("UPDATE Estoque SET quantidade = '%d' WHERE id = '%d'",newQuantidade,id));
    }

    public static void alterarDescricao(Scanner scanner, int id, String itemInfo){
        userUtils.clearConsole();
        System.out.println("Atual: "+itemInfo);
        String newDescricao = userUtils.getUserString("Digite uma nova descrição para o item: ", scanner);
        DataBase.updateDB(String.format("UPDATE Estoque SET descricao = '%s' WHERE id = '%d'",newDescricao,id));
    }

    public static void alterarNome(Scanner scanner, int id, String itemInfo){
        userUtils.clearConsole();
        System.out.println("Atual: "+itemInfo);
        String newNome = userUtils.getUserString("Digite um novo nome para o item: ", scanner);
        DataBase.updateDB(String.format("UPDATE Estoque SET nome = '%s' WHERE id = '%d'",newNome,id));
    }

    public static void apagarItem(Scanner scanner, int id){
        String [] itemData = Estoque.puxarItem(id);
        if (itemData!=null){
            if (userUtils.yesOrNo(String.format("Tem certeza que deseja deletar o Item \"%s\"?",itemData[1]),scanner))
                Estoque.eliminarItem(id);
        }
    }
    public static void editarItem(Scanner scanner, int id){
        boolean sair = false;
        String[] itemData = Estoque.puxarItem(id);
        if (itemData!=null){
            while(!sair){
                Estoque.apresentaItem(id, 2);
                String[] menuString = {
                    String.format("Editar Nome (%s)",itemData[1]),
                    String.format("Editar Descrição (%.25s...)",itemData[2]),
                    String.format("Editar Quantidade (%s)",(itemData[3].equals("-1") ? "Serviço" : itemData[3])),
                    String.format("Editar Preço (%s)",itemData[4]),
                    "Voltar"
                };
                userUtils.drawMenu("=== Editar Item ===", menuString);
                int escolha = userUtils.getUserChoice(scanner,1,5);
                switch (escolha) {
                    case 1:
                        alterarNome(scanner, id, itemData[1]);
                        itemData = Estoque.puxarItem(id);
                        break;
                    case 2:
                        alterarDescricao(scanner, id, itemData[2]);
                        itemData = Estoque.puxarItem(id);
                        break;
                    case 3:
                        alterarQuantidade(scanner, id, itemData[3]);
                        itemData = Estoque.puxarItem(id);
                        break;
                    case 4:
                        alterarPreco(scanner, id, itemData[4]);
                        itemData = Estoque.puxarItem(id);
                        break;
                    case 5:
                        sair = true;
                        break;
                }
            }
        }
    }

    public static void gerenciarItem(Scanner scanner){
        int escolha;
        String[] menuString = {
            "Editar Item",
            "Apagar Item",
            "Voltar"
        };
        int id = userUtils.getUserInt("Digite o ID do item: ", scanner);
        userUtils.clearConsole();
        if (!Estoque.apresentaItem(id, 2)){
            userUtils.Exception("Item inexistente.",scanner);
            return;
        }
        userUtils.drawMenu("=== Gerenciar Item ===", menuString, false);
        escolha = userUtils.getUserChoice(scanner,1,3);
        switch (escolha) {
            case 1:
                editarItem(scanner, id);
                break;
            case 2:
                apagarItem(scanner, id);
                break;
            case 3:
                break;
        }
    }

    public static void adicionarItem(Scanner scanner){ // gerente tem liberdade de fazer o que quiser..
        if (userUtils.yesOrNo(scanner)){
            String nome = userUtils.getUserString("Digite o nome do produto: ", scanner), descricao = userUtils.getUserString("== == == == ==\nDigite uma descrição de até 200 caracteres para este produto:\n-> ", scanner);
            int quantidade = userUtils.getUserInt("== == == == ==\nDigite a quantidade (-1 para serviço, 0 para começar indisponível, >0 para ser um produto)\n-> ", scanner,-1,0);
            BigDecimal preco = userUtils.getUserBigDecimal("Digite o preço: ", scanner); //descobri recentemente que o DECIMAL do sql representa um BidDecimal
            DataBase.updateDB("INSERT INTO Estoque (nome,descricao,quantidade,preço) VALUES (?,?,?,?)", new Object[] {nome,descricao,quantidade,preco});
            // userUtils.clearConsole();
            userUtils.Exception("Item criado!", scanner);
        }
    }

    public static void gerenciarEstoque(Scanner scanner){
        boolean sair = false;
        int escolha, paginas = 1;
        while (!sair) {
            if(!Estoque.listarItens(paginas,2))
                System.out.println("Não há mais itens para mostrar..."); 
            userUtils.drawMenu("Páginas", new String[] {"Avançar","Retroceder","Escolher Item", "Adicionar Item","Voltar"}, false);
            escolha = userUtils.getUserChoice(scanner, 1, 5);
            switch (escolha) {
                case 1:
                    paginas++;
                    break;
                case 2:
                    paginas = (paginas-1>0) ? paginas-1: 1;
                    break;
                case 3:
                    gerenciarItem(scanner);
                    break;
                case 4:
                    adicionarItem(scanner);
                    break;
                case 5:
                    sair = true;
                    break;
            }
        }
    }
}
