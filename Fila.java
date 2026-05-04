public class Fila {
    Node primeiro;
    Node ultimo;

    public Fila() {
        this.primeiro = null;
        this.ultimo = null;
    }

    public void enfileirar(Node novo) {
        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;
        } else {
            ultimo.proximo = novo;
            ultimo = novo;
        }
    }

    public Node desenfileirar() {
        if (primeiro == null) return null;
        Node temp = primeiro;
        primeiro = primeiro.proximo;
        if (primeiro == null) ultimo = null;
        return temp;
    }

    public boolean filaVazia() {
        return primeiro == null;
    }
}