import java.util.*;

public class Lab02Q02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int inicio, fim;
        String contagem = "";

        while(true) {
            input = scanner.nextLine();

            if(input.trim().isEmpty()) {
                break;
            }

            try {
                inicio = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Erro, digite um numero");
                break;
            }
            
            input = scanner.nextLine();

            if(input.trim().isEmpty()) {
                break;
            }

            try {
                fim = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Erro, digite um numero");
                break;
            }

            if(fim < inicio) {
                System.out.println("Erro, fim menor que o inicio");
                break;
            }

            for(int i = inicio; i <= fim; i++) {
                contagem = contagem + i + "";
            }

            for(int i = fim; i >= inicio; i--) {
                contagem = contagem + i + "";
            }

            System.out.println(contagem);
            contagem = "";
        }

        scanner.close();
    }
}
