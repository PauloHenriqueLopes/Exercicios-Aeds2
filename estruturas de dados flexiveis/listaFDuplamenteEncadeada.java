class Celula {
    public int elemento;
    public Celula prox;
    public Celula ant;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
    }
}

public class listaFDuplamenteEncadeada {
    private Celula primeiro;
    private Celula ultimo;

    public listaFDuplamenteEncadeada() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(int elemento) {
        Celula tmp = new Celula(elemento);
        tmp.prox = primeiro.prox;
        tmp.ant = primeiro;
        if (primeiro == ultimo) {
            ultimo = tmp;
        } else {
            primeiro.prox.ant = tmp;
        }
        primeiro.prox = tmp;
    }

    public void inserirFim(int elemento) {
        Celula tmp = new Celula(elemento);
        tmp.ant = ultimo;
        ultimo.prox = tmp;
        ultimo = tmp;
    }

    public void removerInicio() {
        if (primeiro == ultimo) {
            System.out.println("Lista vazia!");
            return;
        }
        Celula remover = primeiro.prox;
        primeiro.prox = remover.prox;
        if (primeiro.prox != null) {
            primeiro.prox.ant = primeiro;
        } else {
            ultimo = primeiro;
        }
        System.out.println("(R) " + remover.elemento);
    }

    public void removerFim() {
        if (primeiro == ultimo) {
            System.out.println("Lista vazia!");
            return;
        }
        Celula remover = ultimo;
        ultimo = remover.ant;
        ultimo.prox = null;
        System.out.println("(R) " + remover.elemento);
    }

    public void inserir(int elemento, int pos) {
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho) {
            System.out.println("Posição inválida!");
            return;
        }
        if (pos == 0) {
            inserirInicio(elemento);
        } else if (pos == tamanho) {
            inserirFim(elemento);
        } else {
            Celula tmp = new Celula(elemento);
            Celula i = primeiro;
            for (int j = 0; j < pos; j++) {
                i = i.prox;
            }
            tmp.prox = i.prox;
            tmp.ant = i;
            i.prox.ant = tmp;
            i.prox = tmp;
        }
    }

    public void remover(int pos) {
        int tamanho = tamanho();
        if (pos < 0 || pos >= tamanho) {
            System.out.println("Posição inválida!");
            return;
        }
        if (pos == 0) {
            removerInicio();
        } else if (pos == tamanho - 1) {
            removerFim();
        } else {
            Celula i = primeiro.prox;
            for (int j = 0; j < pos; j++) {
                i = i.prox;
            }
            Celula remover = i;
            remover.ant.prox = remover.prox;
            remover.prox.ant = remover.ant;
            System.out.println("(R) " + remover.elemento);
        }
    }

    public void mostrar() {
        System.out.print("[ ");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public void mostrarInverso() {
        System.out.print("[ ");
        for (Celula i = ultimo; i != primeiro; i = i.ant) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            tamanho++;
        }
        return tamanho;
    }

    public static void main(String[] args) {
        System.out.println("=== LISTA DINAMICA DUPLAMENTE ENCADEADA ===");
        listaFDuplamenteEncadeada lista = new listaFDuplamenteEncadeada();

        lista.inserirInicio(1);
        lista.inserirInicio(0);
        lista.inserirFim(4);
        lista.inserirFim(5);
        lista.inserir(2, 2);
        lista.inserir(3, 3);
        lista.inserir(6, 6);
        lista.inserir(-1, 0);
        lista.inserirFim(7);
        lista.inserirFim(8);

        System.out.print("Apos insercoes: ");
        lista.mostrar();

        lista.remover(3);
        lista.remover(2);
        lista.removerFim();
        lista.removerInicio();
        lista.remover(0);
        lista.remover(4);

        System.out.print("Apos remocoes: ");
        lista.mostrar();
        System.out.print("Inverso: ");
        lista.mostrarInverso();
    }
}
