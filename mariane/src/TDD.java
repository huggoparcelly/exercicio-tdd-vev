import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
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
        Pagamento payment = new Pagamento(400.00, "21/03/2024", "BOLETO");
        assertEquals(400.00, payment.getValuePaid(), 0.001);
    }

    @Test
    public void testGetType(){
        Pagamento payment = new Pagamento(400.00, "21/03/2024", "BOLETO");
        assertEquals("boleto", payment.getType());
    }

    @Test
    public void testAddPayment(){
        Fatura fatura = new Fatura("01/03/2024", 1500.00, "Mariane", false);
        Pagamento payment = new Pagamento(400.00, "21/03/2024", "BOLETO");

        fatura.addPayment(payment);
        assertEquals(1, fatura.getPayments().size());
        assertEquals(payment, fatura.getPayments().get(0));

    }

    @Test
    public void testProcessadorBoletos(){
        Fatura fatura = new Fatura("21/03/2024", 3000.00, "Mariane", false);
       
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(new Boleto(9844, "21/03/2024", 444.90));
        boletos.add(new Boleto(9890, "22/03/2024", 1500.00));
 
        ProcessadorBoletos processador = new ProcessadorBoletos();
        processador.processarBoletos(fatura, boletos);
 
        assertEquals(2, fatura.getPayments().size());
    }
}
