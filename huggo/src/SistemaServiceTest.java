import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaServiceTest {

    @Test
    public void testaListaVoosDisponiveisVazia() {
        List<Voo> listaVooDisponiveis = SistemaService.getVoosDisponiveis();

        assertTrue(listaVooDisponiveis.isEmpty());
    }

    @Test
    public void testaListaVoosDisponiveisPopulada() {
        Voo voo01 = new Voo();
        Voo voo02 = new Voo();
        Voo voo03 = new Voo();
        List<Voo> listaVooDisponiveis = SistemaService.getVoosDisponiveis();
        listaVooDisponiveis.add(voo01);
        listaVooDisponiveis.add(voo02);
        listaVooDisponiveis.add(voo03);

        assertEquals(listaVooDisponiveis.size(), 3);
    }
}