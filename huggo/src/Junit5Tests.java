import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Realizando testes usando features no JUnit5")
public class Junit5Tests {

    private Voo flight01;
    private Voo flight02;

    private SistemaService sistemaService;

    private final String NAME = "João da Silva";
    private final Integer PASSENGERS_QUANTITY = 2;
    private final String CONTACT = "+55123456789";

    private final String CPF = "11111111111";

    @BeforeEach
    public void setup() {
        this.sistemaService = new SistemaService();
        this.flight01 = new Voo();
        this.flight02 = new Voo();
    }


    @Test
    @DisplayName("Testa a busca por voos, com as informações corretas")
    public void testSearchFlies_whenInformationsAreCorrect() throws Exception {
        List<Voo> expectedAvailableFlights = List.of(flight01);
        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 2;

        adicionaVooDisponivel(flight01);

        List<Voo> availableFlights = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertEquals(expectedAvailableFlights, availableFlights);

    }

    @Test
    @DisplayName("Testa a busca por voos, com o número de passageiros menor que 1")
    public void testSearchFlies_whenNumberOfPassengersIsLessThan1() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 0;

        String expectedMessage = "Número de passageiros inválido";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca por voos, com o número de passageiros mair que 10")
    public void testSearchFlies_whenNumberOfPassengersIsGreatThan10() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 20;

        String expectedMessage = "Número de passageiros inválido";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    @DisplayName("Testa a busca por voos, com a data anterior da atual")
    public void testSearchFlies_whenDateBeforeActualDay() {
        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.now().minusDays(1);
        Integer passengers = 2;
        String expectedMessage = "Data anterior a atual";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca por voos, com o ano anterior ao atual")
    public void testSearchFlies_whenYearValueIsBeforeActualYear() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/1904", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer passengers = 2;

        String expectedMessage = "Ano informado inválido";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca por voos, com o ano após o ano atual")
    public void testSearchFlies_whenYearValueIsAfterActualYearPlusOneYear() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/2026", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer passengers = 2;

        String expectedMessage = "Ano informado inválido";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca por voos, com a origem incorreta")
    public void testSearchFlies_whenOriginIsWrong() {

        criarVoo(flight01);
        String origin = "AAAA";
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer passengers = 2;

        String expectedMessage = "Informação de origem ou destino inválida";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }

    @Test
    @DisplayName("Testa a busca por voos, com o destino incorreto")
    public void testSearchFlies_whenDestinyIsWrong() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = "AAAA";
        LocalDate date = LocalDate.parse("14/03/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer passengers = 2;

        String expectedMessage = "Informação de origem ou destino inválida";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        });

        assertEquals(expectedMessage , exception.getMessage());
    }


    @Test
    @DisplayName("Testa a reserva de voos, com as informações corretas")
    public void testBookFlight_whenInformationAreCorrect() throws Exception {

        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        Reserva expectedReserve = criarReserva(flight01);

        Reserva reserve = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);

        assertEquals(reserve.getNome(), expectedReserve.getNome());
        assertEquals(reserve.getQuantidadePassageiros(), expectedReserve.getQuantidadePassageiros());
        assertEquals(reserve.getContato(), expectedReserve.getContato());
    }

    @Test
    @DisplayName("Testa a reserva de voos, com o nome vazio")
    public void testBookFlight_whenWrongName() {

        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String emptyName = "";

        String expectedMessage = "Informações pessoais inválidas";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), emptyName, CPF, PASSENGERS_QUANTITY, CONTACT);
        });

        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    @DisplayName("Testa a reserva de voos, com a quantidade de passageiros menor que 1")
    public void testBookFlight_whenNumberPassengersIsLessThan1() {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Integer quantidadePassageiros = 0;

        String expectedMessage = "Quantidade de passageiros inválida";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    quantidadePassageiros, CONTACT);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testa a reserva de voos, com a quantidade de passageiros maior que 10")
    public void testBookFlight_whenNumberPassengersIsGreatThan10(){
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Integer passengersQuantity = 11;

        String expectedMessage = "Quantidade de passageiros inválida";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    passengersQuantity, CONTACT);
        });

        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    @DisplayName("Testa a reserva de voos, com as informações de contato erradas")
    public void testBookFlight_whenContactValueIsWrong() {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String contact = "";

        String expectedMessage = "Informações pessoais inválidas";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    PASSENGERS_QUANTITY, contact);
        });

        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    @DisplayName("Testa a reserva de voos, com valor do CPF inválido")
    public void testBookFlight_whenCPFValueIsWrong() {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String cpf = "123";

        String expectedMessage = "Informações pessoais inválidas";
        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, cpf,
                    PASSENGERS_QUANTITY, CONTACT);
        });

        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    @DisplayName("Testa o cancelamento da reserva pelo id")
    public void testFlightReservationCancellation_byId() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        Reserva reserve = sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                PASSENGERS_QUANTITY, CONTACT);
        String expectedMessage = "Reserva cancelada com sucesso";

        String message = sistemaService.cancelarReserva(reserve.getIdReserva());
        assertEquals(message, expectedMessage);

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(reserve));
        assertTrue(reserves.isEmpty());
    }

    @Test
    @DisplayName("Testa o cancelamento da reserva com id errado")
    public void testFlightReservationCancellation_byWrongId() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        String cpfNonexistent = "222.222.222.-22";
        Reserva reserva = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);
        Reserva nonexistentReserve = new Reserva(NAME, cpfNonexistent, PASSENGERS_QUANTITY, CONTACT, flight02);

        String expectedMessage = "Reserva não encontrada";

        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(nonexistentReserve.getIdReserva());
        });
        assertEquals(expectedMessage, exception.getMessage());

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(nonexistentReserve));
        assertFalse(reserves.isEmpty());
        assertTrue(reserves.contains(reserva));
    }

    @Test
    @DisplayName("Testa o cancelamento da reserva pelo cpf")
    public void testFlightReservationCancellation_byCpf() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Reserva reserva = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);

        String expectedMessage = "Reserva cancelada com sucesso";
        String message = sistemaService.cancelarReserva(CPF);
        assertEquals(expectedMessage, message);

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(reserva));
        assertTrue(reserves.isEmpty());
    }

    @Test
    @DisplayName("Testa o cancelamento da reserva com cpf errado")
    public void testFlightReservationCancellation_byWrongCpf() throws Exception {
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        String cpfNonexistent = "123";
        Reserva reserve = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);
        Reserva nonexistentReserve = new Reserva(NAME, cpfNonexistent, PASSENGERS_QUANTITY, CONTACT, flight02);

        String expectedMessage = "Reserva não encontrada";

        Exception exception = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(nonexistentReserve.getCpf());
        });
        assertEquals(expectedMessage, exception.getMessage());

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(nonexistentReserve));
        assertFalse(reserves.isEmpty());
        assertTrue(reserves.contains(reserve));
    }

    private SistemaService adicionaVooDisponivel(Voo voo) {
        criarVoo(voo);
        sistemaService.getVoosDisponiveis().add(voo);
        return sistemaService;
    }

    private Reserva criarReserva(Voo voo) {
        return new Reserva(NAME, CPF, PASSENGERS_QUANTITY, CONTACT, voo);
    }

    private void criarVoo(Voo voo) {
        voo.setData(LocalDate.now());
        voo.setOrigem("CPV");
        voo.setDestino("GRU");
        voo.setHorarioSaida(LocalTime.now());
        voo.setHorarioChegada(LocalTime.now().plusHours(2L));
        voo.setValor(BigDecimal.valueOf(350.30));
        voo.setAssentosDisponiveis(10);
    }
}
