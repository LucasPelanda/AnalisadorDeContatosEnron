public class Pilha {
    Node primeiro;

    public Pilha(){
        this.primeiro = null;
    }

    public void inserir(Node novo){
        novo.proximo = primeiro;
        primeiro = novo;
    }

    public void remover(){
        if(primeiro == null){
            System.out.println("Pilha vazia");
            return;
        }
        primeiro = primeiro.proximo;
    }

    public Node topo(){
        return primeiro;
    }

    public boolean estaVazia(){
        return primeiro == null;
    }
}