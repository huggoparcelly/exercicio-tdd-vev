import static org.junit.Assert.*;
import org.junit.Test;

public class TDD{
    @Test
    public void testGetBoleto(){
        Boleto boleto = new Boleto(9876, "21/03/2024", 1444.90);
        assertEquals(9876, boleto.getBoletoCode());
    }

    @Test
    public void testGetFatura(){
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "Mariane", true);
        assertEquals("Mariane", fatura.getFatura());
    }

    @Test
    public void testBoletoPaid(){
        Boleto boleto = new Boleto(9876, "21/03/2024", 1444.90);
        assertEquals(1444.90, boleto.getTotalPaid(), 0.001);
    }

    @Test
    public void testGetTotalFatura(){
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "Mariane", true);
        assertEquals(1500.00, fatura.getTotalFatura(), 0.001);
    }

    @Test
    public void testFaturaPaid(){
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "Mariane", false);
        assertEquals(false, fatura.getFaturaStatus());


    }

    @Test
    public void testGetPagamento(){
        Pagamento pagamento = new Pagamento(400.00, "21/03/2024", "boleto");
        assertEquals(400.00, pagamento.getValuePaid(), 0.001);
    }
}
