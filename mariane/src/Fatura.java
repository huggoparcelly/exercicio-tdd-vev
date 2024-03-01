public class Fatura {
    private String date;
    private double totalFatura;
    private String clientName;
    private boolean paied;

    public Fatura(String date, double totalFatura, String clientName, boolean paied){
        this.date = date;
        this.totalFatura = totalFatura;
        this.clientName = clientName;
        this.paied = false;
    }

    public String getFatura(){
        return clientName;
    }

    public double getTotalFatura(){
        return totalFatura;
    }

    public void isPaied(){
        this.paied = true;
    }

    public boolean getFaturaStatus(){
        return paied;
    }


 


}
