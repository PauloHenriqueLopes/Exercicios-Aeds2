import java.util.*;

public class Lab01Q02 {
    public static int contaMaiusculaRecursivo (String string, int passo, int contador) {
        int tam = string.length();
        if(tam == passo) {
            return contador;
        } else if(Character.isUpperCase(string.charAt(passo))) {
            return contaMaiusculaRecursivo(string, (passo + 1), (contador + 1));
        } else {
            return contaMaiusculaRecursivo(string, (passo + 1), contador);

        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        List<Integer> list = new ArrayList<Integer>();

        do {
            string = scanner.nextLine();

            if(!string.equals("FIM")) {
                list.add(contaMaiusculaRecursivo(string, 0, 0));
            }
        } while (!(string.equals("FIM")));

        for(int cont : list) {
            System.out.println(cont);
        }

        scanner.close();
    }
}
