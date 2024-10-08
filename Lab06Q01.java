import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Lab06Q01 {
    public static void quickSortMedianPivo(int[] array, int esq, int dir) {
        if (esq < dir) {
            int i = esq, j = dir;
            int pivo = array[(dir + esq) / 2];
            while (i <= j) {
                while (array[i] < pivo) i++;
                while (array[j] > pivo) j--;
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }
            if (esq < j) quickSortMedianPivo(array, esq, j);
            if (i < dir) quickSortMedianPivo(array, i, dir);
        }
    }

    public static void quickSortRandomPivo(int[] array, int esq, int dir) {
        if (esq < dir) {
            Random random = new Random();
            int randomIndex = esq + random.nextInt(dir - esq + 1);
            int temp = array[randomIndex];
            array[randomIndex] = array[dir];
            array[dir] = temp;

            int i = esq, j = dir - 1;
            int pivo = array[dir];
            while (i <= j) {
                while (i <= j && array[i] < pivo) i++;
                while (i <= j && array[j] > pivo) j--;
                if (i <= j) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }
            temp = array[i];
            array[i] = array[dir];
            array[dir] = temp;

            quickSortRandomPivo(array, esq, i - 1);
            quickSortRandomPivo(array, i + 1, dir);
        }
    }

    public static void quickSortLastPivo(int[] array, int esq, int dir) {
        if (esq < dir) {
            int i = esq, j = dir;
            int pivo = array[dir];
            while (i <= j) {
                while (array[i] < pivo) i++;
                while (array[j] > pivo) j--;
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }
            if (esq < j) quickSortLastPivo(array, esq, j);
            if (i < dir) quickSortLastPivo(array, i, dir);
        }
    }

    public static void quickSortFirstPivo(int[] array, int esq, int dir) {
        if (esq < dir) {
            int i = esq, j = dir;
            int pivo = array[esq];
            while (i <= j) {
                while (array[i] < pivo) i++;
                while (array[j] > pivo) j--;
                if (i <= j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }
            if (esq < j) quickSortFirstPivo(array, esq, j);
            if (i < dir) quickSortFirstPivo(array, i, dir);
        }
    }

    public static void criaLog(String nome, int tam, double tempo, String tipoVetor) {
        String filename = nome + ".txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            DecimalFormat df = new DecimalFormat("#.######");
            String formattedDuration = df.format(tempo);
            writer.write("Array: " + tam + ", Tempo: " + formattedDuration + ", Tipo: " + tipoVetor + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Tamanho do vetor (ou digite FIM para sair): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("FIM")) {
                break;
            }

            int tam;
            try {
                tam = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número ou FIM.");
                continue;
            }

            int[] array = new int[tam];

            System.out.println("\nEscolha uma opção para o vetor: " +
                    "\n(1) Vetor Ordenado" +
                    "\n(2) Vetor Parcialmente ordenado" +
                    "\n(3) Vetor Aleatório");
            int vetor = scanner.nextInt();
            scanner.nextLine();

            String tipoVetor = "";

            switch (vetor) {
                case 1:
                    tipoVetor = "Ordenado";
                    for (int i = 0; i < tam; i++) {
                        array[i] = i;
                    }
                    break;
                case 2:
                    tipoVetor = "Parcialmente Ordenado";
                    for (int i = 0; i < tam; i++) {
                        array[i] = i;
                    }
                    Random random = new Random();
                    for (int i = 0; i < tam / 10; i++) {
                        int index1 = random.nextInt(tam);
                        int index2 = random.nextInt(tam);
                        int temp = array[index1];
                        array[index1] = array[index2];
                        array[index2] = temp;
                    }
                    break;
                case 3:
                    tipoVetor = "Desordenado";
                    random = new Random();
                    for (int i = 0; i < tam; i++) {
                        array[i] = random.nextInt(3000);
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            System.out.println("Escolha uma Opção: " +
                    "\n(1) Pivô na primeira posição" +
                    "\n(2) Pivô na última posição" +
                    "\n(3) Pivô aleatório" +
                    "\n(4) Pivô mediana");
            int opc = scanner.nextInt();
            scanner.nextLine();

            long startTime = System.nanoTime();
            switch (opc) {
                case 1:
                    quickSortFirstPivo(array, 0, tam - 1);
                    break;
                case 2:
                    quickSortLastPivo(array, 0, tam - 1);
                    break;
                case 3:
                    quickSortRandomPivo(array, 0, tam - 1);
                    break;
                case 4:
                    quickSortMedianPivo(array, 0, tam - 1);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0;

            DecimalFormat df = new DecimalFormat("#.######");
            String formattedDuration = df.format(duration);

            switch (opc) {
                case 1:
                    criaLog("quicksortFirstPivo", tam, duration, tipoVetor);
                    break;
                case 2:
                    criaLog("quicksortLastPivo", tam, duration, tipoVetor);
                    break;
                case 3:
                    criaLog("quicksortRandomPivo", tam, duration, tipoVetor);
                    break;
                case 4:
                    criaLog("quicksortMedianPivo", tam, duration, tipoVetor);
                    break;
            }

            System.out.println("Duração da execução: " + formattedDuration + " segundos");
        }

        System.out.println("Programa encerrado.");
        scanner.close();
    }
}
