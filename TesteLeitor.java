import java.util.List;

public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();
        
        String caminhoEmails = "C:\\Users\\davik\\Downloads\\maildir";
        l.preencherGrafo(grafo, caminhoEmails);

        // grafo.imprime_adjacencias();

        // INFORMACOES GERAIS
        System.out.println("\nINFORMAÇÕES GERAIS");
        System.out.println("N de Arestas: " + Grafo.qtdArestas);
        System.out.println("N de Vertices: " + Grafo.qtdVertices);
        grafo.imprime_maiores();

        // BUSCAS
        System.out.println("BUSCA PROFUNDIDADE");
        List<String> caminho = grafo.buscaProfundidade("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        if(caminho.size() > 0){
            System.out.print(caminho.get(0));
            for(int i = 1; i < caminho.size(); i++){
                System.out.print(" -> " + caminho.get(i));
            }
            System.out.println("\n------------------------\n");
        }

        System.out.println("BUSCA LARGURA");
        caminho = grafo.buscaLargura("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        if(caminho.size() > 0){
            System.out.print(caminho.get(0));
            for(int i = 1; i < caminho.size(); i++){
                System.out.print(" -> " + caminho.get(i));
            }
            System.out.println("\n------------------------\n");
        }

        // CAMINHO CRITICO
        System.out.println("\nCAMINHO CRITICO");
        grafo.caminhosCritico("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        grafo.imprimeCaminhosCritico("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        System.out.println("----------------\n");

        // NOS A DISTANCIA D
        System.out.println("\nNOS DE DISTANCIA");
        grafo.imprimeNosDistanciaD("rod.hayslett@enron.com", 1);
        
    }
}
