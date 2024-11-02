class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        elemento = 0;
        prox = null;
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        prox = null;
    }
}

public class listaF {
    private Celula primeiro;
    private Celula ultimo;

    public listaF() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(int elemento) {
        Celula tmp = new Celula(elemento);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
    }

    public void inserirFim(int elemento) {
        Celula tmp = new Celula(elemento);
        ultimo.prox = tmp;
        ultimo = tmp;
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
        System.out.println( "(R) " + tmp.elemento);
    }

    public void removerFim() {
        if (primeiro == ultimo) {
            System.out.println("Lista vazia!");
            return;
        }
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        System.out.println( "(R) " + ultimo.elemento);
        ultimo = i;
        ultimo.prox = null;
    }

    public void inserir(int pos, int elemento) {
        if (pos < 0) {
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
    }

    public void remover(int pos) {
        if (primeiro == ultimo || pos < 0) {
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
        System.out.println( "(R) " + tmp.elemento);
        if (tmp == ultimo) {
            ultimo = i;
        }
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== LISTA FLEXIVEL SIMPLESMENTE ENCADEADA ===");
            listaF lista = new listaF();

            lista.inserirInicio(1);
            lista.inserirInicio(0);
            lista.inserirFim(4);
            lista.inserirFim(5);
            lista.inserir(2, 2);
            lista.inserir(3, 3);
            lista.inserir(6, 6);
            lista.inserirFim(7);
            lista.inserirFim(8);

            System.out.print("Apos insercoes: ");
            lista.mostrar();

            lista.remover(3);
            lista.remover(2);
            lista.removerFim();
            lista.removerInicio();
            lista.remover(0);
            lista.remover(2);
            lista.inserirFim(9);

            System.out.print("Apos remocoes: ");
            lista.mostrar();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
