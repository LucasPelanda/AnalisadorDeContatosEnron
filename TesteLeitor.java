import java.util.List;

public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();

        l.preencherGrafo(grafo);

        grafo.caminhosCritico("sandra.brawner@enron.com", "jesus.guerra@enron.com");
        grafo.imprimeCaminhosCritico("sandra.brawner@enron.com ", "jesus.guerra@enron.com");

        List<String> caminho = grafo.buscaLargura("sandra.brawner@enron.com", "jesus.guerra@enron.com");

        System.out.print(caminho.get(0));
        for(int i = 1; i < caminho.size(); i++){
            System.out.print(" -> " + caminho.get(i));
        }
        System.out.println();

        System.out.println("N de Arestas: " + Grafo.qtdArestas);
        System.out.println("N de Vertices: " + Grafo.qtdVertices);

    }
}
