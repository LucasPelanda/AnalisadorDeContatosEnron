public class Fila {
    int inicio;
    int fim;
    int[] fila;

    public Fila(int quantidade) {
        this.fila = new int[quantidade];
        this.inicio = 0;
        this.fim = -1;
    }

    public void enfileirar(int vertice) {
        this.fim++;
        this.fila[this.fim] = vertice;
    }

    public int desenfileirar() {
        int valor = this.fila[this.inicio];
        this.inicio++;
        return valor;
    }

    public boolean filaVazia() {
        return this.inicio > this.fim;
    }
}