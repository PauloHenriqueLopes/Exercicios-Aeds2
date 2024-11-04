import java.util.*;

public class aeroporto {
    public static void main(String[] args) {
        Queue<String> filaOeste = new LinkedList<>();
        Queue<String> filaLeste = new LinkedList<>();
        Queue<String> filaNorte = new LinkedList<>();
        Queue<String> filaSul = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        int p = 0;

        while (true) {
            String comando = scanner.next();
            
            // Verifica se o comando é um inteiro que representa um ponto cardeal
            if (comando.matches("-?\\d+")) {
                p = Integer.parseInt(comando);
                
                // Verifica se o ponto cardeal é 0 para encerrar o programa
                if (p == 0) {
                    break;
                }
                
                // Valida o ponto cardeal
                if (p < -4 || p > -1) {
                    System.out.println("ponto invalido");
                    continue;
                }

            } else {
                // Se não for um número, então é um identificador de aeronave
                String identificador = comando;

                switch (p) {
                    case -1: // Oeste
                        filaOeste.add(identificador);
                        break;
                    case -2: // Sul
                        filaSul.add(identificador);
                        break;
                    case -3: // Norte
                        filaNorte.add(identificador);
                        break;
                    case -4: // Leste
                        filaLeste.add(identificador);
                        break;
                    default:
                        break;
                }
            }
        }

        // Monta a fila final
        List<String> filaFinal = new ArrayList<>();

        // Prioridade: Oeste
        while (!filaOeste.isEmpty()) {
            filaFinal.add(filaOeste.poll());
        }

        // Intercalar Norte e Sul
        while (!filaNorte.isEmpty() || !filaSul.isEmpty()) {
            if (!filaNorte.isEmpty()) {
                filaFinal.add(filaNorte.poll());
            }
            if (!filaSul.isEmpty()) {
                filaFinal.add(filaSul.poll());
            }
        }

        // Leste
        while (!filaLeste.isEmpty()) {
            filaFinal.add(filaLeste.poll());
        }

        // Imprimir a saída em uma única linha
        System.out.println(String.join(" ", filaFinal));

        scanner.close();
    }
}
