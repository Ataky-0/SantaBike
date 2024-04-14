package org.SantaBike;

class LubrificacaoDecorator extends ManutencaoDecorator {
    public LubrificacaoDecorator(Manutencao manutencao) {
        super(manutencao);
    }

    @Override
    public double custo() {
        return super.custo() + 30;
    }
}