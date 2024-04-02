import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SistemaService {

    private List<Voo> voosDisponiveis;

    private List<Reserva> reservas;

    public SistemaService() {
        this.voosDisponiveis = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public List<Voo> getVoosDisponiveis() {
        return voosDisponiveis;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Voo> buscarVoosPorFiltro(String origin, String destiny, LocalDate date, Integer passengers) throws Exception {
        validaOrigemDestino(origin, destiny);
        validaData(date);
        validaPassageiros(passengers);

        return this.getVoosDisponiveis().stream()
                .filter(voo -> voo.getOrigem().equals(origin) && voo.getDestino().equals(destiny) && voo.getData().equals(date) &&
                        (voo.getAssentosDisponiveis() > passengers || voo.getAssentosDisponiveis().equals(passengers) ))
                .toList();
    }

    private static void validaData(LocalDate date) throws Exception {
        validaAno(date);
        if(date.isBefore(LocalDate.now())) {
            throw new Exception("Data anterior a atual");
        }
    }

    private static void validaOrigemDestino(String origin, String destiny) throws Exception {
        if(origin.isBlank() || origin.length() != 3 || destiny.isBlank() || destiny.length() != 3 ) {
            throw new Exception("Informação de origem ou destino inválida");
        }
    }

    private static void validaPassageiros(Integer passengers) throws Exception {
        if(passengers > 10 || passengers < 1) {
            throw new Exception("Número de passageiros inválido");
        }
    }

    private static void validaAno(LocalDate date) throws Exception {
        if(date.getYear() < LocalDate.now().getYear() || date.getYear() > LocalDate.now().getYear() + 1) {
            throw new Exception("Ano informado inválido");
        }
    }

    public Voo buscaVooPorId(UUID id) throws Exception {
         return this.voosDisponiveis.stream()
                 .filter(v -> v.getId().equals(id))
                 .findFirst()
                 .orElseThrow(() -> new Exception("Voo não encontrado"));
    }

    public Reserva reservarVoo(UUID vooId, String nome, String cpf,
                               Integer quantidadePassageiros, String contato) throws Exception {
        Voo voo = this.buscaVooPorId(vooId);
        validaInfoPassageiro(nome, cpf, contato);
        validaAssentos(quantidadePassageiros, voo);

        Reserva reserva = new Reserva(nome, cpf, quantidadePassageiros, contato, voo);
        this.reservas.add(reserva);
        return reserva;
    }

    private static void validaAssentos(Integer quantidadePassageiros, Voo voo) throws Exception {
        if(quantidadePassageiros <= 0 || quantidadePassageiros > voo.getAssentosDisponiveis()) {
            throw new Exception("Quantidade de passageiros inválida");
        }
    }

    private static void validaInfoPassageiro(String nome, String cpf, String contato) throws Exception {
        if(nome.isBlank() || contato.isBlank() || cpf.isBlank() || cpf.length() != 11) {
            throw new Exception("Informações pessoais inválidas");
        }
    }

    public String cancelarReserva(UUID id) throws Exception {
        Reserva reserva = this.buscaReserva(id);
        reservas.remove(reserva);
        return "Reserva cancelada com sucesso";
    }

    public String cancelarReserva(String cpf) throws Exception {
        Reserva reserva = this.buscaReserva(cpf);
        reservas.remove(reserva);
        return "Reserva cancelada com sucesso";
    }

    public String confirmarReservaPorId(UUID idReserva) throws Exception {
        Reserva reserva = buscaReserva(idReserva);
        String detalhesDoVoo = reserva.getVoo().detalhesVoo();
        BigDecimal precoTotal = getPrecoTotal(reserva);
        String informacoesPassageiro = "  Nome: " + reserva.getNome() + "\n" +
                "  CPF: " + reserva.getCpf() + "\n" +
                "  Contato: " + reserva.getContato();

        return detalhesDoVoo + "\n" +
                "Preço Total: R$ " + precoTotal + "\n" +
                "Informações Passageiro: " + "\n" + informacoesPassageiro;
    }

    private BigDecimal getPrecoTotal(Reserva reserva) {
        Integer quantidadePassageiros = reserva.getQuantidadePassageiros();
        BigDecimal valorPassagem = reserva.getVoo().getValor();

        return  BigDecimal.valueOf(quantidadePassageiros).multiply(valorPassagem);
    }

    private Reserva buscaReserva(UUID id) throws Exception {
        return this.reservas.stream()
                .filter(r -> r.getIdReserva().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Reserva não encontrada"));
    }

    private Reserva buscaReserva(String cpf) throws Exception {
        return this.reservas.stream()
                .filter(r -> r.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new Exception("Reserva não encontrada"));
    }
}
