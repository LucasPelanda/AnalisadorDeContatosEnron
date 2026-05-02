public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();

        l.preencherGrafo(grafo);

        grafo.imprime_maiores();

        grafo.caminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");
        grafo.imprimeCaminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");
    }
}
