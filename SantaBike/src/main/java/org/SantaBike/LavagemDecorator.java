package org.SantaBike;

class LavagemDecorator extends ManutencaoDecorator {
    public LavagemDecorator(Manutencao manutencao) {
        super(manutencao);
    }

    @Override
    public double custo() {
        return super.custo() + 20;
    }
}