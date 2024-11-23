import java.util.Scanner;

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.inf = this.sup = this.esq = this.dir = null;
    }
}

class Matriz {
    private Celula inicio;
    private int linha, coluna;

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        alocarMatriz();
    }

    private void alocarMatriz() {
        inicio = new Celula();
        Celula temp = inicio;

        // Criando a primeira linha
        for (int j = 1; j < coluna; j++) {
            temp.dir = new Celula();
            temp.dir.esq = temp;
            temp = temp.dir;
        }

        // Criando as demais linhas
        Celula linhaSup = inicio;
        for (int i = 1; i < linha; i++) {
            Celula linhaAtual = new Celula();
            linhaSup.inf = linhaAtual;
            linhaAtual.sup = linhaSup;
            temp = linhaAtual;

            for (int j = 1; j < coluna; j++) {
                temp.dir = new Celula();
                temp.dir.esq = temp;
                if (linhaSup != null) {
                    linhaSup = linhaSup.dir;
                    temp.dir.sup = linhaSup;
                    linhaSup.inf = temp.dir;
                }
                temp = temp.dir;
            }
            linhaSup = linhaAtual;
        }
    }

    public void preencher(Scanner sc) {
        Celula linhaAtual = inicio;
        for (int i = 0; i < linha; i++) {
            Celula colunaAtual = linhaAtual;
            for (int j = 0; j < coluna; j++) {
                colunaAtual.elemento = sc.nextInt();
                colunaAtual = colunaAtual.dir;
            }
            linhaAtual = linhaAtual.inf;
        }
    }

    public Matriz soma(Matriz m) {
        if (this.linha != m.linha || this.coluna != m.coluna) return null;
        Matriz resp = new Matriz(this.linha, this.coluna);

        Celula a = this.inicio;
        Celula b = m.inicio;
        Celula c = resp.inicio;

        for (int i = 0; i < linha; i++) {
            Celula tempA = a;
            Celula tempB = b;
            Celula tempC = c;

            for (int j = 0; j < coluna; j++) {
                tempC.elemento = tempA.elemento + tempB.elemento;
                tempA = tempA.dir;
                tempB = tempB.dir;
                tempC = tempC.dir;
            }
            a = a.inf;
            b = b.inf;
            c = c.inf;
        }

        return resp;
    }

    public Matriz multiplicacao(Matriz m) {
        if (this.coluna != m.linha) return null;
        Matriz resp = new Matriz(this.linha, m.coluna);

        Celula cLinha = resp.inicio;
        Celula linhaA = this.inicio;

        for (int i = 0; i < this.linha; i++) {
            Celula colunaB = m.inicio;
            Celula cColuna = cLinha;

            for (int j = 0; j < m.coluna; j++) {
                int soma = 0;
                Celula tempA = linhaA;
                Celula tempB = colunaB;

                for (int k = 0; k < this.coluna; k++) {
                    soma += tempA.elemento * tempB.elemento;
                    tempA = tempA.dir;
                    tempB = tempB.inf;
                }
                cColuna.elemento = soma;
                cColuna = cColuna.dir;
                colunaB = colunaB.dir;
            }
            linhaA = linhaA.inf;
            cLinha = cLinha.inf;
        }

        return resp;
    }

    public void mostrarDiagonalPrincipal() {
        if (linha == coluna) {
            Celula temp = inicio;
            while (temp != null) {
                System.out.print(temp.elemento + " ");
                temp = temp.inf != null ? temp.inf.dir : null;
            }
            System.out.println();
        }
    }

    public void mostrarDiagonalSecundaria() {
        if (linha == coluna) {
            Celula temp = inicio;
            while (temp.dir != null) {
                temp = temp.dir;
            }
            while (temp != null) {
                System.out.print(temp.elemento + " ");
                temp = temp.inf != null ? temp.inf.esq : null;
            }
            System.out.println();
        }
    }

    public void mostrar() {
        Celula linhaAtual = inicio;
        for (int i = 0; i < linha; i++) {
            Celula colunaAtual = linhaAtual;
            for (int j = 0; j < coluna; j++) {
                System.out.print(colunaAtual.elemento + " ");
                colunaAtual = colunaAtual.dir;
            }
            System.out.println();
            linhaAtual = linhaAtual.inf;
        }
    }
}

public class matrizDinamica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();

        for (int i = 0; i < casos; i++) {
            int l1 = sc.nextInt();
            int c1 = sc.nextInt();
            Matriz matriz1 = new Matriz(l1, c1);
            matriz1.preencher(sc);

            int l2 = sc.nextInt();
            int c2 = sc.nextInt();
            Matriz matriz2 = new Matriz(l2, c2);
            matriz2.preencher(sc);

            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            Matriz soma = matriz1.soma(matriz2);
            if (soma != null) {
                soma.mostrar();
            } else {
                System.out.println("Impossível somar as matrizes");
            }

            Matriz multiplicacao = matriz1.multiplicacao(matriz2);
            if (multiplicacao != null) {
                multiplicacao.mostrar();
            } else {
                System.out.println("Impossível multiplicar as matrizes");
            }
        }
        sc.close();
    }
}
