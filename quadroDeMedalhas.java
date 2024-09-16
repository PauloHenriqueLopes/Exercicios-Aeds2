import java.util.*;

public class quadroDeMedalhas {
    private String nome;
    private int ouro;
    private int prata;
    private int bronze;

    public quadroDeMedalhas(String nome, int ouro, int prata, int bronze) {
        this.nome = nome;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
    }

    public quadroDeMedalhas() {
        this.nome = "";
        this.ouro = 0;
        this.prata = 0;
        this.bronze = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getOuro() {
        return ouro;
    }

    public int getPrata() {
        return prata;
    }

    public int getBronze() {
        return bronze;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }

    public void setPrata(int prata) {
        this.prata = prata;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public static void organizaQuadro(List<quadroDeMedalhas> listaDeMedalhas) {
        listaDeMedalhas.sort((a, b) -> {
            if (b.getOuro() != a.getOuro()) return Integer.compare(b.getOuro(), a.getOuro());
            if (b.getPrata() != a.getPrata()) return Integer.compare(b.getPrata(), a.getPrata());
            return Integer.compare(b.getBronze(), a.getBronze());
        });

        for (quadroDeMedalhas medalha : listaDeMedalhas) {
            System.out.println(medalha.getNome() + " " + medalha.getOuro() + " " + medalha.getPrata() + " " + medalha.getBronze());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<quadroDeMedalhas> listaMedalhas = new ArrayList<>();

        int quantidade = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < quantidade; i++) {
            String entrada = scanner.nextLine();
            String[] partes = entrada.split(" ");
            if (partes.length == 4) {
                String nome = partes[0];
                int ouro = Integer.parseInt(partes[1]);
                int prata = Integer.parseInt(partes[2]);
                int bronze = Integer.parseInt(partes[3]);
                quadroDeMedalhas medalhas = new quadroDeMedalhas(nome, ouro, prata, bronze);
                listaMedalhas.add(medalhas);
            } else {
                System.out.println("Erro na entrada!");
            }
        }

        organizaQuadro(listaMedalhas);

        scanner.close();
    }
}
