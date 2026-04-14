import java.util.HashMap;

public class Vertice {
    String email;
    HashMap<String, Integer> adjacentes;
    int grauEntrada;
    int grauSaida;

    public Vertice(String email) {
        this.email = email;
        this.grauEntrada = 0;
        this.grauSaida = 0;
        adjacentes = new HashMap<>();
    }
}
