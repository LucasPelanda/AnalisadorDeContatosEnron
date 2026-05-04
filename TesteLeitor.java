import java.util.List;

public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();
        
        String caminhoEmails = "C:\\Users\\davik\\Downloads\\maildir";
        l.preencherGrafo(grafo, caminhoEmails);

        grafo.imprime_adjacencias();
        grafo.caminhosCritico("larry.campbell@enron.com", "james.derrick@enron.com");
        grafo.imprimeCaminhosCritico("larry.campbell@enron.com ", "james.derrick@enron.com");

        List<String> caminho = grafo.buscaProfundidade("larry.campbell@enron.com", "james.derrick@enron.com");
        
        if(caminho.size() > 1){
            System.out.print(caminho.get(0));
            for(int i = 1; i < caminho.size(); i++){
                System.out.print(" -> " + caminho.get(i));
            }
            System.out.println();
        }
        
        System.out.println("N de Arestas: " + Grafo.qtdArestas);
        System.out.println("N de Vertices: " + Grafo.qtdVertices);
    }
}
