import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private String date;
    private double totalFatura;
    private String clientName;
    private boolean paied;
    private List<Pagamento> payments;

    public Fatura(String date, double totalFatura, String clientName, boolean paied){
        this.date = date;
        this.totalFatura = totalFatura;
        this.clientName = clientName;
        this.paied = false;
        this.payments = new ArrayList<>();
        
    }

    public String getFatura(){
        return clientName;
    }

    public double getTotalFatura(){
        return totalFatura;
    }

    public boolean isPaid(){
        return this.paied = true;
    }

    public boolean getFaturaStatus(){
        return paied;
    }

    public void addPayment(Pagamento payment){
        this.payments.add(payment);
    }

    public List<Pagamento> getPayments(){
        return payments;
    }


 


}
