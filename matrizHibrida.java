public class matrizHibrida {
    class Matriz {
        CelulaMatriz inicio;
        int linha, colula;
        CelulaDupla diagUnificada() {
            CelulaMatriz i = inicio;
            CelulaDupla inicioDupla = new CelulaDupla();
            CelulaDupla fimDupla = inicioDupla;
            while (i != null) {
                Celula j = i.inicio.prox;
                while (j != null) {
                    CelulaDupla nova = new CelulaDupla();
                    nova.elemento = j.elemento;

                    fimDupla.prox = nova;
                    nova.ant = fimDupla;
                    fimDupla = nova;

                    j = j.prox;
                }
                i = i.dir != null ? i.dir.inf : null;
            }
            return inicioDupla;
        }
    }

    class CelulaMatriz {
        CelulaMatriz esq, dir, inf, sup;
        Celula inicio, fim;
    }

    class Celula {
        int elemento;
        Celula prox;
    }

    class CelulaDupla {
        int elemento;
        CelulaDupla prox, ant;
    }
}