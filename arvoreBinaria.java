import java.util.*;
import java.util.concurrent.ExecutionException;

public class arvoreBinaria {
    class No{
        public int elemento;
        public No esq, dir;

        public No(int elemento) {
            this.elemento = elemento;
            this.esq = null;
            this.dir = null;
        }

        public No(int elemento, No esq, No dir) {
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }
    class Arvore{
        private No raiz;


        public Arvore() {
            raiz = null;
        }

        public boolean pesquisar(int x) {
            return pesquisar(raiz, x);
        }

        private boolean pesquisar(No i, int x) {
            boolean resp;
            if(i == null) {
                resp = false;
            } else if (x == i.elemento) {;
                resp = true;
            } else if(x < i.elemento) {
                resp = pesquisar(i.esq, x);
            } else {
                resp = pesquisar(i.dir, x);
            }
            return resp;
        }

        public void caminharCentral() {
            System.out.print("[ ");
            caminharCentral(raiz);
            System.out.println(" ]");
        }

        private void caminharCentral(No i) {
            if(i != null) {
                caminharCentral(i.esq);
                System.out.println(i.elemento + " ");
                caminharCentral(i.dir);
            }
        }

        public void caminharPre() {
            System.out.print("[ ");
            caminharPre(raiz);
            System.out.println(" ]");
        }

        private void caminharPre(No i) {
            if(i != null) {
                System.out.println(i.elemento + " ");
                caminharCentral(i.esq);
                caminharCentral(i.dir);
            }
        }

        public void caminharPos() {
            System.out.print("[ ");
            caminharPos(raiz);
            System.out.println(" ]");
        }

        private void caminharPos(No i) {
            if(i != null) {
                caminharCentral(i.esq);
                caminharCentral(i.dir);
                System.out.println(i.elemento + " ");
            }
        }

        public void inserir(int elemento) throws Exception {
            raiz = inserir(elemento, raiz);
        }

        private No inserir(int x, No i) throws Exception {
            if(i == null) {
                i = new No(x);
            } else if(x < i.elemento) {
                i.esq = inserir(x, i.esq);
            } else if(x > i.elemento) {
                i.dir = inserir(x, i.dir);
            } else {
                throw new Exception("Erro ao inserir");
            }
            return i;
        }
    }
}