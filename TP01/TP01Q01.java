import java.util.*;

public class TP01Q01 {
    public static boolean palindromo(String string, int passo) {
        int tam = string.length();
        if((tam/2) == passo) {
            return true;
        } else if(string.charAt(passo) != string.charAt(tam - passo - 1)) {
            return false;
        } else {
            return palindromo(string, (passo + 1));
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        List<String> list = new ArrayList<String>();

        do{
            boolean palindromo = true;
            string = scanner.nextLine();
            if(!string.equals("FIM")) {
                palindromo = palindromo(string, 0);
                if(palindromo) {
                    list.add("SIM");
                } else {
                    list.add("NAO");
                }
            }
            
        } while(!(string.equals("FIM")));

        for (String palindromo : list) {
            System.out.println(palindromo);
        }

        scanner.close();
    }
}