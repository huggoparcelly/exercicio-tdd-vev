import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaServiceFuncionalTest {

    private Voo flight01;
    private Voo flight02;

    private SistemaService sistemaService;

    private final String NAME = "João da Silva";
    private final Integer PASSENGERS_QUANTITY = 2;
    private final String CONTACT = "+55123456789";

    private final String CPF = "111.111.111-11";

    @Before
    public void setup() {
         this.sistemaService = new SistemaService();
        this.flight01 = new Voo();
        this.flight02 = new Voo();
    }

    @Test
    public void testSearchFlies_whenInformationsAreCorrect() {
        List<Voo> expectedAvailableFlights = List.of(flight01);
        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 2;


        List<Voo> availableFlights = sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        assertEquals(expectedAvailableFlights, availableFlights);

    }

    @Test
    public void testSearchFlies_whenNumberOfPassengersIsLessThan1() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 0;

        String expectedMessage = "Numero de passageiros menor que 1";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenNumberOfPassengersIsGreatThan10() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = flight01.getData();
        Integer passengers = 20;

        String expectedMessage = "Numero de passageiros maior que 10";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenMonthValueIsLessThan0() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/0/2024");
        Integer passengers = 2;

        String expectedMessage = "Mês não está entre 1..12";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenMonthValueIsGreatThan12() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/13/2024");
        Integer passengers = 2;

        String expectedMessage = "Mês não está entre 1..12";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenYearValueIsBeforeActualYear() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/1904");
        Integer passengers = 2;

        String expectedMessage = "Ano é anterior ao ano atual";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenYearValueIsAfterActualYearPlusOneYear() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/2026");
        Integer passengers = 2;

        String expectedMessage = "Ano é maior que o ano atual + 1 ano";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenDayValueIsLessThan1() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("0/03/2024");
        Integer passengers = 2;

        String expectedMessage = "O dia não está entre 1..31";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenDayValueIsLessThan1() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("32/03/2024");
        Integer passengers = 2;

        String expectedMessage = "O dia não está entre 1..31";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenOriginIsWrong() {

        criarVoo(flight01);
        String origin = "AAAA";
        String destiny = flight01.getDestino();
        LocalDate date = LocalDate.parse("14/03/2024");
        Integer passengers = 2;

        String expectedMessage = "Origem inválida";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }

    @Test
    public void testSearchFlies_whenOriginIsWrong() {

        criarVoo(flight01);
        String origin = flight01.getOrigem();
        String destiny = "AAAA";
        LocalDate date = LocalDate.parse("14/03/2024");
        Integer passengers = 2;

        String expectedMessage = "Destino inválido";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.buscarVoosPorFiltro(origin, destiny, date, passengers);
        }).getMessage();

        assertEquals(expectedMessage , message);
    }


    @Test
    public void testBookFlight_whenInformationAreCorrect() throws Exception {

        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        Reserva expectedReserve = criarReserva(flight01);

        Reserva reserve = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);

        assertEquals(reserve.getNome(), expectedReserve.getNome());
        assertEquals(reserve.getQuantidadePassageiros(), expectedReserve.getQuantidadePassageiros());
        assertEquals(reserve.getContato(), expectedReserve.getContato());
    }

    @Test
    public void testBookFlight_whenWrongName() {

        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String emptyName = "";

        String expectedMessage = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), emptyName, CPF, PASSENGERS_QUANTITY, CONTACT);
        }).getMessage();

        assertEquals(expectedMessage, message);

    }

    @Test
    public void testBookFlight_whenNumberPassengersIsLessThan1() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Integer quantidadePassageiros = 0;

        // Test
        String expectedMessage = "Quantidade de passageiros inválida";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    quantidadePassageiros, CONTACT);
        }).getMessage();

        assertEquals(expectedMessage, message);
    }

    @Test
    public void testBookFlight_whenNumberPassengersIsGreatThan9() throws Exception {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Integer passengersQuantity = 10;

        // Test
        String expectedMessage = "Quantidade de passageiros inválida";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    passengersQuantity, CONTACT);
        }).getMessage();

        assertEquals(expectedMessage, message);

    }

    @Test
    public void testBookFlight_whenContactValueIsWrong() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String contact = "";

        // Test
        String expectedMessage = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, CPF,
                    PASSENGERS_QUANTITY, contact);
        }).getMessage();

        assertEquals(expectedMessage, message);

    }

    @Test
    public void testBookFlight_whenCPFValueIsWrong() {
        // Mocks
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        String cpf = "123";

        // Test
        String expectedMessage = "Informações pessoais inválidas";
        String message = assertThrows(Exception.class, () -> {
            sistemaService.reservarVoo(flight01.getId(), NAME, cpf,
                    PASSENGERS_QUANTITY, CONTACT);
        }).getMessage();

        assertEquals(expectedMessage, message);

    }

    @Test
    public void testFlightReservationCancellation_byId() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        //Test
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
    public void testFlightReservationCancellation_byWrongId() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        //Test
        String cpfNonexistent = "222.222.222.-22";
        Reserva reserva = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);
        Reserva nonexistentReserve = new Reserva(NAME, cpfNonexistent, PASSENGERS_QUANTITY, CONTACT, flight02);

        String expectedMessage = "Reserva inexistente";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(nonexistentReserve.getIdReserva());
        }).getMessage();
        assertEquals(expectedMessage, message);

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(nonexistentReserve));
        assertFalse(reserves.isEmpty());
        assertTrue(reserves.contains(reserva));
    }

    @Test
    public void testFlightReservationCancellation_byCpf() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(flight01);
        Reserva reserva = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);

        //Test
        String expectedMessage = "Reserva cancelada com sucesso";
        String message = sistemaService.cancelarReserva(CPF);
        assertEquals(expectedMessage, message);

        List<Reserva> reserves = sistemaService.getReservas();
        assertFalse(reserves.contains(reserva));
        assertTrue(reserves.isEmpty());
    }

    @Test
    public void testFlightReservationCancellation_byWrongCpf() throws Exception {
        // Mock
        SistemaService sistemaService = adicionaVooDisponivel(flight01);

        //Test
        String cpfNonexistent = "123";
        Reserva reserve = sistemaService.reservarVoo(flight01.getId(), NAME, CPF, PASSENGERS_QUANTITY, CONTACT);
        Reserva nonexistentReserve = new Reserva(NAME, cpfNonexistent, PASSENGERS_QUANTITY, CONTACT, flight02);

        String expectedMessage = "CPF inválido";

        String message = assertThrows(Exception.class, () -> {
            sistemaService.cancelarReserva(nonexistentReserve.getCpf());
        }).getMessage();
        assertEquals(expectedMessage, message);

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
