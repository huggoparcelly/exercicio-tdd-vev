public class TDD {

    public Boleto testGetBoleto(){
        int boletoCodeExpected = 9876;
        String date = "21/03/2024";
        double totalPaid = 1444.90;


        Boleto boleto = new Boleto(boletoCodeExpected, date, totalPaid);
        

        assertEquals(boletoCodeExpected, boleto.getBoletoCode());
    }
}
