public class Node {
    public int destino;  
    public int peso;     
    public Node proximo; 

    public Node(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
        this.proximo = null;
    }
}
