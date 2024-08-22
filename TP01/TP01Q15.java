import java.util.*;

public class TP01Q15 {

    public static String verificarTipo(String string, boolean vogais, boolean consoantes, boolean inteiro, boolean real, int passo) {
        if (passo >= string.length()) {
            if (vogais) return "SIM NAO NAO NAO";
            if (consoantes) return "NAO SIM NAO NAO";
            if (inteiro && real) return "NAO NAO SIM SIM";
            if (real) return "NAO NAO NAO SIM";
            return "NAO NAO NAO NAO";
        }

        char c = string.charAt(passo);

        if (!isVogal(c)) {
            vogais = false;
        }

        if (!isConsoante(c)) {
            consoantes = false;
        }

        if (!Character.isDigit(c) && c != '.' && c != ',') {
            inteiro = false;
            real = false;
        } else if (c == '.' || c == ',') {
            inteiro = false;
            real = true;
        }

        return verificarTipo(string, vogais, consoantes, inteiro, real, passo + 1);
    }

    private static boolean isVogal(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }

    private static boolean isConsoante(char c) {
        return Character.isLetter(c) && !isVogal(c);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        List<String> list = new ArrayList<String>();
        int passo = 0;

        do {
            string = scanner.nextLine();
            if(!string.equals("FIM")) {
                list.add(verificarTipo(string, true, true, true, true, 0));
                System.out.println(list.get(passo));
                passo ++;
            }
        } while (!(string.equals("FIM")));

        scanner.close();
    }
}