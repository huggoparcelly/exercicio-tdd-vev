import org.junit.Assert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertNull;
import java.util.*;
import org.junit.*;
import org.junit.jupiter.api.*;


public class TestesFuncionais {

    @Test
    //CT-PB-01: criação de pagamento - todos os dados validados
    public void testCriaPagamento() {
        Pagamento pagamento = new Pagamento(100.00, "17/03/2024", "boleto");
        assertEquals("boleto", pagamento.getType());
        assertEquals(100.00, pagamento.getValuePaid());
    }

    @Test
    //CT-PB-02: criação de pagamento - todos os dados validados
    public void testCriaPagamentos() {
        new Boleto(2, "17/03/2024", 100.00);
        Pagamento pagamento1 = new Pagamento(20.00, "17/03/2024", "boleto");
        
        new Boleto(2, "17/03/2024", 200.00);
        Pagamento pagamento2 = new Pagamento(200.00, "17/03/2024", "boleto");
        
        assertEquals("boleto", pagamento1.getType());
        assertEquals(100.00, pagamento1.getValuePaid());
        assertEquals("boleto", pagamento2.getType());
        assertEquals(200.00, pagamento2.getValuePaid());
    }

    @Test
    //CT-PB-03: criação de pagamento - boleto com dado inválido
    public void testCriaBoletoValorVazio() {
        Boleto boleto = new Boleto(2, "17/03/2024", -1);
        assertNotNull(boleto.getTotalPaid());
    }

    @Test
    //CT-PB-04: criação de pagamento - boleto com dado inválido
    public void testCriaBoletoCodeVazio() {
        Boleto boleto = new Boleto(-1, "21/03/2024", 20.00);
        assertEquals(boleto.getBoletoCode(), "Boleto code must be a positive number.");
    }

    @Test
    //CT-PB-05: criação de pagamento - boleto com data inválida
    public void testCriaBoletoDataVazio() {
        Boleto boleto = new Boleto(3, "", 20.00);
        assertNotNull(boleto.getDate());
    }

    @Test
    //CT-PB-06: criação de pagamento - boleto com dados inválidos
    public void testCriaBoletoDadosVazios() {
        Boleto boleto = new Boleto(-2, "", -3);
        assertNotNull(boleto.getBoletoCode());
        assertNotNull(boleto.getDate());
        assertNotNull(boleto.getTotalPaid());
    }

    @Test
    //CT-PB-07: soma dos boletos igual ao valor da fatura
    public void testFaturaPaga() {
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, "21/03/2024", 500.00), new Boleto(2, "21/03/2024", 400.00), new Boleto(3, "21/03/2024", 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-08: Soma dos boletos menor que o valor da fatura
    public void testFaturaNaoPaga() {
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, "21/03/2024", 1500.00), new Boleto(2, "21/03/2024", 4100.00), new Boleto(3, "21/03/2024", 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-09: Soma dos boletos maior que o valor da fatura
    public void testFaturaPagaSomaMenorValorFatura() {
        Fatura fatura = new Fatura("21/03/2024", 500.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, "21/03/2024", 500.00), new Boleto(2, "21/03/2024", 100.00), new Boleto(3, "21/03/2024", 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-10: Boletos e fatura iguais a zero
    public void testFaturaZero() {
        Fatura fatura = new Fatura("21/03/2024", 0.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, "21/03/2024", 0.00), new Boleto(2, "21/03/2024", 0.00), new Boleto(3, "21/03/2024", 0.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

    @Test
    //CT-PB-11: Soma dos boletos igual a zero e valor da fatura maior que zero
    public void testFaturaBoletoZero() {
        Fatura fatura = new Fatura("21/03/2024", 1000.00, "José Reis", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, "21/03/2024", 0.00), new Boleto(2, "21/03/2024", 0.00), new Boleto(3, "21/03/2024", 0.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }
}

