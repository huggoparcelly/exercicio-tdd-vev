import java.util.ArrayList;
import java.util.List;

public class SistemaService {

    private static List<Voos> voosDisponiveis = new ArrayList<>();

    public static List<Voos> getVoosDisponiveis() {
        return voosDisponiveis;
    }

    public static void setVoosDisponiveis(List<Voos> voosDisponiveis) {
        SistemaService.voosDisponiveis = voosDisponiveis;
    }
}
