// DAVI NATUME, PEDRO FAVERO, LUCAS PELANDA, EDUARDO TEODORO 

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Grafo {
    // ATRIBUTOS DO GRAFO
    private HashMap<String, Vertice> tabelaAdjacencias; 
    public Vertice[] maioresSaidas;
    public Vertice[] maioresEntradas;
    public static int qtdArestas = 0;
    public static int qtdVertices = 0;

    // CONSTANTES PARA O MELHOR CAMINHO
    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final double INFINITO = 999999999.0;
    public HashMap<String, String> caminho;

    public Grafo() {
        this.tabelaAdjacencias = new HashMap<>();
        this.maioresEntradas = new Vertice[20];
        this.maioresSaidas = new Vertice[20];
        this.caminho = new HashMap<>();
    }

    public void adicionarMensagem(String origem, String destino) {
        // CRIA VERTICES CASO NAO EXISTAM
        if(!tabelaAdjacencias.containsKey(origem)){
            tabelaAdjacencias.put(origem, new Vertice(origem));
            Grafo.qtdVertices++;
        }
        if(!tabelaAdjacencias.containsKey(destino)){
            tabelaAdjacencias.put(destino, new Vertice(destino));
            Grafo.qtdVertices++;
        }

        tabelaAdjacencias.get(origem).adicionarDestinatario(destino);
        tabelaAdjacencias.get(destino).adicionarRemetente();

        ajustarMaiores(tabelaAdjacencias.get(origem), tabelaAdjacencias.get(destino));
    }

    public void ajustarMaiores(Vertice origem, Vertice destino) {

        // Como vamos usar só um metodo para ajustar tanto saida qunato entrada dei break no for em vez de return 
        boolean achouEntrada = false;
        
        for (int i = 0; i < maioresEntradas.length; i++) {

            //se existe espaço vazio coloca o email lá
            if (maioresEntradas[i] == null) {
                maioresEntradas[i] = destino;
                achouEntrada = true;
                break;
            }

            //se o email já ta na lista não precisa fazer nada
            if (maioresEntradas[i].email.equals(destino.email)) {
                achouEntrada = true;
                break;
            }
        }

        //se o email não ta na lista e tem mais entradas que o ultimo da lista (estamos fazendo a lista sempre ordenada) substitui o ultimo da lista
        if (!achouEntrada && destino.grauEntrada > maioresEntradas[19].grauEntrada) {
            maioresEntradas[19] = destino;
        }

        //ordenação 
        Arrays.sort(maioresEntradas, (a, b) -> {
            if (a == null) return 1;
            if (b == null) return -1;
            return Integer.compare(b.grauEntrada, a.grauEntrada);
        });

        //mesma lógica para saida
        boolean achouSaida = false;
        for (int j = 0; j < maioresSaidas.length; j++) {
            if (maioresSaidas[j] == null) {
                maioresSaidas[j] = origem;
                achouSaida = true;
                break;
            }
            if (maioresSaidas[j].email.equals(origem.email)) {
                achouSaida = true;
                break;
            }
        }
        if (!achouSaida && origem.grauSaida > maioresSaidas[19].grauSaida) {
            maioresSaidas[19] = origem;
        }
        Arrays.sort(maioresSaidas, (a, b) -> {
            if (a == null) return 1;
            if (b == null) return -1;
            return Integer.compare(b.grauSaida, a.grauSaida);
        });
    }

    //imprime a lista de adjacencias do grafo adaptado para Hash 
    public void imprime_adjacencias() {
        for (Map.Entry<String, Vertice> entrada : tabelaAdjacencias.entrySet()) {
            
            Vertice atual = entrada.getValue();

            if(atual.adjacentes.size() > 1){
                System.out.println(entrada.getKey() + " ");
                for(Map.Entry<String, Integer> adjacente : atual.adjacentes.entrySet()) {
                    System.out.print("-> "  + "[" + adjacente.getKey() + "](peso:" + adjacente.getValue() + ") ");
                }
                System.out.println();
            }  
        }
    }

    public void imprime_maiores() {
        System.out.println("\nMaiores Entradas");
        for (int i = 0; i < maioresEntradas.length; i++) {
            if (maioresEntradas[i] != null) {
                System.out.println((i + 1) + ". " + maioresEntradas[i].email + " - Grau de Entrada: " + maioresEntradas[i].grauEntrada);
            }
        }

        System.out.println("\nMaiores Saídas");
        for (int i = 0; i < maioresSaidas.length; i++) {
            if (maioresSaidas[i] != null) {
                System.out.println((i + 1) + ". " + maioresSaidas[i].email + " - Grau de Saída: " + maioresSaidas[i].grauSaida);
            }
        }
    }

    public double caminhosCritico(String s, String t) {
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)) return INFINITO;

        HashMap<String, Double>  distancia = new HashMap<>();
        HashMap<String, Boolean> perm = new HashMap<>();
        String corrente;
        String k = s;
        double dc;
        double menordist;
        double novadist;

        for (String email : tabelaAdjacencias.keySet()) {
            perm.put(email, NAOMEMBRO);
            distancia.put(email, INFINITO);
            caminho.put(email, null);        
        }

        perm.put(s, MEMBRO);
        distancia.put(s, 0.0);
        corrente = s;

        while (!corrente.equals(t)) {
            menordist = INFINITO;
            dc = distancia.get(corrente);

            for (String i : tabelaAdjacencias.keySet()) {
                if (!perm.get(i)) {
                    int pesoOriginal = tabelaAdjacencias.get(corrente).adjacentes.getOrDefault(i, 0);

                    if (dc != INFINITO && pesoOriginal > 0) {
                        novadist = dc + (1.0 / pesoOriginal); // aqui faz o inveros porque tem que pegar o maior peso
                    } else {
                        novadist = INFINITO;
                    }

                    if (novadist < distancia.get(i)) {
                        distancia.put(i, novadist);
                        caminho.put(i, corrente);
                    }
                    if (distancia.get(i) < menordist) {
                        menordist = distancia.get(i);
                        k = i;
                    }
                }
            }

            if (menordist == INFINITO) {
                return INFINITO;
            }

            corrente = k;
            perm.put(corrente, MEMBRO);
        }

        return distancia.get(t);
    }

    public void imprimeCaminhosCritico(String s, String t) {
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)){
            System.out.println("Não presente no grafo: " + s);
            return;
        } 

        if (caminho.get(t) == null && !s.equals(t)) {
            System.out.println("Nao existe caminho entre os vertices informados.");
            return;
        }

        ArrayList<String> nos = new ArrayList<>();
        String i = t;
        while (i != null) {
            nos.add(0, i);
            i = caminho.get(i);
        }

        imprimeCaminhoListaComPesos(nos);
    }

    // iprime caminho com os emails e pesos das arestas
    private void imprimeCaminhoListaComPesos(List<String> nos) {
        int custoAcumulado = 0;
        for (int idx = 0; idx < nos.size(); idx++) {
            System.out.print(nos.get(idx));
            if (idx < nos.size() - 1) {
                int peso = tabelaAdjacencias.get(nos.get(idx)).adjacentes.getOrDefault(nos.get(idx + 1), 0);
                custoAcumulado += peso;
                System.out.print(" -(" + peso + ")-> ");
            }
        }
        System.out.println("\nDependencia acumulada no caminho: " + custoAcumulado);
    }

    public void imprimeBuscaProfundidade(String s, String t) {
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)) {
            System.out.println("Não presente no grafo: " + s);
            return;
        }

        List<String> nos = buscaProfundidade(s, t);
        if (nos.isEmpty()) {
            System.out.println("Nao existe caminho entre os vertices informados.");
            return;
        }

        imprimeCaminhoListaComPesos(nos);
    }

    public void imprimeBuscaLargura(String s, String t) {
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)) {
            System.out.println("Não presente no grafo: " + s);
            return;
        }

        List<String> nos = buscaLargura(s, t);
        if (nos.isEmpty()) {
            System.out.println("Nao existe caminho entre os vertices informados.");
            return;
        }

        imprimeCaminhoListaComPesos(nos);
    }

    public List<String> buscaLargura(String origem, String destino) {

        if (!tabelaAdjacencias.containsKey(origem) || !tabelaAdjacencias.containsKey(destino)) {
            return new ArrayList<>();
        }

        Fila fila = new Fila();   
        HashSet<String> visitados = new HashSet<>(); // se já foi visitado.
        HashMap<String, String> ListaDeVisitados = new HashMap<>(); // Armazena os passos anteriores para reconstruir o caminho

        Node noOrigem = new Node(origem);
        fila.enfileirar(noOrigem);
        visitados.add(origem);
        ListaDeVisitados.put(origem, null); // o Antes do vertice de origem é nulo

        while (!fila.filaVazia()) {

            Node atualNode = fila.desenfileirar();
            String atual = atualNode.valor;

            if (atual.equals(destino)) {
                // Reconstruir o caminho percorrido
                List<String> caminhoEncontrado = new ArrayList<>();
                String passo = destino;
                while (passo != null) {
                    caminhoEncontrado.add(0, passo); // Adiciona o passo no início da lista
                    passo = ListaDeVisitados.get(passo); // Move para o passo anterior
                }
                return caminhoEncontrado;
            }
        

            for (Map.Entry<String, Integer> adjacente : tabelaAdjacencias.get(atual).adjacentes.entrySet()) {
                String vizinho = adjacente.getKey();

                if (!visitados.contains(vizinho)) {
                    Node noVizinho = new Node(vizinho);
                    fila.enfileirar(noVizinho);
                    visitados.add(vizinho);
                    ListaDeVisitados.put(vizinho, atual); // Armazena o passo anterior
                }
            }
        }
        return new ArrayList<>();
    }

    public List<String> buscaProfundidade(String origem, String destino) {
        // Caso um deles não exista
        if (!tabelaAdjacencias.containsKey(origem) || !tabelaAdjacencias.containsKey(destino)) {
            return new ArrayList<>();
        }

        Pilha pilha = new Pilha();
        Node noOrigem = new Node(origem);
        pilha.inserir(noOrigem);
        
        HashSet<String> visitados = new HashSet<>();
        HashMap<String, String> caminhoVisitados = new HashMap<>();
        
        visitados.add(origem);
        caminhoVisitados.put(origem, null);
        
        while (pilha.primeiro != null) {
            Node atual = pilha.primeiro;
            String email = atual.valor;
            pilha.remover();
            
            if (email.equals(destino)) {
                // Reconstruir o caminho percorrido
                List<String> caminhoEncontrado = new ArrayList<>();
                String passo = destino;
                while (passo != null) {
                    caminhoEncontrado.add(0, passo);
                    passo = caminhoVisitados.get(passo);
                }
                return caminhoEncontrado;
            }
            
            for (Map.Entry<String, Integer> adjacente : tabelaAdjacencias.get(email).adjacentes.entrySet()) {
                String vizinho = adjacente.getKey();
                
                if (!visitados.contains(vizinho)) {
                    Node noVizinho = new Node(vizinho);
                    noVizinho.valor = vizinho;
                    pilha.inserir(noVizinho);
                    visitados.add(vizinho);
                    caminhoVisitados.put(vizinho, email);
                }
            }
        }
        
        return new ArrayList<>();
    }

    private ResultadoDistancia calcularNosDistanciaD(String origem, int d) {
        ResultadoDistancia resultado = new ResultadoDistancia();

        if (!tabelaAdjacencias.containsKey(origem)) {
            return resultado; //origem não existe no grafo, retorna resultado vazio
        }

        if (d < 0) {
            return resultado; //se a distancia for invalida, retorna resultado vazio
        }

        Fila fila = new Fila();
        HashSet<String> visitados = new HashSet<>(); //garante que cada no seja visitado apenas uma vez

        Node noOrigem = new Node(origem);
        fila.enfileirar(noOrigem);
        visitados.add(origem);

        resultado.distancias.put(origem, 0); //a origem começa com distancia 0, pq ainda nao foi percorrida nenhuma aresta
        resultado.anterior.put(origem, null);

        while (!fila.filaVazia()) {
            Node atualNode = fila.desenfileirar();
            String atual = atualNode.valor;
            int distanciaAtual = resultado.distancias.get(atual);

            if (distanciaAtual == d) {
                resultado.nos.add(atual); 
                continue;
            }

            for (Map.Entry<String, Integer> adjacente : tabelaAdjacencias.get(atual).adjacentes.entrySet()) {
                String vizinho = adjacente.getKey();

                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    Node noVizinho = new Node(vizinho);
                    fila.enfileirar(noVizinho);

                    resultado.distancias.put(vizinho, distanciaAtual + 1); 
                    resultado.anterior.put(vizinho, atual);
                }
            }
        }

        return resultado;
    }

    public List<String> nosDistanciaD(String origem, int d) {
        return calcularNosDistanciaD(origem, d).nos;
    }

    public void imprimeNosDistanciaD(String origem, int d) {
        if (!tabelaAdjacencias.containsKey(origem)) {
            System.out.println("Vertice de origem nao encontrado.");
            return;
        }

        ResultadoDistancia resultado = calcularNosDistanciaD(origem, d);

        if (resultado.nos.isEmpty()) {
            System.out.println("Nenhum no encontrado a distancia " + d + " de " + origem);
            return;
        }

        System.out.println("Nos a distancia " + d + " de " + origem + ":");

        for (String no : resultado.nos) {
            List<String> caminho = montarCaminho(no, resultado.anterior);
            System.out.println("\nNó encontrado: " + no);
            System.out.println("Tamanho do caminho: " + resultado.distancias.get(no));
            System.out.println("Caminho: " + String.join(" -> ", caminho));
        }
    }

    private List<String> montarCaminho(String destino, HashMap<String, String> anterior) {
        List<String> caminho = new ArrayList<>();
        String atual = destino;

        while (atual != null) {
            caminho.add(0, atual);
            if (!anterior.containsKey(atual)) break; // nó não mapeado
            atual = anterior.get(atual);             // null para a origem
        }

        return caminho;
    }
}
