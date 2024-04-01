import java.util.List;

public class ProcessadorBoletos {


    public void processarBoletos(Fatura fatura, List<Boleto> boletos) {
        double totalPaid = 0;
        
         
        for(Boleto boleto : boletos){
            totalPaid += boleto.getTotalPaid();
        }
 
        if(totalPaid > fatura.getTotalFatura()){
            fatura.isPaid();
        }
 
        for(Boleto boleto : boletos){
            Pagamento payment = new Pagamento(boleto.getTotalPaid(), boleto.getDate(), "BOLETO");
            fatura.addPayment(payment);
        }
    }
 }
 