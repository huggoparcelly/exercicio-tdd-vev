import static org.junit.Assert.*;

public class TDD{

    public void testGetBoleto(){
        int boletoCodeExpected = 9876;
        String date = "21/03/2024";
        double totalPaid = 1444.90;


        Boleto boleto = new Boleto(boletoCodeExpected, date, totalPaid);
        

        assertEquals(boletoCodeExpected, boleto.getBoletoCode());
    }

    public void testGetFatura(){
        String date = "21/03/2024";
        double totalFatura = 1500.00;
        String clientNameExpected = "Mariane";

        Fatura fatura = new Fatura(date, totalFatura, clientName);
        assertArrayEquals(clientNameExpected, fatura.getClientName());

    }
   
}
