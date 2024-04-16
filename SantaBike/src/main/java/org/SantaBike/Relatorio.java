package org.SantaBike;

import java.util.Scanner;

public class Relatorio {
   public static void apresentarRelatorios(Scanner scanner) {
        RelatorioInterface relatorio = new RelatorioBase();
        relatorio = new RelatorioListaAgendados(relatorio);
        relatorio = new RelatorioListaReservados(relatorio);

        for (String texto : relatorio.lista())
            System.out.println(texto);
        userUtils.Exception(scanner);
   } 
}
