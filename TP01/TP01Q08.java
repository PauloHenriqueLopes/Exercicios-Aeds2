import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TP01Q08 {

    private static String formataEntrada(String entrada) {
        entrada = entrada.trim();
        entrada = entrada.replace(',', '.');

        try {
            if (entrada.startsWith(".")) {
                entrada = "0" + entrada;
            }

            double numero = Double.parseDouble(entrada);

            return formatarNumero(numero);
        } catch (NumberFormatException e) {
            return entrada;
        }
    }

    private static String formatarNumero(double numero) {
        String formato = String.format("%.10f", numero).replace(',', '.');

        formato = formato.replaceAll("0*$", "");
        formato = formato.replaceAll("\\.$", "");

        return formato;
    }    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<String> numeros = new Stack<>();
        File arquivo = new File("tp01q08.txt");

        int repeticao = scanner.nextInt();
        scanner.nextLine();

        for(int i = 0; i < repeticao; i++) {
            String numero = scanner.nextLine();
            String formatado = formataEntrada(numero);
            numeros.push(formatado);
        }

        try(FileWriter fileWriter = new FileWriter(arquivo); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for(String num : numeros) {
                printWriter.println(num);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        while(!numeros.isEmpty()) {
            System.out.println(numeros.pop());
        }
    }
}
