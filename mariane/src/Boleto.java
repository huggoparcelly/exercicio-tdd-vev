public class Boleto {
    private int boletoCode;
    private String date;
    private double totalPaid;

    public Boleto(int boledoCode, String date, double totalPaid){
        this.boletoCode = boledoCode;
        this.date = date;
        this.totalPaid = totalPaid;
    }

    public int getBoletoCode(){
        return boletoCode;
    }


}