import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaReservaDeVooServiceTest {

    private Voo voo01;
    private Voo voo02;
    private Voo voo03;

    @Before
    public void setup() {
        voo01 = new Voo();
        voo02 = new Voo();
        voo03 = new Voo();
    }

    @Test
    public void testaListaVoosDisponiveisVazia() {
        SistemaService sistemaService = new SistemaService(new ArrayList<>());
        List<Voo> listaVooDisponiveis = sistemaService.getVoosDisponiveis();
        assertTrue(listaVooDisponiveis.isEmpty());
    }

    @Test
    public void testaListaVoosDisponiveisPopulada() {
        List<Voo> voos = List.of(voo01, voo02, voo03);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDispononiveis = sistemaService.getVoosDisponiveis();
        assertEquals(voosDispononiveis.size(), 3);
    }

    @Test
    public void testaConteudoDoRetornoGetVoosDisponiveis() {
        criarVoo(voo01);
        criarVoo(voo02);
        criarVoo(voo03);

        List<Voo> voos = List.of(voo01, voo02, voo03);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDispononiveis = sistemaService.getVoosDisponiveis();

        assertEquals(voosDispononiveis, voos);
        assertTrue(voosDispononiveis.contains(voo02));
        assertTrue(voosDispononiveis.get(0).detalhesVoo().contains("Campina Grande - PB"));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroComListaVazia() {
        String filtro = "Campina Grande - PB";
        SistemaService sistemaService = new SistemaService(new ArrayList<>());
        assertTrue(sistemaService.buscarVoosByFiltro(filtro).isEmpty());
    }

    /* Todo
        busca por origem
        busca por destino
        busca por data
        busca por numero passageiros

    */
    @Test
    public void testaBuscaDeVoosPorFiltroOrigemExistente() {
        String origem = "Campina Grande - PB";
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosByFiltro(origem);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }


    private void criarVoo(Voo voo) {
        voo.setData(LocalDate.now());
        voo.setOrigem("Campina Grande - PB");
        voo.setDestino("Fortaleza - CE");
        voo.setHorarioSaida(LocalTime.now());
        voo.setHorarioChegada(LocalTime.now().plusHours(2L));
        voo.setValor(BigDecimal.valueOf(350.30));
        voo.setAssentosDisponiveis(10);
    }
}