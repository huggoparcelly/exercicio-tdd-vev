import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestesFuncionais{
    @SuppressWarnings("deprecation")
    @Test
    //CT-PB-01: criação de pagamento - todos os dados validados
    public void testCriaPagamento(){
        Pagamento pagamento = new Pagamento(100.00, "17/03/2024", "boleto");
        assertEquals(pagamento.getType(), "boleto");
        assertEquals(100.00, pagamento.getValuePaid(), 0.00);
    }

    @Test
    //CT-PB-02: criação de pagamento - todos os dados validados
    public void testCriaPagamentos(){
        Boleto boleto1 = new Boleto(2, "17/03/2024", 100.00);
        Pagamento pagamento1 = new Pagamento(20.00, "17/03/2024", "boleto");
        Boleto boleto2 = new Boleto(2, "17/03/2024", 200.00);
        Pagamento pagamento2 = new Pagamento(200.00, "17/03/2024", "boleto");
        assertEquals(pagamento1.getType(), "boleto");
        assertEquals(100.00, pagamento1.getValuePaid(), 0.00);
        assertEquals(pagamento2.getType(), "boleto");
        assertEquals(200.00, pagamento2.getValuePaid(), 0.00);
    }

    @Test
    //CT-PB-03: criação de pagamento - boleto com valor vazio
    public void testCriaBoletoValorVazio(){
        Boleto boleto = new Boleto(2, "17/03/2024", null);     
        assertNotNull(boleto.getTotalPaid());
    }

    @Test
    //CT-PB-04: criação de pagamento - boleto com código vazio
    public void testCriaBoletoCodeVazio(){
        Boleto boleto = new Boleto(null, "17/03/2024", 20.00);     
        assertNotNull(boleto.getBoletoCode());       
    }

    @Test
    //CT-PB-05: criação de pagamento - boleto com data vazia
    public void testCriaBoletoDataVazio(){
        Boleto boleto = new Boleto(3, null, 20.00);     
        assertNotNull(boleto.getDate());       
    }

    @Test
    //CT-PB-06: criação de pagamento - boleto com dados vazios
    public void testCriaBoletoDadosVazios(){
        Boleto boleto = new Boleto(null, null, null);     
        assertNotNull(boleto.getBoletoCode());    
        assertNotNull(boleto.getDate());   
        assertNotNull(boleto.getTotalPaid());    
    }

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