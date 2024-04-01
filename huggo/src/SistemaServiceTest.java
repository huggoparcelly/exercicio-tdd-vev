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

    private final String NOME = "João da Silva";
    private final Integer QUANTIDADE_PASSAGEIROS = 1;
    private final String CONTATO = "+55123456789";
    private final String CPF = "111.111.111-11";

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
    public void testaBuscaDeVoosPorFiltroComListaVazia() throws Exception {
        criarVoo(voo01);
        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = LocalDate.parse("14/03/1904");
        Integer passengers = 2;

        SistemaService sistemaService = new SistemaService();
        assertTrue(sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers).isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroOrigemExistente() throws Exception {
        String origem = "Origem: Campina Grande - PB";
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);
        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = LocalDate.parse("14/03/1904");
        Integer passengers = 2;

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroOrigemInexistente() throws Exception {
        String origin = "AAA";
        String destiny = voo01.getDestino();
        LocalDate date = LocalDate.parse("14/03/1904");
        Integer passengers = 2;
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroDataExistente() throws Exception {
        criarVoo(voo01);
        criarVoo(voo02);
        voo02.setData(LocalDate.now().minusDays(2));
        List<Voo> voos = List.of(voo01);
        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = voo01.getData();
        Integer passengers = 2;

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroDataInexistente() throws Exception {
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);

        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = LocalDate.now().minusDays(2);
        Integer passengers = 2;

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaBuscaDeVoosPorFiltroNumeroAssentosExistente() throws Exception {
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);

        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = voo01.getData();
        Integer passengers = 2;

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertEquals(voosDisponiveis, voos);
        assertFalse(voosDisponiveis.isEmpty());
        assertEquals(voosDisponiveis.size(), 1);
        assertTrue(voosDisponiveis.contains(voo01));
        assertFalse(voosDisponiveis.contains(voo02));
    }

    @Test
    public void testaBuscaDeVoosPorFiltroNumeroAssentosInexistente() throws Exception {
        criarVoo(voo01);
        List<Voo> voos = List.of(voo01);

        String origin = voo01.getOrigem();
        String destiny = voo01.getDestino();
        LocalDate date = voo01.getData();
        Integer passengers = 20;

        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo01);

        List<Voo> voosDisponiveis = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertNotEquals(voosDisponiveis, voos);
        assertTrue(voosDisponiveis.isEmpty());
    }

    @Test
    public void testaSelecionarVooDisponivel() throws Exception {

        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        Voo vooRetornado = sistemaService.buscaVooPorId(voo01.getId());
        assertEquals(vooRetornado, voo01);
    }

    @Test
    public void testaSelecionarVooInexistenteSemVoosDisponiveis() {
        SistemaService sistemaService = new SistemaService();

        String menssagemEsperada = "Voo não encontrado";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscaVooPorId(voo01.getId());
        }).getMessage();

        assertEquals(menssagemEsperada, message);
    }

    @Test
    public void testaSelecionarVooInexistenteComUmVooDisponivel() {

        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        String menssagemEsperada = "Voo não encontrado";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscaVooPorId(voo02.getId());
        }).getMessage();

        assertEquals(menssagemEsperada, message);
    }

    @Test
    public void testaCriacaoDaReserva() throws Exception {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        Reserva reservaEsperada = criarReserva(voo01);

        // Test
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF,
                                                QUANTIDADE_PASSAGEIROS, CONTATO);

        assertEquals(reserva.getNome(), reservaEsperada.getNome());
        assertEquals(reserva.getQuantidadePassageiros(), reservaEsperada.getQuantidadePassageiros());
        assertEquals(reserva.getContato(), reservaEsperada.getContato());

    }

    @Test
    public void testaCriacaoDaReservaComNomeInvalido() throws Exception {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        String nomeVazio = "";

        // Test
        String menssagemEsperada = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), nomeVazio, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);
        }).getMessage();

        assertEquals(menssagemEsperada, message);

    }

    @Test
    public void testaCriacaoDaReservaComQuantidadePasseigosNegativo() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        Integer quantidadePassageiros = -1;

        // Test
        String menssagemEsperada = "Quantidade de passageiros inválida";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), NOME, CPF,
                    quantidadePassageiros, CONTATO);
        }).getMessage();

        assertEquals(menssagemEsperada, message);
    }

    @Test
    public void testaCriacaoDaReservaComQuantidadePasseigosMaiorQueCapacidade() throws Exception {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        Integer quantidadePassageiros = 100;

        // Test
        String menssagemEsperada = "Quantidade de passageiros inválida";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), NOME, CPF,
                    quantidadePassageiros, CONTATO);
        }).getMessage();

        assertEquals(menssagemEsperada, message);

    }

    @Test
    public void testaCriacaoDaReservaComContatoInvalido() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        String contato = "";

        // Test
        String menssagemEsperada = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), NOME, CPF,
                    QUANTIDADE_PASSAGEIROS, contato);
        }).getMessage();

        assertEquals(menssagemEsperada, message);

    }

    @Test
    public void testaCriacaoDaReservaComCPFInvalido() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        String cpf = "";

        // Test
        String menssagemEsperada = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(voo01.getId(), NOME, cpf,
                    QUANTIDADE_PASSAGEIROS, CONTATO);
        }).getMessage();

        assertEquals(menssagemEsperada, message);

    }

    @Test
    public void testaCancelamentoDeReservaPorId() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        //Test
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF,
                QUANTIDADE_PASSAGEIROS, CONTATO);
        String menssagemEsperada = "Reserva cancelada com sucesso";

        String menssagem = sistemaService.cancelarReserva(reserva.getIdReserva());
        assertEquals(menssagem, menssagemEsperada);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reserva));
        assertTrue(reservas.isEmpty());
    }

    @Test
    public void testaCancelamentoDeReservaPorIdComListaReservasVazia() {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        //Test
        Reserva reserva = criarReserva(voo01);
        String menssagemEsperada = "Reserva não encontrada";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(reserva.getIdReserva());
        }).getMessage();
        assertEquals(menssagemEsperada, message);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reserva));
        assertTrue(reservas.isEmpty());
    }

    @Test
    public void testaCancelamentoDeReservaPorIdInexistenteComListaReservasPopulada() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        //Test
        String cpfReservaInexistente = "222.222.222.-22";
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);
        Reserva reservaInexistente = new Reserva(NOME, cpfReservaInexistente, QUANTIDADE_PASSAGEIROS, CONTATO, voo02);

        String menssagemEsperada = "Reserva não encontrada";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(reservaInexistente.getIdReserva());
        }).getMessage();
        assertEquals(menssagemEsperada, message);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reservaInexistente));
        assertFalse(reservas.isEmpty());
        assertTrue(reservas.contains(reserva));
    }

    @Test
    public void testaCancelamentoDeReservaPorCpf() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);

        //Test
        String menssagemEsperada = "Reserva cancelada com sucesso";
        String menssagem = sistemaService.cancelarReserva(CPF);
        assertEquals(menssagemEsperada, menssagem);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reserva));
        assertTrue(reservas.isEmpty());
    }

    @Test
    public void testaCancelamentoDeReservaPorCpfComListaReservasVazia() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        //Test
        Reserva reserva = criarReserva(voo01);
        String menssagemEsperada = "Reserva não encontrada";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(CPF);
        }).getMessage();
        assertEquals(menssagemEsperada, message);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reserva));
        assertTrue(reservas.isEmpty());
    }

    @Test
    public void testaCancelamentoDeReservaPorCpfInexistenteComListaReservasPopulada() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        //Test
        String cpfReservaInexistente = "222.222.222.-22";
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);
        Reserva reservaInexistente = new Reserva(NOME, cpfReservaInexistente, QUANTIDADE_PASSAGEIROS, CONTATO, voo02);

        String menssagemEsperada = "Reserva não encontrada";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(reservaInexistente.getCpf());
        }).getMessage();
        assertEquals(menssagemEsperada, message);

        List<Reserva> reservas = sistemaService.getReservas();
        assertFalse(reservas.contains(reservaInexistente));
        assertFalse(reservas.isEmpty());
        assertTrue(reservas.contains(reserva));
    }

    @Test
    public void testaConfirmarReservaSemReservasCadastradas() {
        SistemaService sistemaService = new SistemaService();
        Reserva reserva = criarReserva(voo01);

        String menssagemEsperada = "Reserva não encontrada";

        String menssagem = assertThrows(Exception.class, () -> {
            sistemaService.confirmarReservaPorId(reserva.getIdReserva());
        }).getMessage();

        assertEquals(menssagemEsperada , menssagem);
    }

    @Test
    public void testeConfirmarReservar() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(voo01);
        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);

        String detalhesVooEsperados = reserva.getVoo().detalhesVoo();
        BigDecimal precoTotal = getPrecoTotal(reserva);
        String informacoesPassageiro = "  Nome: " + reserva.getNome() + "\n" +
                "  CPF: " + reserva.getCpf() + "\n" +
                "  Contato: " + reserva.getContato();

        String confirmacaoEsperada = detalhesVooEsperados + "\n" +
                "Preço Total: R$ "+ precoTotal + "\n" +
                "Informações Passageiro: " + "\n" + informacoesPassageiro;

        String confirmacao = sistemaService.confirmarReservaPorId(reserva.getIdReserva());

        assertEquals(confirmacaoEsperada, confirmacao);
    }

    @Test
    public void testeConfirmarReservarComIdInexistente() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(voo01);

        Reserva reserva = sistemaService.reservarVoo(voo01.getId(), NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO);
        Reserva reservaInexistente = criarReserva(voo02);

        String menssagemEsperada = "Reserva não encontrada";
        String menssagem = assertThrows(Exception.class, () -> {
            sistemaService.confirmarReservaPorId(reservaInexistente.getIdReserva());
        }).getMessage();

        assertNotEquals(reservaInexistente, reserva);
        assertEquals(menssagemEsperada , menssagem);
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

    private SistemaService adicionaVooDisponivel(Voo voo) {
        criarVoo(voo);
        SistemaService sistemaService = new SistemaService();
        sistemaService.getVoosDisponiveis().add(voo);
        return sistemaService;
    }

    private Reserva criarReserva(Voo voo) {
        return new Reserva(NOME, CPF, QUANTIDADE_PASSAGEIROS, CONTATO, voo);
    }

    private BigDecimal getPrecoTotal(Reserva reserva) {
        Integer quantidadePassageiros = reserva.getQuantidadePassageiros();
        BigDecimal valorPassagem = reserva.getVoo().getValor();

        return  BigDecimal.valueOf(quantidadePassageiros).multiply(valorPassagem);
    }
}