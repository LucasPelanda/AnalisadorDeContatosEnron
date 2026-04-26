// DAVI NATUME, PEDRO FAVERO, LUCAS PELANDA, EDUARDO TEODORO 

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Grafo {
    // ATRIBUTOS DO GRAFO
    private HashMap<String, Vertice> tabelaAdjacencias; 
    public Vertice[] maioresSaidas;
    public Vertice[] maioresEntradas;

    // CONSTANTES PARA O MELHOR CAMINHO
    //private static final boolean MEMBRO = true;
    //private static final boolean NAOMEMBRO = false;
    //private static final int INFINITO = 999999999;

    public Grafo() {
        this.tabelaAdjacencias = new HashMap<>();
        this.maioresEntradas = new Vertice[20];
        this.maioresSaidas = new Vertice[20];
    }

    public void adicionarMensagem(String origem, String destino) {
        // CRIA VERTICES CASO NAO EXISTAM
        if(!tabelaAdjacencias.containsKey(origem)){
            tabelaAdjacencias.put(origem, new Vertice(origem));
        }
        if(!tabelaAdjacencias.containsKey(destino)){
            tabelaAdjacencias.put(destino, new Vertice(destino));
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



    // public void remove_adjacencia(int origem, int destino) {
    //     if(validaVertice(origem) && validaVertice(destino)){
    //         listaAdj[origem].remover(destino);
    //     }  
    // }

    //imprime a lista de adjacencias do grafo adaptado para Hash 
    public void imprime_adjacencias() {
        for (Map.Entry<String, Vertice> entrada : tabelaAdjacencias.entrySet()) {
            System.out.println(entrada.getKey() + " ");
            Vertice atual = entrada.getValue();

            for(Map.Entry<String, Integer> adjacente : atual.adjacentes.entrySet()) {
                System.out.print("-> "  + "[" + adjacente.getKey() + "](peso:" + adjacente.getValue() + ") ");
            }
        
            System.out.println();
        }
    }

    public void imprime_maiores() {
        System.out.println(" Maiores Entradas");
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

    // public void seta_informacao(int indiceVertice, String valor) {
    //     if(validaVertice(indiceVertice)){
    //         rotulos[indiceVertice] = valor;
    //     }

    // }

    // public int adjacentes(int indiceVertice, int[] adjacentes) {
    //     if(validaVertice(indiceVertice)){
    //         int quantidade = 0;
    //         Node atual = listaAdj[indiceVertice].head;
    //         while (atual != null) {
    //             adjacentes[quantidade] = atual.destino;
    //             quantidade++;
    //             atual = atual.proximo;
    //         }
    //         return quantidade;
    //     }
    //     else{
    //         return 0;
    //     }
    // }

    // public boolean[][] matrizAdjacenciaBooleana() {
    //     boolean[][] matriz = new boolean[tamanho][tamanho];
    //     for (int i = 0; i < tamanho; i++) {
    //         Node atual = listaAdj[i].head;
    //         while (atual != null) {
    //             matriz[i][atual.destino] = true;
    //             atual = atual.proximo;
    //         }
    //     }
    //     return matriz;
    // }

    // public boolean[][] fechamentoTransitivoWarshall() {
    //     boolean[][] m = matrizAdjacenciaBooleana();
    //     boolean[][] fechamento = new boolean[tamanho][tamanho];
    //     fechamento(m, fechamento);
    //     return fechamento;
    // }

    // public void fechamento(boolean[][] m, boolean[][] fechamento) {
    //     int i;
    //     int j;
    //     int k;
    //     // inicializacao da matriz de fechamento
    //     for (i = 0; i < tamanho; i++) {
    //         for (j = 0; j < tamanho; j++) {
    //             fechamento[i][j] = m[i][j];
    //         }
    //     }
    //     // algoritmo de Warshall
    //     for (k = 0; k < tamanho; k++) {
    //         for (i = 0; i < tamanho; i++) {
    //             if (fechamento[i][k]) {
    //                 for (j = 0; j < tamanho; j++) {
    //                     fechamento[i][j] = fechamento[i][j] || fechamento[k][j];
    //                 }
    //             }
    //         }
    //     }
    // }

    // private int peso(int origem, int destino) {
    //     Node atual = listaAdj[origem].head;
    //     while (atual != null) {
    //         if (atual.destino == destino) {
    //             return atual.peso;
    //         }
    //         atual = atual.proximo;
    //     }
    //     return INFINITO;
    // }

    // public int melhorCaminho(int s, int t) {
    //     if(!validaVertice(s) || !validaVertice(t)) return INFINITO;

    //     int distancia[] = new int[tamanho];
    //     boolean perm[] = new boolean[tamanho];
    //     int corrente;
    //     int i;
    //     int k = s;
    //     int dc;
    //     int menordist;
    //     int novadist;
    //     // inicializacao
    //     for (i = 0; i < tamanho; ++i) {
    //         perm[i] = NAOMEMBRO;
    //         distancia[i] = INFINITO;
    //         caminho[i] = -1;
    //     }

    //     perm[s] = MEMBRO;
    //     distancia[s] = 0;
    //     corrente = s;
    //     while (corrente != t) {
    //         menordist = INFINITO;
    //         dc = distancia[corrente];
    //         for (i = 0; i < tamanho; i++) {
    //             if (!perm[i]) {
    //                 if (dc != INFINITO) {
    //                     novadist = dc + peso(corrente, i);
    //                 } else {
    //                     novadist = INFINITO;
    //                 }
    //                 if (novadist < distancia[i]) {
    //                     distancia[i] = novadist;
    //                     caminho[i] = corrente;
    //                 }
    //                 if (distancia[i] < menordist) {
    //                     menordist = distancia[i];
    //                     k = i;
    //                 }
    //             }
    //         }

    //         if (menordist == INFINITO) {
    //             return INFINITO;
    //         }

    //         corrente = k;
    //         perm[corrente] = MEMBRO;
    //     }
       

    //     return distancia[t];
    // }


    // public void imprimeCaminho(int s, int t) {
    //     if(!validaVertice(s) || !validaVertice(t)) return;

    //     if (caminho[t] == -1 && s != t) {
    //         System.out.println("Nao existe caminho entre os vertices informados.");
    //         return;
    //     }
    //     int i = caminho[t];
    //     System.out.print(rotulos[t] + " ");
    //     while (i != s) {
    //         System.out.print(rotulos[i] + " ");
    //         i = caminho[i];
    //         if (i == -1) {
    //             System.out.println();
    //             return;
    //         }
    //     }
    //     System.out.println(rotulos[i]);
    // }

    // public static void main(String[] args) {
    //     System.out.println("=== Testes Warshall ===");
    //     Grafo grafoWarshall = new Grafo(4);
    //     grafoWarshall.cria_adjacencia(0, 1, 1);
    //     grafoWarshall.cria_adjacencia(1, 2, 1);
    //     grafoWarshall.cria_adjacencia(2, 3, 1);
    //     boolean[][] matrizFechamento = grafoWarshall.fechamentoTransitivoWarshall();
    //     System.out.println(matrizFechamento[0][3] ? "OK: 0 alcanca 3" : "ERRO: 0 deveria alcancar 3");
    //     System.out.println(!matrizFechamento[3][0] ? "OK: 3 nao alcanca 0" : "ERRO: 3 nao deveria alcancar 0");

    //     System.out.println("\n=== Testes Dijkstra ===");
    //     Grafo grafoDijkstra = new Grafo(5);
    //     grafoDijkstra.seta_informacao(0, "A");
    //     grafoDijkstra.seta_informacao(1, "B");
    //     grafoDijkstra.seta_informacao(2, "C");
    //     grafoDijkstra.seta_informacao(3, "D");
    //     grafoDijkstra.seta_informacao(4, "E");

    //     grafoDijkstra.cria_adjacencia(0, 1, 10);
    //     grafoDijkstra.cria_adjacencia(0, 2, 3);
    //     grafoDijkstra.cria_adjacencia(2, 1, 1);
    //     grafoDijkstra.cria_adjacencia(1, 3, 2);
    //     grafoDijkstra.cria_adjacencia(2, 4, 2);
    //     grafoDijkstra.cria_adjacencia(4, 3, 1);

    //     int distanciaMinima = grafoDijkstra.melhorCaminho(0, 3);
    //     System.out.println(distanciaMinima == 6 ? "distancia minima = 6" : "distancia minima deveria ser 6");
    //     System.out.print("Caminho encontrado de D a A: ");
    //     grafoDijkstra.imprimeCaminho(0, 3);

    // }
}
