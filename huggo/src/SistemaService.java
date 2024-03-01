import java.util.ArrayList;
import java.util.List;

public class SistemaService {

    private static List<Voo> voosDisponiveis = new ArrayList<>();

    public static List<Voo> getVoosDisponiveis() {
        return voosDisponiveis;
    }

}
