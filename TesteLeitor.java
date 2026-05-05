public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();
        
        String caminhoEmails = "caminho\\da\\base";
        l.preencherGrafo(grafo, caminhoEmails);

        // grafo.imprime_adjacencias();

        // INFORMACOES GERAIS
        System.out.println("\nINFORMAÇÕES GERAIS");
        System.out.println("N de Arestas: " + Grafo.qtdArestas);
        System.out.println("N de Vertices: " + Grafo.qtdVertices);
        grafo.imprime_maiores();

        // BUSCAS
        System.out.println("\nBUSCA PROFUNDIDADE");
        grafo.imprimeBuscaProfundidade("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        System.out.println("------------------------\n");

        System.out.println("BUSCA LARGURA");
        grafo.imprimeBuscaLargura("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        System.out.println("------------------------\n");

        // CAMINHO CRITICO
        System.out.println("\nCAMINHO CRITICO");
        grafo.caminhosCritico("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        grafo.imprimeCaminhosCritico("rod.hayslett@enron.com", "martin.cuilla@enron.com");
        System.out.println("----------------\n");

        // NOS A DISTANCIA D
        System.out.println("\nNOS DE DISTANCIA");
        grafo.imprimeNosDistanciaD("rod.hayslett@enron.com", 6);
        
    }
}
