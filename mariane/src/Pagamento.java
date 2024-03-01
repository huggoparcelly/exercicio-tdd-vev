public class Pagamento {
    private double valuePaid;
    private String date;
    private String type;

    public Pagamento(double valuePaid, String date, String type){
        this.valuePaid = valuePaid;
        this.date = date;
        this.type = "boleto";
    }

    public double getValuePaid(){
        return valuePaid;
    }

    public String getType(){
        return type;
    }

}
