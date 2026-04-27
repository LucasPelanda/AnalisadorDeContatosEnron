# 📧 Analisador de Contatos Enron

> **Projeto Colaborativo I** — Bacharelado em Ciência da Computação  
> 5º Período — Resolução de Problemas com Grafos

## 📋 Visão Geral

Desenvolvimento de um **analisador de contatos** baseado no [Enron Email Dataset](https://www.cs.cmu.edu/~./enron/), um conjunto público de e-mails corporativos. O projeto aplica **teoria dos grafos** para extrair informações úteis a partir da rede de contatos gerada pelos e-mails da base.

---

## 📅 Informações da Entrega

| Campo | Detalhe |
|---|---|
| **Prazo** | Terça-feira, 05/05/2026 (sem tolerância de atraso) |
| **Entregáveis** | Código-fonte via AVA + Teste de autoria presencial |
| **Equipes** | Até 4 pessoas |
| **Peso** | 40% de cada RA (Complemento das Notas RA1 e RA2) |

---

## 🗂️ Dataset

- **Nome:** Enron Email Dataset
- **Tipo:** Base de benchmark pública
- **URL:** https://www.cs.cmu.edu/~./enron/

---

## ✅ Requisitos e Funcionalidades

### 1. Construção do Grafo `(2.0 pts)`
Gerar um **grafo direcionado, ponderado e rotulado** a partir dos e-mails da base:
- **Vértices:** cada usuário identificado pelo seu e-mail
- **Arestas:** direcionadas do remetente para o(s) destinatário(s)
- **Peso:** frequência de envio entre dois usuários
- **Rótulo:** endereço de e-mail de cada vértice

---

### 2. Informações Gerais do Grafo `(1.0 pt)`

| Item | Pontuação | Descrição |
|---|---|---|
| a | 0.25 pt | Número de **vértices** do grafo |
| b | 0.25 pt | Número de **arestas** do grafo |
| c | 0.25 pt | Top 20 indivíduos com maior **grau de saída** (com valores) |
| d | 0.25 pt | Top 20 indivíduos com maior **grau de entrada** (com valores) |

---

### 3. Busca em Profundidade — DFS `(1.5 pts)`
Verificar se um indivíduo **X pode alcançar Y** via DFS, retornando a lista de nós visitados no caminho.

---

### 4. Busca em Largura — BFS `(1.5 pts)`
Verificar se um indivíduo **X pode alcançar Y** via BFS, retornando a lista de nós visitados no caminho.

---

### 5. Nós a Distância D `(2.0 pts)`
Retornar uma lista com todos os nós que estão a exatamente **D arestas de distância** de um nó N.
> Uma ligação entre X e Y = distância 1.

---

### 6. Caminho Crítico (Dijkstra Adaptado) `(2.0 pts)`
Determinar o **caminho de maior custo acumulado** (caminho crítico do fluxo de informação) entre um indivíduo A e um indivíduo C, usando uma **adaptação do algoritmo de Dijkstra**:
- O peso das arestas representa o **grau de dependência** de A em relação a B
- A adaptação considera o **inverso do peso** (`peso⁻¹`) para encontrar o caminho de maior dependência acumulada
- Retornar os indivíduos do caminho e a dependência acumulada

---

## ⚠️ Regras e Observações

- Todos os algoritmos devem **tratar ciclos** (evitar loops infinitos)
- **Proibido** utilizar programas prontos ou semi-prontos da Internet ou de outros grupos
- Plágio (total ou parcial) — tanto quem forneceu quanto quem recebeu — resulta em **nota zero**

---

## 📊 Resumo de Pontuação

| # | Funcionalidade | Pontos |
|---|---|---|
| 1 | Construção do grafo | 2.0 |
| 2 | Informações gerais | 1.0 |
| 3 | DFS | 1.5 |
| 4 | BFS | 1.5 |
| 5 | Nós a distância D | 2.0 |
| 6 | Dijkstra adaptado (caminho crítico) | 2.0 |
| | **Total** | **10.0** |
