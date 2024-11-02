class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        this.elemento = 0;
        this.prox = null; 
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

public class listaFlexivelConcatenadas {
    private Celula primeiro;
    private Celula ultimo;
    private int tamanho;

    public listaFlexivelConcatenadas() {
        this.primeiro = new Celula();
        this.ultimo = primeiro;
        this.tamanho = 0;
    }

    public void inserirInicio(int elemento) {
        Celula tmp = new Celula(elemento);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tamanho++;
    }

    public void inserirFim(int elemento) {
        Celula tmp = new Celula(elemento);
        ultimo.prox = tmp;
        ultimo = tmp;
        tamanho++;
    }

    public void removerInicio() {
        if (primeiro == ultimo) {
            System.out.println("Lista vazia!");
            return;
        }
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        if (tmp == ultimo) {
            ultimo = primeiro;
        }
        System.out.println("(R) " + tmp.elemento);
        tamanho--;
    }

    public void removerFim() {
        if (primeiro == ultimo) {
            System.out.println("Lista vazia!");
            return;
        }
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        System.out.println("(R) " + ultimo.elemento);
        ultimo = i;
        ultimo.prox = null;
        tamanho--;
    }

    public void inserir(int pos, int elemento) {
        if (pos < 0 || pos > tamanho) {
            System.out.println("Posição inválida!");
            return;
        }
        Celula i = primeiro;
        for (int j = 0; j < pos && i.prox != null; j++, i = i.prox);
        Celula tmp = new Celula(elemento);
        tmp.prox = i.prox;
        i.prox = tmp;
        if (i == ultimo) {
            ultimo = tmp;
        }
        tamanho++;
    }

    public void remover(int pos) {
        if (primeiro == ultimo || pos < 0 || pos >= tamanho) {
            System.out.println("Posição inválida ou lista vazia!");
            return;
        }
        Celula i = primeiro;
        for (int j = 0; j < pos && i.prox != null; j++, i = i.prox);
        if (i.prox == null) {
            System.out.println("Posição inválida!");
            return;
        }
        Celula tmp = i.prox;
        i.prox = tmp.prox;
        System.out.println("(R) " + tmp.elemento);
        if (tmp == ultimo) {
            ultimo = i;
        }
        tamanho--;
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public listaFlexivelConcatenadas concatena(listaFlexivelConcatenadas fila1, listaFlexivelConcatenadas fila2) {
        listaFlexivelConcatenadas filaConcatenada = new listaFlexivelConcatenadas();

        for (Celula i = fila1.primeiro.prox; i != null; i = i.prox) {
            filaConcatenada.inserirFim(i.elemento);
        }

        for (Celula i = fila2.primeiro.prox; i != null; i = i.prox) {
            filaConcatenada.inserirFim(i.elemento);
        }

        return filaConcatenada;
    }

    public listaFlexivelConcatenadas concatenaAlternado(listaFlexivelConcatenadas lista1, listaFlexivelConcatenadas lista2) {
        listaFlexivelConcatenadas listaConcatenada = new listaFlexivelConcatenadas();
        
        Celula i = lista1.primeiro.prox;
        Celula j = lista2.primeiro.prox;
    
        while (i != null || j != null) {
            if (i != null) {
                listaConcatenada.inserirFim(i.elemento);
                i = i.prox;
            }
            if (j != null) {
                listaConcatenada.inserirFim(j.elemento);
                j = j.prox;
            }
        }
    
        return listaConcatenada;
    }

    public static void main(String[] args) {
        System.out.println("=== LISTA FLEXIVEL CONCATENADAS ===");
        listaFlexivelConcatenadas lista1 = new listaFlexivelConcatenadas();
        listaFlexivelConcatenadas lista2 = new listaFlexivelConcatenadas();

        lista1.inserirInicio(1);
        lista1.inserirInicio(0);
        lista1.inserirFim(4);
        lista1.inserirFim(5);
        lista1.inserir(2, 2);
        lista1.inserir(3, 3);
        lista1.inserir(6, 6);
        lista1.inserirFim(7);
        lista1.inserirFim(8);

        System.out.print("Apos insercoes Lista 1: ");
        lista1.mostrar();

        lista2.inserirInicio(3);
        lista2.inserirInicio(95);
        lista2.inserirFim(10);
        lista2.inserirFim(24);
        lista2.inserir(2, 75);
        lista2.inserir(3, 1);
        lista2.inserir(6, 0);
        lista2.inserirFim(9);
        lista2.inserirFim(19);

        System.out.print("Apos insercoes Lista 2: ");
        lista2.mostrar();

        lista1.remover(3);
        lista1.remover(2);
        lista2.removerFim();
        lista2.removerInicio();
        lista1.remover(0);
        lista2.remover(2);

        System.out.print("Apos Remocoes Lista 1: ");
        lista1.mostrar();

        System.out.print("Apos Remocoes Lista 2: ");
        lista2.mostrar();

        System.out.print("Concatenadas: ");
        listaFlexivelConcatenadas listaConcatenada = lista1.concatena(lista1, lista2);
        listaConcatenada.mostrar();

        System.out.print("Concatenadas Ordenada: ");
        listaFlexivelConcatenadas listaConcatenada2 = lista1.concatenaAlternado(lista1, lista2);
        listaConcatenada2.mostrar();
    }
}
