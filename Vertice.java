import java.util.HashMap;

public class Vertice {
    public String email;
    public HashMap<String, Integer> adjacentes;
    public int grauEntrada;
    public int grauSaida;

    public Vertice(String email) {
        this.email = email;
        this.grauEntrada = 0;
        this.grauSaida = 0;
        adjacentes = new HashMap<>();
    }

    /**
     * Cria um novo adjacente caso não exista ou aumenta o peso do adjacente 
     * e aumenta o grau de saida
     * @param destino email adjacente
     */
    public void adicionarDestinatario(String destino){
        
        if(!adjacentes.containsKey(destino)){
            adjacentes.put(destino, 1);
            grauSaida++;
            return;
        }

        adjacentes.put(destino, adjacentes.get(destino) + 1);
    }

    /**
     * Aumenta o grau de entrada
     */
    public void adicionarRemetente(){
        grauEntrada++;
    }
}
