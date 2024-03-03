import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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

    public Reserva reservarVoo(UUID vooId, String nome, Integer quantidadePassageiros, String contato) throws Exception {
        Voo voo = this.buscaVooPorId(vooId);
        if(nome.isBlank() || contato.isBlank()) {
            throw new Exception("Informações pessoais inválidas");
        }
        if(quantidadePassageiros <= 0 || quantidadePassageiros > voo.getAssentosDisponiveis()) {
            throw new Exception("Quantidade de passageiros inválida");
        }
        return new Reserva(nome, quantidadePassageiros, contato, voo);
    }
}
