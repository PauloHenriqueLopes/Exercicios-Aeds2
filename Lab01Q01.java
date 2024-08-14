import java.util.*;

public class Lab01Q01 {
    public static int contaMaiuscula(String string) {
        int tam = string.length();
        int contador = 0;

        for(int i = 0; i < tam; i++) {
            if(Character.isUpperCase(string.charAt(i))) {
                contador++;
            }
        }

        return contador;
    }
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        List<Integer> list = new ArrayList<Integer>();

        do {
            string = scanner.nextLine();
            if(!string.equals("FIM")) {
                list.add(contaMaiuscula(string));
            }
        } while (!(string.equals("FIM")));

        for(int cont : list) {
            System.out.println(cont);
        }

        scanner.close();
    }

}