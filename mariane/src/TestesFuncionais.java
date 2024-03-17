import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestesFuncionais{
    @Test
    //CT-PB-07: soma dos boletos igual ao valor da fatura
    public void testFaturaPaga() {
        Fatura fatura = new Fatura(null, 1500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 500.00), new Boleto(2, null, 400.00), new Boleto(3, null, 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }


    @Test
    //CT-PB-08: Soma dos boletos menor que o valor da fatura
    public void testFaturaNaoPaga() {
        Fatura fatura = new Fatura(null, 1500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 1500.00), new Boleto(2, null, 4100.00), new Boleto(3, null, 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-09: Soma dos boletos maior que o valor da fatura
    public void testFaturaPagaSomaMenorValorFatura() {
        Fatura fatura = new Fatura(null, 500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 500.00), new Boleto(2, null, 100.00), new Boleto(3, null, 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-10: Boletos e fatura iguais a zero
    public void testFaturaZero() {
        Fatura fatura = new Fatura(null, 0.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 0.00), new Boleto(2, null, 0.00), new Boleto(3, null, 0.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-11: Soma dos boletos igual a zero e valor da fatura maior que zero
    public void testFaturaBoletoZero() {
        Fatura fatura = new Fatura(null, 1000.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 0.00), new Boleto(2, null, 0.00), new Boleto(3, null, 0.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }









}