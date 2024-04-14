package org.SantaBike;

public class manutencaobike {
    public static void main(String[] args) {
        Manutencao manutencao = new ManutencaoBasica();
        manutencao = new LavagemDecorator(manutencao);
        manutencao = new LubrificacaoDecorator(manutencao);
        manutencao = new AjusteMarchasDecorator(manutencao);

      
        System.out.println("A bicicleta foi lavada, lubrificada e teve as marchas ajustadas!! " );
        System.out.println("Custo total foi: " + manutencao.custo());
    }
}