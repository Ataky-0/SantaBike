package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class RelatorioListaAgendados extends RelatorioDecorator{
    public RelatorioListaAgendados(RelatorioInterface relatorioInterface){
        super(relatorioInterface);
    }

    @Override
    public ArrayList<String> lista(){
        ResultSet agendamentos = DataBase.consultarResulta("SELECT * FROM Agendamentos WHERE status != 'Completado'");
        ArrayList<String> novaLista = super.lista();
        try {
            novaLista.add("|| Agendamentos ||");
            while (agendamentos.next()){
                String[] userData = Cliente.puxarCliente(agendamentos.getString("cpf_cliente")); 
                novaLista.add(String.format("Nome do Cliente: %s | Telefone: %s | Hora: %tT | Data Marcada: %tD | ID do Serviço: %d | Status: %s", userData[1],userData[2],agendamentos.getTime("hora"),agendamentos.getDate("data_marcada"),agendamentos.getInt("id_servico"),agendamentos.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novaLista;
    }
}
