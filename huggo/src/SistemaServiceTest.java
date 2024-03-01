import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaServiceTest {

    @Test
    public void testaListaVoosDisponiveisVazia() {
        List<Voos> listaVoosDisponiveis = SistemaService.getVoosDisponiveis();

        assertTrue(listaVoosDisponiveis.isEmpty());
    }
}