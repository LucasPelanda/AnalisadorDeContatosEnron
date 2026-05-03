import java.util.List;

public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();

        l.preencherGrafo(grafo);

        grafo.caminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");
        grafo.imprimeCaminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");

        List<String> caminho = grafo.buscaLargura("drew.fossum@enron.com", "lee.huber@enron.com");

        for(String passo : caminho){
            System.out.print(passo + " -> ");
        }
    }
}
