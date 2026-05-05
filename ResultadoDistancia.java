import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultadoDistancia {
    List<String> nos;
    HashMap<String, String> anterior;
    HashMap<String, Integer> distancias;

    ResultadoDistancia() {
        this.nos = new ArrayList<>();
        this.anterior = new HashMap<>();
        this.distancias = new HashMap<>();
    }
}
