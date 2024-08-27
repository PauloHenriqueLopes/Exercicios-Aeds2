import java.util.*;

public class Lab03Q01 {
    public static Boolean expressao(String string) {
        int tam = string.length();
        int abre = 0, fecha = 0;
        int primeiroFecha = -1, primeiroAbre = -1;
        char caracter;

        for(int i = 0; i < tam; i++) {
            caracter = string.charAt(i);
            if(caracter == '(') {
                abre++;
                if(primeiroAbre == -1) {
                    primeiroAbre = i;
                }
            }
            if(caracter == ')') {
                fecha++;
                if(primeiroFecha == -1) {
                    primeiroFecha = i;
                }
            }   
        }

        if(primeiroAbre > primeiroFecha){
            return false;
        }

        if(abre == fecha) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        Boolean resposta;

        do{
            string = scanner.nextLine();
            if(!string.equals("FIM")) {
                resposta = expressao(string);
                System.out.println(resposta ? "correto" : "incorreto");
            }
        }while(!string.equals("FIM"));

        scanner.close();
    }
}