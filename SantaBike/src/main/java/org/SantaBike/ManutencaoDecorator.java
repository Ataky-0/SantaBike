package org.SantaBike;

abstract class ManutencaoDecorator implements Manutencao {
    protected Manutencao manutencao;

    public ManutencaoDecorator(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

    @Override
    public double custo() {
        return manutencao.custo();
    }
}
