import java.util.*;

public class treinoprovaTeorica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();
        scanner.nextLine();

        int[] entrada = new int[quantidade];
        
        for(int i = 0; i < quantidade; i++) {
            entrada[i] = scanner.nextInt();
            scanner.nextLine();
        }

        for(int  i = 1; i < quantidade; i++) {
            int  j = i - 1;
            int chave = entrada[i];

            if(chave % 2 == 0) {
                while (j >= 0 && ((entrada[j] > chave) || (entrada[j] % 2 == 1))) {
                    entrada[j + 1] = entrada[j];
                    j--;
                }
            }
            entrada[j + 1] = chave;
        }

        for(int i : entrada){
            System.out.print(i + " ");
        }

        scanner.close();
    }
}