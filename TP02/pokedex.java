import java.util.*;
import java.io.*;

public class pokedex {
    public class Pokemon {
        private int id;
        private int generation;
        private String name;
        private String description;
        private String[] types;
        private String[] abilities;
        private Double weight;
        private Double height;
        private int captureRate;
        private Boolean isLegendary;
        private Date captureDate;

        //construtores
        public Pokemon(int id, int generation, String name, String description, String[] types, String[] abilities, Double weight, Double height, int captureRate, Boolean isLegendary, Date captureDate) {
            this.id = id;
            this.generation = generation;
            this.name = name;
            this.description = description;
            this.types = types;
            this.abilities = abilities;
            this.weight = weight;
            this.height = height;
            this.captureRate = captureRate;
            this.isLegendary = isLegendary;
            this.captureDate = captureDate;
        }

        public Pokemon() {
            this.id = 0;
            this.generation = 0;
            this.name = "";
            this.description = "";
            this.types = new String[0];
            this.abilities = new String[0];
            this.weight = 0.0;
            this.height = 0.0;
            this.captureRate = 0;
            this.isLegendary = false;
            this.captureDate = new Date();
        }


        //gets
        public int getId() {
            return id;
        }
        public int getGeneration() {
            return generation;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String[] getTypes() {
            return types;
        }
        public String[] getAbilities() {
            return abilities;
        }
        public Double getWeight() {
            return weight;
        }
        public Double getHeight() {
            return height;
        }
        public int getCaptureRate() {
            return captureRate;
        }
        public Boolean getIsLengendary() {
            return isLegendary;
        }
        public Date getCapDate() {
            return captureDate;
        }


        //sets
        public void setId(int id) {
            this.id = id;
        }
        public void setGeneration(int generation) {
            this.generation = generation;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setTypes(String[] types) {
            this.types = types;
        }
        public void setAbilities(String[] abilities) {
            this.abilities = abilities;
        }
        public void serWeight(Double weight) {
            this.weight = weight;
        }
        public void setHeight(Double height) {
            this.height = height;
        }
        public void setCaptureRate(int captureRate) {
            this.captureRate = captureRate;
        }
        public void setIsLegendary(Boolean isLegendary) {
            this.isLegendary = isLegendary;
        }
        public void setCaptureDate(Date capturDate) {
            this.captureDate = capturDate;
        }

        //clone
        public Pokemon clone() {
            return new Pokemon(this.id, this.generation, this.name, this.description, 
                               this.types.clone(), this.abilities.clone(), 
                               this.weight, this.height, this.captureRate, 
                               this.isLegendary, (Date) this.captureDate.clone());
        }

        //imprimir
        public void imprimir(Pokemon pokemon) {
            System.out.println("[#" + pokemon.getId() + " -> " + pokemon.getName() + ": " + pokemon.getDescription() + " - " + getTypes() + " - " +
            pokemon.getAbilities() + " - " + pokemon.getWeight() + "kg - " + pokemon.getHeight() + "m - " + pokemon.getCaptureRate() + "% - "
             + pokemon.getIsLengendary() + " - " + pokemon.getGeneration() + "gen] - " + pokemon.getCapDate());
        }

        //ler
        public void ler() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID do pokemon: ");
            this.id = scanner.nextInt();
            System.out.println("Geração do pokemon: ");
            this.generation = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Nome do pokemon: ");
            this.name = scanner.nextLine();
            System.out.println("Descrição do pokemon: ");
            this.description = scanner.nextLine();
            System.out.println("Tipo(s) do pokemon: ");
            this.types = scanner.nextLine().split(",");
            System.out.println("Abilidades do pokemon: ");
            this.abilities = scanner.nextLine().split(",");
            System.out.println("Peso do pokemon: ");
            this.weight = scanner.nextDouble();
            System.out.println("Tamanho do pokemon: ");
            this.height = scanner.nextDouble();
            System.out.println("Chance de captura: ");
            this.captureRate = scanner.nextInt();
            System.out.println("É um pokemon Lendario? ");
            this.isLegendary = scanner.nextBoolean();
            this.captureDate = new Date();
            scanner.close();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> idsPokemons = new ArrayList<>();
        String entrada;
        
        do{
            entrada = scanner.nextLine();
            if(!entrada.equals("FIM")) {
                int id = Integer.parseInt(entrada);
                idsPokemons.add(id);
            }
        }while(!(entrada.equals("FIM")));


        try(Scanner lerArquivo = new Scanner(new File("/tmp/pokemon.csv"));) {
            while (lerArquivo.hasNextLine()) {
                String linha = lerArquivo.nextLine();
                System.out.println(linha);
            }
            lerArquivo.close();
        } catch(FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado: " + e.getMessage());
        }

        scanner.close();
    }
}