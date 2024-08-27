import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI; 
import java.net.URISyntaxException;


public class TP01Q07 {
    public static void pesquisaHtml(String nome, String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try{
                desktop.browse(new URI(url));
            } catch(IOException | URISyntaxException) {
                e.printStackTrace();
            }
        } else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String nome;
        String url;

        do{
            nome = scanner.nextLine();
            if(!nome.equals("FIM")) {
                url = scanner.nextLine();
                if(!url.equals("FIM")){
                    pesquisaHtml(nome, url);
                }
            }
        }while(!nome.equals("FIM") && !url.equals("FIM"));

        scanner.close();
    }
}