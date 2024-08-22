import java.util.*;

public class TP01Q05 {

    final static String[] expressao = {
        " ",
        "not(0)",
        "not(1)",
        "and(0,0)",
        "and(1,1)",
        "and(0,1)",
        "and(1,0)",
        "and(0,0,",
        "and(1,1,",
        "and(0,1,",
        "and(1,0,",
        "or(0,0)",
        "or(1,1)",
        "or(0,1)",
        "or(1,0)",
        "or(0,0,",
        "or(1,1,",
        "or(0,1,",
        "or(1,0,"
    };

    final static String[] substituicao = {
        "",
        "1",
        "0",
        "0",
        "1",
        "0",
        "0",
        "and(and(0,0),",
        "and(and(1,1),",
        "and(and(0,1),",
        "and(and(1,0),",
        "0",
        "1",
        "1",
        "1",
        "or(or(0,0),",
        "or(or(1,1),",
        "or(or(0,1),",
        "or(or(1,0),"
    };

    final static String[] op1 = expressao;
    final static String[] op2 = substituicao;

    public static int[] valoresLidos(int i, int vet[]) {
        Scanner scanner = new Scanner(System.in);
        for (int j = 0; j < i; j++) {
            vet[j] = scanner.nextInt();
        }
        return vet;
    }

    public static String alterarExpressao(String e1, int variaveis[]) {
        return alterarExpressao(e1, variaveis, 0);
    }

    public static String alterarExpressao(String e1, int variaveis[], int i) {
        String r = "";
        if (i >= e1.length()) {
            return r;
        } else {
            r += alterarExpressao2(e1, variaveis, i);
            r += alterarExpressao(e1, variaveis, i + 1);
        }
        return r;
    }

    public static char alterarExpressao2(String e1, int variaveis[], int i) {
        return alterarExpressao2(e1, variaveis, i, 0);
    }

    public static char alterarExpressao2(String e1, int variaveis[], int i, int c) {
        char letra;
        if (c >= variaveis.length) {
            letra = e1.charAt(i);
        } else if ((char) ('A' + c) == e1.charAt(i)) {
            letra = (char) (variaveis[c] + '0');
        } else {
            letra = alterarExpressao2(e1, variaveis, i, c + 1);
        }
        return letra;
    }

    public static char resolve(String e2) {
        if (e2.charAt(0) == '0' || e2.charAt(0) == '1') {
            return e2.charAt(0);
        } else {
            e2 = resolve2(e2);
        }
        return resolve(e2);
    }

    public static String resolve2(String e2) {
        return resolve2(e2, 0);
    }

    public static String resolve2(String e2, int i) {
        if (i >= op1.length) {
            return e2;
        } else {
            e2 = sub(e2, op1[i], op2[i]);
        }
        return resolve2(e2, i + 1);
    }

    public static String sub(String frase, String procurando, String lugar) {
        return sub(frase, procurando, lugar, 0);
    }

    public static String sub(String frase, String procurando, String lugar, int i) {
        String nova = "";
        if (i >= frase.length()) {
            nova = "";
        } else if (saoIguais(frase, procurando, i)) {
            nova += lugar;
            i += procurando.length() - 1;
            nova += sub(frase, procurando, lugar, i + 1);
        } else {
            nova += frase.charAt(i);
            nova += sub(frase, procurando, lugar, i + 1);
        }
        return nova;
    }

    public static boolean saoIguais(String frase, String procurando, int i) {
        boolean t = false;
        int j = i;
        if (frase.length() - i >= procurando.length() && frase.charAt(i) == procurando.charAt(0)) {
            j += saoIguais2(frase, procurando, i, j);
            t = (j - i) >= procurando.length();
        }
        return t;
    }

    public static int saoIguais2(String frase, String procurando, int i, int j) {
        int c;
        if (!(j < frase.length() && j - i < procurando.length() && frase.charAt(j) == procurando.charAt(j - i))) {
            c = 0;
        } else {
            c = 1 + saoIguais2(frase, procurando, i, j + 1);
        }
        return c;
    }

    public static String resultado(String e2) {
        return resolve(e2) == '1' ? "SIM" : "NÃO";
    }

    public static void entrada() {
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();
        scanner.nextLine();

        if (quantidade == 0) {
            return;
        } else {
            int[] vet = new int[quantidade];
            int[] variaveis = valoresLidos(quantidade, vet);
            String e1 = scanner.nextLine();
            String e2 = alterarExpressao(e1, variaveis);
            System.out.println(resultado(e2));
            entrada();
        }
    }

    public static void main(String[] args) {
        entrada();
    }
}
