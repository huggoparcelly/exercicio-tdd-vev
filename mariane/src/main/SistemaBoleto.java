package main;

import java.util.ArrayList;
import java.util.List;

public class SistemaBoleto {
    public static void main(String[] args) {
        Fatura fatura = new Fatura("01/03/2024", 1000.00, "Mariane", false);

        List<Boleto> boletos = new ArrayList<>();
        boletos.add(new Boleto(9844, "21/03/2024", 500.00));
        boletos.add(new Boleto(9890, "22/03/2024", 300.00));

        ProcessadorBoletos processador = new ProcessadorBoletos();
        processador.processarBoletos(fatura, boletos);

        if(fatura.isPaid()){
            System.out.println("A fatura foi paga.");
        }else{
            System.out.println("A fatura nao foi paga.");
        }
    }


}
