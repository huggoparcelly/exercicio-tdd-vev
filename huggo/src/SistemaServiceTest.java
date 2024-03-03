import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        SistemaService sistemaService = new SistemaService();
        List<Voo> listaVooDisponiveis = sistemaService.getVoosDisponiveis();
        assertTrue(listaVooDisponiveis.isEmpty());
    }

    @Test
    public void testaListaVoosDisponiveisPopulada() {
        List<Voo> voos = List.of(voo01, voo02, voo03);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().addAll(voos);

        List<Voo> voosDispononiveis = sistemaService.getVoosDisponiveis();
        assertEquals(voosDispononiveis.size(), 3);
    }

    @Test
    public void testaConteudoDoRetornoGetVoosDisponiveis() {
        criarVoo(voo01);
        criarVoo(voo02);
        criarVoo(voo03);

        List<Voo> voos = List.of(voo01, voo02, voo03);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().addAll(voos);

        List<Voo> voosDispononiveis = sistemaService.getVoosDisponiveis();

        assertEquals(voosDispononiveis, voos);
        assertTrue(voosDispononiveis.contains(voo02));
        assertTrue(voosDispononiveis.get(0).detalhesVoo().contains("Campina Grande - PB"));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroComListaVazia() {
        String filtro = "Campina Grande - PB";
        SistemaService sistemaService = new SistemaService();
        assertTrue(sistemaService.buscarVoosPorFiltro(filtro).isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroOrigemExistente() {
        String origem = "Origem: Campina Grande - PB";
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

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

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

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

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

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

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(data);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroNumeroAssentosExistente() {
        String numeroAssentos = "Assentos Disponiveis: 10";
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

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

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(numeroAssentos);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaSelecionarVoo() throws Exception {
        criarVoo(voo01);
        criarVoo(voo02);

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        Voo vooRetornado = sistemaService.buscaVooPorId(voo01.getId());
        assertEquals(vooRetornado, voo01);
    }

    @Test
    public void testaSelecionarVooInexistenteSemVoos() {
        SistemaService sistemaService = new SistemaService();

        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscaVooPorId(voo01.getId());
        }).getMessage();

        assertEquals("Voo não encontrado", message);
    }

    @Test
    public void testaSelecionarVooInexistenteComUmVoo() {
        criarVoo(voo01);
        criarVoo(voo02);

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscaVooPorId(voo02.getId());
        }).getMessage();

        assertEquals("Voo não encontrado", message);
    }

    @Test
    public void testaCriacaoDaReserva() throws Exception {
        // Mocks
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "João da Silva";
        Integer quantidadePassageiros = 1;
        String contato = "+55123456789";
        Reserva reservaEsperada = new Reserva(nome, quantidadePassageiros, contato, voo01);

        // Test
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), nome,
                                                quantidadePassageiros, contato);

        assertEquals(reserva.getNome(), reservaEsperada.getNome());
        assertEquals(reserva.getQuantidadePassageiros(), reservaEsperada.getQuantidadePassageiros());
        assertEquals(reserva.getContato(), reservaEsperada.getContato());

    }

    @Test
    public void testaCriacaoDaReservaComNomeInvalido() throws Exception {
        // Mocks
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "";
        Integer quantidadePassageiros = 1;
        String contato = "+55123456789";

        // Test
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), nome,
                    quantidadePassageiros, contato);
        }).getMessage();

        assertEquals("Informações pessoais inválidas", message);

    }

    @Test
    public void testaCriacaoDaReservaComQuantidadePasseigosNegativo() {
        // Mocks
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "João da Silva";
        Integer quantidadePassageiros = -1;
        String contato = "+55123456789";

        // Test
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), nome,
                    quantidadePassageiros, contato);
        }).getMessage();

        assertEquals("Quantidade de passageiros inválida", message);
    }

    @Test
    public void testaCriacaoDaReservaComQuantidadePasseigosMaiorQueCapacidade() throws Exception {
        // Mocks
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "João da Silva";
        Integer quantidadePassageiros = 100;
        String contato = "+55123456789";

        // Test
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), nome,
                    quantidadePassageiros, contato);
        }).getMessage();

        assertEquals("Quantidade de passageiros inválida", message);

    }

    @Test
    public void testaCriacaoDaReservaComContatoInvalido() {
        // Mocks
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "João da Silva";
        Integer quantidadePassageiros = 1;
        String contato = "";

        // Test
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), nome,
                    quantidadePassageiros, contato);
        }).getMessage();

        assertEquals("Informações pessoais inválidas", message);

    }

    @Test
    public void testaCancelamentoDeReservaPorId() throws Exception {
        // Mock
        criarVoo(voo01);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);
        String nome = "João da Silva";
        Integer quantidadePassageiros = 1;
        String contato = "+55123456789";
        sistemaService.reservarVoo(voo01.getId(), nome, quantidadePassageiros, contato);
        String menssagemEsperada = "Reserva cancelado com sucesso";

        //Test
        String menssagem = sistemaService.cancelarReserva(voo01.getId());
        assertEquals(menssagem, menssagemEsperada);
    }

    /*
     TODO
        cancelamento por infoPessoal com lista vazia
        cancelamento por id com lista vazia
        cancelamento por id de reserva, com voo inexistente
        cancelamento por infoPessoal de reserva, com voo inexistente
        cancelamento por id de reserva, com reserva inexistente
        cancelamento por infoPessoal de reserva, com reserva inexistente
        confimação de reserva por id com lista vazia
        confimação de reserva por id da reserva
        confimação de reserva por id inexistente

        Refatorar metodo e testes buscarVoosPorFiltro
     */



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