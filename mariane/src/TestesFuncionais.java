import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestesFuncionais{
    @Test
    public void testFaturaPaga() {
        Fatura fatura = new Fatura(null, 1500.00, "Cliente", false);
        ProcessadorBoletos processador = new ProcessadorBoletos();

        processador.processarBoletos(fatura, Arrays.asList(new Boleto(1, null, 500.00), new Boleto(2, null, 400.00), new Boleto(3, null, 600.00)));

        assertTrue(fatura.isPaid());
        assertEquals(3, fatura.getPayments().size());
    }

}