public class Boleto {
    private int boletoCode;
    private String date;
    private double totalPaid;

    public Boleto(int boletoCode, String date, double totalPaid){
        this.boletoCode = boletoCode;
        this.date = date;
        this.totalPaid = totalPaid;
    }

    public int getBoletoCode(){
        return boletoCode;
    }


}