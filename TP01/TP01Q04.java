import java.util.*;

public class TP01Q04 {
    public static String alteracaoAleatoria(String string, char x, char y) {
        String stringAlterada = string.replaceAll(x, y);
        return stringAlterada;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<String>();
        Random gerador = new Random();
        gerador.setSeed(4);

        char x = (char)('a'+(Math.abs(gerador.nextInt() % 26)));
        System.out.println(x);
        char y = (char)('a'+(Math.abs(gerador.nextInt() % 26)));
        System.out.println(y);
        String string;

        do {

            string = scanner.nextLine();
            if(!string.equals("FIM")) {

                list.add(alteracaoAleatoria(string, x, y));
            }
        } while (!(string.equals("FIM")));

        for (String palavra : list) {
            System.out.println(palavra);
        }

        scanner.close();
    }
}
