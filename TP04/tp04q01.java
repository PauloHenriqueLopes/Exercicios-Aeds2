import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Pokemon {
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
    public Pokemon(int id, int generation, String name, String description, String[] types, String[] abilities,
     Double weight, Double height, int captureRate, Boolean isLegendary, String captureDate) {
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
    public void setWeight(Double weight) {
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
        String tiposFormatados = Arrays.toString(tipos).replace("[", "").replace("]", "");
        System.out.print("[" + tiposFormatados + "] - ");
    
        // Imprimir habilidades
        String[] habilidades = pokemon.getAbilities();
        String habilidadesFormatadas = Arrays.toString(habilidades).replace("[", "").replace("]", "");
        System.out.print("[" + habilidadesFormatadas + "] - ");
    
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

class No {
    public Pokemon pokemon;
    public No dir, esq;

    public No(Pokemon pokemon) {
        this(pokemon, null, null);
    }

    public No(Pokemon pokemon, No dir, No esq) {
        this.pokemon = pokemon;
        this.dir = dir;
        this.esq = esq;
    }
}

class ArvoreBinaria {
    public No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public boolean pesquisar(String name) {
        System.out.print("=>raiz ");
        return pesquisar(name, raiz);
    }

    private boolean pesquisar(String name, No i) {
        boolean resp;
        if(i == null) {
            resp = false;
        } else if (name.equals(i.pokemon.getName())) {
            resp = true;
        } else if (name.compareTo(i.pokemon.getName()) < 0) {
            System.out.print("esq ");
            resp = pesquisar(name, i.esq);
        } else {
            System.out.print("dir ");
            resp = pesquisar(name, i.dir);
        }
        return resp;
    }

    public void inserir(Pokemon pokemon) throws Exception {
        raiz = inserir(pokemon, raiz);
    }

    private No inserir(Pokemon pokemon, No i) throws Exception {
        if( i == null) {
            i = new No(pokemon);
        } else if(pokemon.getName().compareTo(i.pokemon.getName()) < 0) {
            i.esq = inserir(pokemon, i.esq);
        } else if(pokemon.getName().compareTo(i.pokemon.getName()) > 0) {
            i.dir = inserir(pokemon, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }
}



public class tp04q01 {
    public static Pokemon parsePokemon(String[] parts) {
        try {
            int id = Integer.parseInt(parts[0].trim());
            int gen = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            String description = parts[3].trim();
            String type1 = parts[4].trim();
            String type2 = parts[5].trim();
            String[] types = type2.isEmpty() ? new String[]{"'" + type1 + "'"} : new String[]{"'" + type1 + "'", "'" + type2 + "'"};
            String habilidadesString = parts[6].replace(".", ",").replace("[", "").replace("]", "").trim();
            String[] habilidades = habilidadesString.split(",");  
            Double peso = parts[7].trim().equals("0") ? 0.0 : Double.parseDouble(parts[7].trim());
            Double altura = parts[8].trim().equals("0") ? 0.0 : Double.parseDouble(parts[8].trim());
            int captura = Integer.parseInt(parts[9].trim());
            Boolean lendario = parts[10].trim().equals("1");
            String data = parts[11].trim();
    
            return new Pokemon(id, gen, name, description, types, habilidades, peso, altura, captura, lendario, data);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao processar o Pokémon com dados: " + Arrays.toString(parts));
            e.printStackTrace();
            return null;
        }
    }

    public static void lerNomes(List<String> nomesPokemons, Scanner scanner) {
        String nome;
        do{
            nome = scanner.nextLine();
            if(!nome.equals("FIM")) {
                nomesPokemons.add(nome);
            }
        }while(!(nome.equals("FIM")));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> requestedIds = new ArrayList<>();

        ArvoreBinaria arvore = new ArvoreBinaria();
        List<Pokemon> listaPokemon = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();

            if(input.equals("FIM")) break;

            requestedIds.add(input);
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader("/tmp/pokemon.csv"));

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                line = line.replace("\",,,", "\", , ,");
                line = line.replace(",,", ", ,");
                line = line.replace("\"", "");
                line = line.replace(", ", ".");
                line = line.replace(",", ";");
                line = line.replace(".;[", "[; ;");
                line = line.replace("]..;", "];0.0;0.0;");
                line = line.replace("].; ;", "];0.0;0.0;");

                String[] parts = line.split(";");

                Pokemon novoPokemon = parsePokemon(parts);
                listaPokemon.add(novoPokemon);
            }
            reader.close();
        } catch(IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        for (int i = 0; i < requestedIds.size(); i++) {
            for (Pokemon j : listaPokemon) {
                if (Integer.parseInt(requestedIds.get(i)) == j.getId()) {
                    try {
                        arvore.inserir(j);
                    } catch (Exception e) {
                        System.err.println("Erro ao inserir Pokémon na árvore: " + e.getMessage());
                    }
                }
            }
        }

        List<String> nomesPokemons = new ArrayList<>();
        lerNomes(nomesPokemons, scanner);

        for(String nome : nomesPokemons) {
            boolean possui;
            System.out.println(nome);
            possui = arvore.pesquisar(nome);
            System.out.println(possui ? "SIM" : "NAO");
        }
    }
}