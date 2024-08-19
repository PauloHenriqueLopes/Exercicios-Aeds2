import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tp01Q10 {
    public static Boolean palindromoRecursivo(String string, int passo) {
        int tam = string.length();
        
        if((tam/2) == passo) return true;
        else if(string.charAt(passo) == string.charAt(tam - passo - 1)) return palindromoRecursivo(string, (passo + 1));
        else return false;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Boolean> list = new ArrayList<Boolean>();
        String string;

        do {
            string = scanner.nextLine();
            if(!(string.equals("FIM"))) {
                Boolean resposta = palindromoRecursivo(string, 0);
                list.add(resposta);
            }
        } while (!(string.equals("FIM")));

        for(Boolean caso : list) {
            System.out.println(caso ? "SIM" : "NAO");
        }

        scanner.close();
    }
}
