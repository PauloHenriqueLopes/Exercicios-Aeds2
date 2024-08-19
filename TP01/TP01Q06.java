import java.util.Scanner;

public class TP01Q06 {
    public static boolean vogal(String string) {
        char caracter;
        String vogais = "AEIOUaeiou";
        int tam = string.length();

        for(int i = 0; i < tam; i++) {
            caracter = string.charAt(i);
            if(vogais.indexOf(caracter) == -1) return false;
        }
        return true;
    }

    public static boolean consoante(String string) {
        char caracter;
        String vogais = "AEIOUaeiou";
        int tam = string.length();

        for(int i = 0; i < tam; i++) {
            caracter = string.charAt(i);
            if(vogais.indexOf(caracter) != -1 || Character.isDigit(caracter)) return false;
        }

        return true;
    }

    public static boolean inteiro(String string) {
        char caracter;
        int tam = string.length();

        for(int i = 0; i < tam; i++) {
            caracter = string.charAt(i);
            if(caracter == '.' || caracter == ',' || Character.isAlphabetic(caracter)) return false;
        }
        return true;
    }

    public static boolean real(String string) {
        try {
            if (string.startsWith(".") || string.startsWith(",")) {
                string = "0" + string;
            }

            string = string.replace(',', '.');
            Double.parseDouble(string);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;

        do {
            string = scanner.nextLine();
            if (!string.equals("FIM")) {
                boolean vogal = vogal(string);
                boolean consoante = !vogal && consoante(string);
                boolean inteiro = inteiro(string);
                boolean real = real(string);
                System.out.println((vogal ? "SIM" : "NAO") + ", " +(consoante ? "SIM" : "NAO") + ", " + (inteiro ? "SIM" : "NAO") + ", " + (real ? "SIM" : "NAO"));
            }
        } while (!string.equals("FIM"));

        scanner.close();
    }
}