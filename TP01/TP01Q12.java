import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TP01Q12 {public static StringBuilder ciframento(String string) {
        return ciframentoHelper(string, 0, new StringBuilder());
    }

    private static StringBuilder ciframentoHelper(String string, int index, StringBuilder criptografia) {
        if (index >= string.length()) {
            return criptografia;
        }

        char caracter = string.charAt(index);
        if (caracter >= 32 && caracter <= 126) {
            caracter = (char) (caracter + 3);
            if (caracter > 126) {
                caracter = (char) (caracter - 94);
            }
            criptografia.append(caracter);
        }

        return ciframentoHelper(string, index + 1, criptografia);
    }

    private static void processInputs(List<String> list) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        if (!string.equals("FIM")) {
            list.add(ciframento(string).toString());
            processInputs(list);
        } 
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        processInputs(list);

        for (String ciframento : list) {
            System.out.println(ciframento);
        }
    }
}
