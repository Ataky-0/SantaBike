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
            novaLista.add("|| Agendamentos:");
            while (agendamentos.next()) 
                novaLista.add(String.format("CPF do Cliente: %s | Hora: %tT | Data Marcada: %tD | ID do Serviço: %d | Status: %s", agendamentos.getString("cpf_cliente"),agendamentos.getTime("hora"),agendamentos.getDate("data_marcada"),agendamentos.getInt("id_servico"),agendamentos.getString("status")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novaLista;
    }
}
