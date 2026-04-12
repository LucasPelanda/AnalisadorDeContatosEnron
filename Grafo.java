public class Grafo {
    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final int INFINITO = 999999999;
    private ListaEncadeada[] listas; 
    private String[] vertices;       
    public int tamanho;
    private int[] caminho;

    public Grafo(int tamanho) {
        this.tamanho = tamanho;
        this.listas = new ListaEncadeada[tamanho];
        this.vertices = new String[tamanho];
        this.caminho = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            listas[i] = new ListaEncadeada();
            vertices[i] = "";
        }
    }

    private void validaVertice(int indiceVertice) {
        if (indiceVertice < 0 || indiceVertice >= tamanho) {
            throw new IllegalArgumentException("Indice de vertice invalido: " + indiceVertice);
        }
    }

    public void cria_adjacencia(int origem, int destino, int peso) {
        validaVertice(origem);
        validaVertice(destino);
        listas[origem].inserir(destino, peso);
    }

    public void remove_adjacencia(int origem, int destino) {
        validaVertice(origem);
        validaVertice(destino);
        listas[origem].remover(destino);
    }

    public void imprime_adjacencias() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(vertices[i] + "[" + i + "] ");
            Node atual = listas[i].head;
            while (atual != null) {
                System.out.print("-> " + vertices[atual.destino] + "[" + atual.destino + "](peso:" + atual.peso + ") ");
                atual = atual.proximo;
            }
            System.out.println();
        }
    }

    public void seta_informacao(int indiceVertice, String valor) {
        validaVertice(indiceVertice);
        vertices[indiceVertice] = valor;
    }

    public int adjacentes(int indiceVertice, int[] adjacentes) {
        validaVertice(indiceVertice);
        int quantidade = 0;
        Node atual = listas[indiceVertice].head;
        while (atual != null) {
            adjacentes[quantidade] = atual.destino;
            quantidade++;
            atual = atual.proximo;
        }
        return quantidade;
    }

    public boolean[][] matrizAdjacenciaBooleana() {
        boolean[][] matriz = new boolean[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            Node atual = listas[i].head;
            while (atual != null) {
                matriz[i][atual.destino] = true;
                atual = atual.proximo;
            }
        }
        return matriz;
    }

    public boolean[][] fechamentoTransitivoWarshall() {
        boolean[][] m = matrizAdjacenciaBooleana();
        boolean[][] fechamento = new boolean[tamanho][tamanho];
        fechamento(m, fechamento);
        return fechamento;
    }

    public void fechamento(boolean[][] m, boolean[][] fechamento) {
        int i;
        int j;
        int k;
        // inicializacao da matriz de fechamento
        for (i = 0; i < tamanho; i++) {
            for (j = 0; j < tamanho; j++) {
                fechamento[i][j] = m[i][j];
            }
        }
        // algoritmo de Warshall
        for (k = 0; k < tamanho; k++) {
            for (i = 0; i < tamanho; i++) {
                if (fechamento[i][k]) {
                    for (j = 0; j < tamanho; j++) {
                        fechamento[i][j] = fechamento[i][j] || fechamento[k][j];
                    }
                }
            }
        }
    }

    private int peso(int origem, int destino) {
        Node atual = listas[origem].head;
        while (atual != null) {
            if (atual.destino == destino) {
                return atual.peso;
            }
            atual = atual.proximo;
        }
        return INFINITO;
    }

    public int melhorCaminho(int s, int t) {
        validaVertice(s);
        validaVertice(t);
        int distancia[] = new int[tamanho];
        boolean perm[] = new boolean[tamanho];
        int corrente;
        int i;
        int k = s;
        int dc;
        int j = 0;
        int menordist;
        int novadist;
        // inicializacao
        for (i = 0; i < tamanho; ++i) {
            perm[i] = NAOMEMBRO;
            distancia[i] = INFINITO;
            caminho[i] = -1;
        }

        perm[s] = MEMBRO;
        distancia[s] = 0;
        corrente = s;
        while (corrente != t) {
            menordist = INFINITO;
            dc = distancia[corrente];
            for (i = 0; i < tamanho; i++) {
                if (!perm[i]) {
                    if (dc != INFINITO) {
                        novadist = dc + peso(corrente, i);
                    } else {
                        novadist = INFINITO;
                    }
                    if (novadist < distancia[i]) {
                        distancia[i] = novadist;
                        caminho[i] = corrente;
                    }
                    if (distancia[i] < menordist) {
                        menordist = distancia[i];
                        k = i;
                    }
                }
            }

            if (menordist == INFINITO) {
                return INFINITO;
            }

            corrente = k;
            perm[corrente] = MEMBRO;
            j++;
        }
        if (j < 0) {
            return INFINITO;
        }

        return distancia[t];
    }


    public void imprimeCaminho(int s, int t) {
        validaVertice(s);
        validaVertice(t);
        if (caminho[t] == -1 && s != t) {
            System.out.println("Nao existe caminho entre os vertices informados.");
            return;
        }
        int i = caminho[t];
        System.out.print(vertices[t] + " ");
        while (i != s) {
            System.out.print(vertices[i] + " ");
            i = caminho[i];
            if (i == -1) {
                System.out.println();
                return;
            }
        }
        System.out.println(vertices[i]);
    }

    public static void main(String[] args) {
        System.out.println("=== Testes simples: Warshall ===");
        Grafo grafoWarshall = new Grafo(4);
        grafoWarshall.cria_adjacencia(0, 1, 1);
        grafoWarshall.cria_adjacencia(1, 2, 1);
        grafoWarshall.cria_adjacencia(2, 3, 1);
        boolean[][] matrizFechamento = grafoWarshall.fechamentoTransitivoWarshall();
        System.out.println(matrizFechamento[0][3] ? "OK: 0 alcanca 3" : "ERRO: 0 deveria alcancar 3");
        System.out.println(!matrizFechamento[3][0] ? "OK: 3 nao alcanca 0" : "ERRO: 3 nao deveria alcancar 0");

        System.out.println("\n=== Testes simples: Dijkstra ===");
        Grafo grafoDijkstra = new Grafo(5);
        grafoDijkstra.seta_informacao(0, "A");
        grafoDijkstra.seta_informacao(1, "B");
        grafoDijkstra.seta_informacao(2, "C");
        grafoDijkstra.seta_informacao(3, "D");
        grafoDijkstra.seta_informacao(4, "E");

        grafoDijkstra.cria_adjacencia(0, 1, 10);
        grafoDijkstra.cria_adjacencia(0, 2, 3);
        grafoDijkstra.cria_adjacencia(2, 1, 1);
        grafoDijkstra.cria_adjacencia(1, 3, 2);
        grafoDijkstra.cria_adjacencia(2, 4, 2);
        grafoDijkstra.cria_adjacencia(4, 3, 1);

        int distanciaMinima = grafoDijkstra.melhorCaminho(0, 3);
        System.out.println(distanciaMinima == 6 ? "OK: distancia minima = 6" : "ERRO: distancia minima deveria ser 6");
        System.out.print("Caminho encontrado (de t ate s): ");
        grafoDijkstra.imprimeCaminho(0, 3);

    }
}
