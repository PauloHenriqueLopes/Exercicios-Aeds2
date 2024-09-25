import java.util.*;
import java.io.*;

public class Pokedex {
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
        private String captureDate;

        //construtores
        public Pokemon(int id, int generation, String name, String description, String[] types, String[] abilities, Double weight, Double height, int captureRate, Boolean isLegendary, String captureDate) {
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
            this.captureDate = "";
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
        public String getCaptureDate() {
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
        public void setCaptureDate(String capturDate) {
            this.captureDate = capturDate;
        }

        //clone
        public Pokemon clone() {
            return new Pokemon(this.id, this.generation, this.name, this.description, 
                               this.types.clone(), this.abilities.clone(), 
                               this.weight, this.height, this.captureRate, 
                               this.isLegendary, this.captureDate);
        }

        //imprimir
        public void imprimir(Pokemon pokemon) {
            System.out.print("[#" + pokemon.getId() + " -> " + pokemon.getName() + ": " + pokemon.getDescription() + " - ");
        
            // Imprimir tipos
            String[] tipos = pokemon.getTypes();
            System.out.print(Arrays.toString(tipos) + " - ");
        
            // Imprimir habilidades
            String[] habilidades = pokemon.getAbilities();
            System.out.print(Arrays.toString(habilidades) + " - ");
        
            // Imprimir outros atributos
            System.out.print(pokemon.getWeight() + "kg - " + pokemon.getHeight() + "m - " +
                             pokemon.getCaptureRate() + "% - " + pokemon.getIsLengendary() + 
                             " - " + pokemon.getGeneration() + " gen] - " + pokemon.getCaptureDate());
            System.out.println();
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
            System.out.println("Data de captura: ");
            this.captureDate = scanner.nextLine();
            scanner.close();
        }
    }

    public Pokemon tratamento(String[] parts) {
        int id = Integer.parseInt(parts[0]);
        int gen = Integer.parseInt(parts[1]);
        String name = parts[2];
        String description = parts[3];
        String type1 = parts[4];
        String type2 = parts[5];
        String[] types = new String[] {type1, type2};
        
        String hab1 = parts[6].replace("\"[", "");
        String hab2 = parts[7].replace("]\"", ""). replace(" ", "");
        String[] habilidades = new String[] {hab1, hab2};
        
        Double peso = Double.parseDouble(parts[8]);
        Double altura = Double.parseDouble(parts[9]);
        int captura = Integer.parseInt(parts[10]);
        Boolean lendario = parts[11].equals("0") ? false : true;
        String data = parts[12];

        return new Pokemon(id, gen, name, description, types, habilidades, peso, altura, captura, lendario, data);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> idsPokemons = new ArrayList<>();
        List<Pokemon> listaPokemons = new ArrayList<>();
        String entrada;
        
        do{
            entrada = scanner.nextLine();
            if(!entrada.equals("FIM")) {
                idsPokemons.add(entrada);
            }
        }while(!(entrada.equals("FIM")));

        Pokedex pokedex = new Pokedex();

        try(Scanner lerArquivo = new Scanner(new File("pokemon.csv"));) {
            lerArquivo.nextLine();
            while (lerArquivo.hasNextLine()) {
                String linha = lerArquivo.nextLine();
                String parts[] = linha.split(",");
                if(idsPokemons.contains(parts[0])){
                    listaPokemons.add(pokedex.tratamento(parts));
                }
            }
            lerArquivo.close();
        } catch(FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado: " + e.getMessage());
        }

        scanner.close();

        for (Pokemon pokemon : listaPokemons) {
            pokemon.imprimir(pokemon);
        }
    }
}