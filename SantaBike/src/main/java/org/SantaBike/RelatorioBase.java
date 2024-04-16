package org.SantaBike;

import java.util.ArrayList;

class RelatorioBase implements RelatorioInterface {
    @Override
    public ArrayList<String> lista(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Relatório de Serviços Agendados e Produtos Reservados atualmente no sistema.");
        return lista;
    }
    
}