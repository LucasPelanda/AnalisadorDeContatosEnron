public class ListaEncadeada {
    public Node head; 

    public ListaEncadeada() {
        this.head = null;
    }

    public void inserir(int destino, int peso) {
        Node novo = new Node(destino, peso);
        novo.proximo = this.head;
        this.head = novo;
    }

    public void remover(int destino) {
        if (this.head == null){ 
            return;
        }
        if (this.head.destino == destino) {
            this.head = this.head.proximo;
            return;
        }

        Node atual = this.head;
        while (atual.proximo != null) {
            if (atual.proximo.destino == destino) {
                atual.proximo = atual.proximo.proximo;
                return;
            }
            atual = atual.proximo;
        }
    }
}
