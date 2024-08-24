import java.util.*;

public class Lab02Q02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int inicio, fim;
        StringBuilder contagem = new StringBuilder();

        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }

            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }

            String[] partes = input.split(" ");
            if (partes.length != 2) {
                System.out.println("Erro, digite dois números separados por espaço");
                continue;
            }

            try {
                inicio = Integer.parseInt(partes[0]);
                fim = Integer.parseInt(partes[1]);
            } catch (NumberFormatException e) {
                System.out.println("Erro, digite números válidos");
                continue;
            }

            if (fim < inicio) {
                System.out.println("Erro, fim menor que o inicio");
                continue;
            }

            contagem.setLength(0);

            for (int i = inicio; i <= fim; i++) {
                contagem.append(i);
            }

            for (int i = fim; i >= inicio; i--) {
                String reversed = new StringBuilder(String.valueOf(i)).reverse().toString();
                contagem.append(reversed);
            }

            System.out.println(contagem.toString());
        }

        scanner.close();
    }
}
