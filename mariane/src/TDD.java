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
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "Mariane");
        assertEquals("Mariane", fatura.getFatura());
    }

    @Test
    public void testBoletoPaid(){

        Boleto boleto = new Boleto(9876, "21/03/2024", 1444.90);
        Fatura fatura = new Fatura("21/03/2024", 1500.00, "Mariane");

        assertEquals(1444.90, boleto.getTotalPaid());
    }

}
