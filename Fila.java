public class Fila {
    int inicio;
    int fim;
    String[] fila;

    public Fila() {
        this.fila = new String[100];
        this.inicio = 0;
        this.fim = -1;
    }

    public void enfileirar(String vertice) {
        if (this.fim == this.fila.length - 1) {
            String[] novoArray = new String[this.fila.length * 2];
            System.arraycopy(this.fila, 0, novoArray, 0, this.fila.length);
            this.fila = novoArray;
        }
        this.fim++;
        this.fila[this.fim] = vertice;
    }

    public String desenfileirar() {
        String valor = this.fila[this.inicio];
        this.inicio++;
        return valor;
    }

    public boolean filaVazia() {
        return this.inicio > this.fim;
    }
}