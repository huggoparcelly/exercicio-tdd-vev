import java.util.ArrayList;
import java.util.List;

public class SistemaService {

    private List<Voo> voosDisponiveis;

    public SistemaService(List<Voo> voosDisponiveis) {
        this.voosDisponiveis = voosDisponiveis;
    }

    public List<Voo> getVoosDisponiveis() {
        return voosDisponiveis;
    }

    public List<Voo> buscarVoosByFiltro(String filtro) {
        return this.getVoosDisponiveis().stream()
                .filter(voo -> voo.detalhesVoo().contains(filtro))
                .toList();
    }
}
