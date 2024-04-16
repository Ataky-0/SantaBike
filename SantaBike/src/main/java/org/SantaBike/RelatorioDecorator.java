package org.SantaBike;

import java.util.ArrayList;

public abstract class RelatorioDecorator implements RelatorioInterface {
    protected RelatorioInterface relatorio;

    public RelatorioDecorator(RelatorioInterface relatorio) {
        this.relatorio = relatorio;
    }
    
    @Override
    public ArrayList<String> lista() {
        return relatorio.lista();
    }
}
