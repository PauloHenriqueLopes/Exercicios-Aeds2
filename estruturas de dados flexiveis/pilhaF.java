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

class Fila {
    private Celula primeiro;
    private Celula ultimo;

    public Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(int elemento) {
        ultimo.prox = new Celula(elemento);
        ultimo = ultimo.prox;
    }

    public void remover() {
        if(primeiro == ultimo) {
            System.out.println("Fila Vazia!");
            return;
        }
        Celula remover  = primeiro.prox;
        primeiro = primeiro.prox;
        System.out.println("(R) " + remover.elemento);
    }

    public void mostrarFila() {
        System.out.print("[ ");
        for(Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }
}

public class pilhaF {
    private Celula topo;

    public pilhaF() {
        topo = null;
    }

    public void inserir(int elemento) {
        Celula tmp = new Celula(elemento);

        tmp.prox = topo;
        topo = tmp;
    }

    public void remover() {
        if(topo.prox == null) {
            System.out.println("Pilha vazia!");
            return;
        }
        Celula remover = topo;
        topo = remover.prox;
        System.out.println("(R) " + remover.elemento);
    }

    public void mostrar() {
        System.out.print("[ ");
		for (Celula i = topo; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
		}
		System.out.println("] ");
    }

    public int getSoma() {
        return getSoma(topo);
    }

    private int getSoma(Celula i) {
        int resp = 0;
        if(i != null) {
            resp += i.elemento + getSoma(i.prox);
        }
        return resp;
    }

    public void mostraPilha() {
		mostraPilha(topo);
	}

	private void mostraPilha(Celula i) {
		if (i != null) {
			mostraPilha(i.prox);
			System.out.println("" + i.elemento);
		}
	}

    public Fila converteFilaInsercao() {
        Fila fila = new Fila();
        pilhaF pilhaAux = new pilhaF();

        for(Celula i = topo; i != null; i = i.prox) {
            pilhaAux.inserir(i.elemento);
        }

        for(Celula i = pilhaAux.topo; i != null; i = i.prox) {
            fila.inserir(i.elemento);
        }

        return fila;
    }

    public Fila converteFilaRemocao() {
        Fila fila = new Fila();

        for(Celula i = topo; i != null; i = i.prox) {
            fila.inserir(i.elemento);
        }

        return fila;
    }


    public static void main(String[] args) {
        System.out.println(" ==== PILHA FLEXIVEL ====");
			pilhaF pilha = new pilhaF();

			pilha.inserir(0);
			pilha.inserir(1);
			pilha.inserir(2);
			pilha.inserir(3);
			pilha.inserir(4);
			pilha.inserir(5);

			System.out.print("Apos insercoes: ");
			pilha.mostrar();

            System.out.print("Soma: ");
			pilha.getSoma();

			pilha.remover();
			pilha.remover();
			pilha.remover();

			System.out.print("Apos as remocoes: ");
			pilha.mostrar();

            Fila fila = pilha.converteFilaInsercao();

            System.out.print("Fila resultante(ORDEM DE INSERCAO PILHA): ");
            fila.mostrarFila();

            Fila fila2 = pilha.converteFilaRemocao();

            System.out.print("Fila resultante(ORDEM DE REMOCAO PILHA): ");
            fila2.mostrarFila();
    }
}
