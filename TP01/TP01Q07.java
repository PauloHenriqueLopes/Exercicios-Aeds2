import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TP01Q07 {
    public static String getHTML(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        int responseCode = -1;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } else {
                System.out.println("Erro na requisição. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    public static void conexao(String nome, String endereco) {
        String html = getHTML(endereco);
        
        if (html.isEmpty()) {
            System.out.println("Não foi possível obter o conteúdo da página.");
            return;
        }

        int a = encontra(html, 'a');
        int e = encontra(html, 'e');
        int i = encontra(html, 'i');
        int o = encontra(html, 'o');
        int u = encontra(html, 'u');
        int á = encontra(html, '\u00E1'); // á
        int é = encontra(html, '\u00E9'); // é
        int í = encontra(html, '\u00ED'); // í
        int ó = encontra(html, '\u00F3'); // ó
        int ú = encontra(html, '\u00FA'); // ú
        int à = encontra(html, '\u00E0'); // à
        int è = encontra(html, '\u00E8'); // è
        int ì = encontra(html, '\u00EC'); // ì
        int ò = encontra(html, '\u00F2'); // ò
        int ù = encontra(html, '\u00F9'); // ù
        int ã = encontra(html, '\u00E3'); // ã
        int õ = encontra(html, '\u00F5'); // õ
        int â = encontra(html, '\u00E2'); // â
        int ê = encontra(html, '\u00EA'); // ê
        int î = encontra(html, '\u00EE'); // î
        int ô = encontra(html, '\u00F4'); // ô
        int û = encontra(html, '\u00FB'); // û

        int consoantes = encontraConsoante(html);
        int br = encontraBr(html);
        int table = encontraTable(html);
        int nomePagina = encontraNome(html, nome);

        System.out.printf("a(%d) e(%d) i(%d) o(%d) u(%d) á(%d) é(%d) í(%d) ó(%d) ú(%d) à(%d) è(%d) ì(%d) ò(%d) ù(%d) ã(%d) õ(%d) â(%d) ê(%d) î(%d) ô(%d) û(%d) consoante(%d) <br>(%d) <table>(%d) nomepágina(%d)\n",
            a, e, i, o, u, á, é, í, ó, ú, à, è, ì, ò, ù, ã, õ, â, ê, î, ô, û, consoantes, br, table, nome.length());
    }

    private static int encontra(String texto, char caracter) {
        int count = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == caracter) {
                count++;
            }
        }
        return count;
    }

    private static int encontraConsoante(String texto) {
        String vogais = "AEIOUaeiou";
        int count = 0;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isLetter(c) && vogais.indexOf(c) == -1) {
                count++;
            }
        }
        return count;
    }

    private static int encontraBr(String texto) {
        return encontraSubtexto(texto, "<br>");
    }

    private static int encontraTable(String texto) {
        return encontraSubtexto(texto, "<table>");
    }

    private static int encontraSubtexto(String texto, String subtexto) {
        int count = 0;
        int index = 0;

        while ((index = texto.indexOf(subtexto, index)) != -1) {
            count++;
            index += subtexto.length();
        }

        return count;
    }

    private static int encontraNome(String texto, String nome) {
        return encontraSubtexto(texto, nome);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        String nome;
        String endereco;
    
        while (true) {
            nome = scanner.nextLine();
            if (nome.equals("FIM")) break;
            
            endereco = scanner.nextLine();
            if (endereco.equals("FIM")) break;
            
            conexao(nome, endereco);
        }
    
        scanner.close();
    }
}
