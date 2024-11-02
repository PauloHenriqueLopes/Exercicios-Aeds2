class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        elemento = 0;
        prox = null;
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

public class filaF {
    private Celula primeiro;
    private Celula ultimo;

    public filaF() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(int elemento) {
        Celula tmp = new Celula(elemento);
        ultimo.prox = tmp;
        ultimo = tmp;
    }

    public void remover() {
        if(primeiro == ultimo) {
            System.out.println("Fila Vazia!");
            return;
        }
        Celula tmp = primeiro;
        primeiro = tmp.prox;
        System.err.println("(R) " + primeiro.elemento);
        tmp.prox = null;
        tmp = null;
    }

    public void mostrar() {
        System.out.print("[ ");
        for(Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("==== FILA FLEXIVEL ====");
        filaF fila = new filaF();

        fila.inserir(5);
        fila.inserir(7);
        fila.inserir(8);
        fila.inserir(9);

        System.out.println("Apos insercoes(5, 7, 8, 9): ");
        fila.mostrar();


        fila.remover();
        fila.remover();

        System.out.println("Apos remocoes: ");
        fila.mostrar();

        fila.inserir(3);
        fila.inserir(4);

        System.out.println("Apos insercoes(3, 4): ");
        fila.mostrar();

        fila.remover();
        fila.remover();
        fila.remover();

        System.out.println("Apos remocoes: ");
        fila.mostrar();

        fila.inserir(4);
        fila.inserir(5);

        System.out.println("Apos insercoes(4, 5): ");
        fila.mostrar();

        fila.remover();
        fila.remover();

        System.out.println("Apos remocoes: ");
        fila.mostrar();

        fila.inserir(6);
        fila.inserir(7);

        System.out.println("Apos insercoes(6, 7): ");
        fila.mostrar();

        fila.remover();

        System.out.println("Apos remocao: ");
        fila.mostrar();
    }
}
