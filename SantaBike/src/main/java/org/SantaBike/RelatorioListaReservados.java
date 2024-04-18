package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class RelatorioListaReservados extends RelatorioDecorator{
    public RelatorioListaReservados(RelatorioInterface relatorio){
        super(relatorio);
    }

    @Override
    public ArrayList<String> lista(){
        ResultSet reservas = DataBase.consultarResulta("SELECT * FROM Vendas");
        ArrayList<String> novaLista = super.lista();
        try {
            novaLista.add("|| Reservas ||");
            while (reservas.next()){ 
                String[] userData = Cliente.puxarCliente(reservas.getString("cpf_cliente")); 
                novaLista.add(String.format("Nome do Cliente: %s | Telefone: %s | ID do Produto: %d | Quantidade: %d",userData[1], userData[2],reservas.getInt("id_produto"),reservas.getInt("quantidade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novaLista;
    }
}