class CelulaDupla {
    public int elemento;
    public CelulaDupla prox;
    public CelulaDupla ant;

    public CelulaDupla() {
        this.elemento = 0;
        this.prox = null;
        this.ant = null;
    }

    public CelulaDupla(int elemento) {
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
    }
}
public class listaFlexivelDuplamenteConcatenadas {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;
    private int tamanho;

    public listaFlexivelDuplamenteConcatenadas() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
        tamanho = 0;
    }

    public void inserirInicio(int elemento) {
        CelulaDupla inserir = new CelulaDupla(elemento);

        if(primeiro == ultimo) {
            primeiro.prox = inserir;
            inserir.ant = primeiro;
            ultimo = inserir;
        } else {
            inserir.prox = primeiro.prox;
            primeiro.prox.ant = inserir;
            primeiro.prox = inserir;
            inserir.ant = primeiro;
        }
        tamanho++;
    }

    public void inserirFim(int elemento) {
        CelulaDupla inserir = new CelulaDupla(elemento);
        if(primeiro == ultimo) {
            primeiro.prox = inserir;
            inserir.ant = primeiro;
            ultimo = inserir;
        } else {
            ultimo.prox = inserir;
            inserir.ant = ultimo;
            ultimo = inserir;
        }
        tamanho++;
    }

    public void removerInicio() {
        if(primeiro == ultimo) {
            System.out.println("Erro, lista vazia!");
            return;
        } 
        CelulaDupla remover = primeiro.prox;
        primeiro.prox = remover.prox;
        if (remover.prox != null) {
            remover.prox.ant = primeiro;
        } else {
            ultimo = primeiro;
        }
        remover.prox = null;
        remover.ant = null;
        System.out.println("(R) " + remover.elemento);
        tamanho--;
    }

    public void removerFim() {
        CelulaDupla remover;
        if(primeiro == ultimo) {
            System.out.println("Erro, lista vazia!");
            return;
        } else {
            remover = ultimo;
            ultimo = remover.ant;
            remover.ant.prox = null;
            remover.ant = null;
        }
        tamanho--;
    }

    public void inserir(int elemento, int pos) {
        if(pos < 0 || pos > tamanho) {
            System.out.println("Erro, posicao invalida!");
            return;
        }
        if(pos == 0) {
            inserirInicio(elemento);
        } else if(pos == tamanho) {
            inserirFim(elemento);
        } else {
            CelulaDupla inserir = new CelulaDupla(elemento);
            CelulaDupla i = primeiro;
            for(int j = 0; j < pos; j++ , i = i.prox);
            i.prox.ant = inserir;
            inserir.prox = i.prox;
            i.prox = inserir;
            tamanho++;
        }
    }

    public void remover(int pos) {
        if(pos < 0 || pos > tamanho) {
            System.out.println("Erro, posicao invalida!");
            return;
        } else if(pos == 0) {
            removerInicio();
        } else if(pos == tamanho) {
            removerFim();
        } else {
            CelulaDupla remover;
            CelulaDupla i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            remover = i.prox;
            remover.prox.ant = i; 
            i.prox = remover.prox;
            remover.prox = null;
            remover.ant = null;
            System.out.println("(R) " + remover.elemento);
            tamanho--;
        }
    }

    public void mostrar() {
        CelulaDupla i;
        System.out.print("[ ");
        for(i = primeiro.prox; i != null; i = i.prox) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public void mostrarInvertido() {
        CelulaDupla i;
        System.out.print("[ ");
        for(i = ultimo; i.ant != null; i = i.ant) {
            System.out.print(i.elemento + " ");
        }
        System.out.println("]");
    }

    public listaFlexivelDuplamenteConcatenadas concatena(listaFlexivelDuplamenteConcatenadas lista1, listaFlexivelDuplamenteConcatenadas lista2) {
        listaFlexivelDuplamenteConcatenadas listaConcatenada = new listaFlexivelDuplamenteConcatenadas();

        for(CelulaDupla i = lista1.primeiro.prox; i != null; i = i.prox) {
            listaConcatenada.inserirFim(i.elemento);
        }

        for(CelulaDupla i = lista2.primeiro.prox; i != null; i = i.prox) {
            listaConcatenada.inserirFim(i.elemento);
        }

        return listaConcatenada;
    }

    public listaFlexivelDuplamenteConcatenadas concatenaAlternado(listaFlexivelDuplamenteConcatenadas lista1, listaFlexivelDuplamenteConcatenadas lista2) {
        listaFlexivelDuplamenteConcatenadas listaConcatenada = new listaFlexivelDuplamenteConcatenadas();
        CelulaDupla i , j;
        i = lista1.primeiro.prox;
        j = lista2.primeiro.prox;
        while (i != null || j !=null) {
            if(i != null) {
                listaConcatenada.inserirFim(i.elemento);
                i = i.prox;
            }
            if(j != null) {
                listaConcatenada.inserirFim(j.elemento);
                j = j.prox;
            }
        }
        return listaConcatenada;
    }

    public static void main(String[] args) {
        listaFlexivelDuplamenteConcatenadas lista1 = new listaFlexivelDuplamenteConcatenadas();
        listaFlexivelDuplamenteConcatenadas lista2 = new listaFlexivelDuplamenteConcatenadas();

        lista1.inserirInicio(1);
        lista1.inserirFim(1);
        lista1.inserirInicio(5);
        lista1.inserirInicio(2);
        lista1.inserirFim(2);
        lista1.inserirFim(0);
        lista1.inserirFim(0);
        lista1.inserirFim(4);
        
        System.out.println("Lista 1: ");
        lista1.mostrar();
        lista1.mostrarInvertido();

        lista2.inserirInicio(3);
        lista2.inserirFim(29);
        lista2.inserirInicio(125);
        lista2.inserirInicio(12);
        lista2.inserirFim(2);
        lista2.inserirFim(40);
        lista2.inserirFim(8);
        lista2.inserirFim(4);
        
        System.out.println("Lista 2: ");
        lista2.mostrar();
        lista2.mostrarInvertido();

        System.out.println("Listas Concatenadas: ");
        listaFlexivelDuplamenteConcatenadas listaConcatenada = lista1.concatena(lista1, lista2);
        listaConcatenada.mostrar();

        System.out.println("Listas Concatenadas Alternadas: ");
        listaFlexivelDuplamenteConcatenadas listaConcatenada2 = lista1.concatenaAlternado(lista1, lista2);
        listaConcatenada2.mostrar();
    }
}
