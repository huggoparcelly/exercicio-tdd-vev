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

    public List<Voo> buscarVoosPorFiltro(String filtro) {
        return this.getVoosDisponiveis().stream()
                .filter(voo -> voo.detalhesVoo().contains(filtro))
                .toList();
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
        if(nome.isBlank() || contato.isBlank() || cpf.isBlank()) {
            throw new Exception("Informações pessoais inválidas");
        }
        if(quantidadePassageiros <= 0 || quantidadePassageiros > voo.getAssentosDisponiveis()) {
            throw new Exception("Quantidade de passageiros inválida");
        }
        Reserva reserva = new Reserva(nome, cpf, quantidadePassageiros, contato, voo);
        this.reservas.add(reserva);
        return reserva;
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

        return "Voo: " + detalhesDoVoo + "\n" +
                "Preço Total: R$ \n" +
                "Informações Passageiro: ";
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
