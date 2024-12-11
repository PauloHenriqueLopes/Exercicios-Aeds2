class Quadrado{
    public int lado;

    Quadrado(int lado) {
        this.lado = lado;
    }

    Quadrado() {
        this.lado = 0;
    }
}

public class oo {
    public static void main(String[] args) {
        Quadrado quadrado = new Quadrado(10);
        Quadrado quadrado2 = new Quadrado();

        System.out.println(quadrado.lado);
        System.out.println(quadrado2.lado);
    }
}