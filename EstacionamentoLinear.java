import java.util.*;

public class EstacionamentoLinear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            if (n == 0 && k == 0) break;
            
            // Listas para armazenar os horários de entrada e saída
            int[] entrada = new int[n];
            int[] saida = new int[n];
            
            for (int i = 0; i < n; i++) {
                entrada[i] = scanner.nextInt();
                saida[i] = scanner.nextInt();
            }
            
            // Simulação do estacionamento usando uma pilha
            Stack<Integer> estacionamento = new Stack<>();
            boolean possivel = true;
            
            for (int i = 0; i < n; i++) {
                // Enquanto houver carros na pilha que podem sair antes da entrada
                while (!estacionamento.isEmpty() && estacionamento.peek() <= entrada[i]) {
                    estacionamento.pop();
                }
                
                // Adiciona o carro à pilha (horário de saída)
                estacionamento.push(saida[i]);
                
                // Verifica se a pilha excede a capacidade
                if (estacionamento.size() > k) {
                    possivel = false;
                    break;
                }
            }
            
            System.out.println(possivel ? "Sim" : "Nao");
        }
        
        scanner.close();
    }
}
