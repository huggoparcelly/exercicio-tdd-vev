import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaServiceTest {

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
        assertTrue(sistemaService.buscarVoosPorFiltro(filtro).isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroOrigemExistente() {
        String origem = "Origem: Campina Grande - PB";
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origem);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroOrigemInexistente() {
        String origem = "Origem: Cidade Grande";
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origem);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroDataExistente() {
        String data = "Data: " + LocalDate.now();
        criarVoo(voo01);
        criarVoo(voo02);
        voo02.setData(LocalDate.now().minusDays(2));

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(data);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroDataInexistente() {
        String data = "Data: " + LocalDate.now().minusDays(2);
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(data);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroNumeroAssentosExistente() {
        String numeroAssentos = "Assentos Disponiveis: 10";
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(numeroAssentos);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroNumeroAssentosInexistente() {
        String numeroAssentos = "Assentos Disponiveis: 5";
        criarVoo(voo01);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(numeroAssentos);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaSelecionarVoo() {
        criarVoo(voo01);
        criarVoo(voo02);

        List<Voo> voos = List.of(voo01);
        SistemaService sistemaService = new SistemaService(voos);

        Voo vooRetornado = sistemaService.buscaVooPorId(voo01.getId());
        assertEquals(vooRetornado, voo01);
    }

    @Test
    public void testaSelecionarVooInexistente() {
        SistemaService sistemaService = new SistemaService(new ArrayList<>());

        String message = assertThrows(NoSuchElementException.class, () -> {
            sistemaService.buscaVooPorId(voo01.getId());
        }).getMessage();

        assertEquals("No value present", message);
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