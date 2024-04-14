package org.SantaBike;

class AjusteMarchasDecorator extends ManutencaoDecorator {
    public AjusteMarchasDecorator(Manutencao manutencao) {
        super(manutencao);
    }

    @Override
    public double custo() {
        return super.custo() + 40;
    }
}