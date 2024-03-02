import java.util.List;
import java.util.UUID;

public class SistemaService {

    private List<Voo> voosDisponiveis;

    public SistemaService(List<Voo> voosDisponiveis) {
        this.voosDisponiveis = voosDisponiveis;
    }

    public List<Voo> getVoosDisponiveis() {
        return voosDisponiveis;
    }

    public List<Voo> buscarVoosPorFiltro(String filtro) {
        return this.getVoosDisponiveis().stream()
                .filter(voo -> voo.detalhesVoo().contains(filtro))
                .toList();
    }

    public Voo buscaVooPorId(UUID id) {
        return new Voo();
    }
}
