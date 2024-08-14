import java.util.*;

public class TP01Q03 {

    public static StringBuilder ciframento(String string) {
        StringBuilder criptografia = new  StringBuilder();
        char caracter;

        for(int i = 0; i < (string.length()); i++) {
            caracter = string.charAt(i);
            if(caracter >= 32 && caracter <= 126) {
                caracter = (char) (caracter + 3);
                if(caracter > 126) {
                    caracter = (char) (caracter - 94);
                }
                criptografia.append(caracter);
            }
        }

        return criptografia;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<String>();
        String string;
        
        do {
            string = scanner.nextLine();
            if(!string.equals("FIM")){
                list.add(ciframento(string).toString());
            }
        } while (!(string.equals("FIM")));

        for(String ciframento : list) { 
            System.out.println(ciframento);
        }

        scanner.close();
    }
}
