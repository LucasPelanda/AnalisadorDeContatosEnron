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
    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final double INFINITO = 999999999.0;
    private HashMap<String, String> caminho;

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

    public double caminhosCritico(String s, String t) {
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)) return INFINITO;

        HashMap<String, Double>  distancia = new HashMap<>();
        HashMap<String, Boolean> perm      = new HashMap<>();
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
        if (!tabelaAdjacencias.containsKey(s) || !tabelaAdjacencias.containsKey(t)) return;

        if (caminho.get(t) == null && !s.equals(t)) {
            System.out.println("Nao existe caminho entre os vertices informados.");
            return;
        }

        java.util.ArrayList<String> nos = new java.util.ArrayList<>();
        String i = t;
        while (i != null) {
            nos.add(0, i);
            i = caminho.get(i);
        }

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
}
