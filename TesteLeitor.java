public class TesteLeitor {
    public static void main(String[] args){
        Leitor l = new Leitor();
        Grafo grafo = new Grafo();

        l.preencherGrafo(grafo);

        //grafo.imprime_maiores();
        
        ///grafo.adicionarMensagem("A", "B");
        ///grafo.adicionarMensagem("B", "C");
        ///grafo.adicionarMensagem("B", "D");
        ///grafo.adicionarMensagem("C", "E");
        ///grafo.adicionarMensagem("D", "F");

        ///grafo.imprimeNosDistanciaD("A", 3);

        grafo.caminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");
        grafo.imprimeCaminhosCritico("drew.fossum@enron.com", "lee.huber@enron.com");
        grafo.imprimeNosDistanciaD("drew.fossum@enron.com", 6);
    }
}
