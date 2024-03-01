public class Fatura {
    private String date;
    private double totalFatura;
    private String clientName;

    public Fatura(String date, double totalFatura, String clientName){
        this.date = date;
        this.totalFatura = totalFatura;
        this.clientName = clientName;
    }

    public String getFatura(){
        return clientName;
    }

    public double getTotalFatura(){
        return totalFatura;
    }

 


}
