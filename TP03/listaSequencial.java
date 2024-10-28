import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

class Celula {
    public Pokemon pokemon;
    public Celula prox;

    public Celula(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.prox = null;
    }
}

class listaPokemon {
    private Celula primeiro;
    private Celula ultimo;

    public listaPokemon() {
        primeiro = new Celula(null);
        ultimo = primeiro;
    }

    public void inserirInicio(Pokemon pokemon) {
        Celula nova = new Celula(pokemon);
        nova.prox = primeiro.prox;
        primeiro.prox = nova;
        if (primeiro == ultimo) {
            ultimo = nova;
        }
    }

    public void inserir(Pokemon pokemon, int pos) throws Exception {
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho) {
            throw new Exception("Erro ao inserir posição (" + pos + " / tamanho = " + tamanho + ") inválida!");
        } else if (pos == 0) {
            inserirInicio(pokemon);
        } else if (pos == tamanho) {
            inserirFim(pokemon);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            Celula nova = new Celula(pokemon);   
            nova.prox = i.prox;
            i.prox = nova;
        }
    }

    public void inserirFim(Pokemon pokemon) {
        Celula nova = new Celula(pokemon);
        ultimo.prox = nova;
        ultimo = nova;
    }

    public Pokemon removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        if (tmp == ultimo) {
            ultimo = primeiro;
        }
        tmp.prox = null;
        return tmp.pokemon;
    }

    public Pokemon remover(int pos) throws Exception {
        int tamanho = tamanho();
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
        } else if (pos == 0) {
            return removerInicio();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = i.prox;
            i.prox = tmp.prox;
            if (tmp == ultimo) {
                ultimo = i;
            }
            tmp.prox = null;
            return tmp.pokemon;
        }
    }

    public Pokemon removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");
        }
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);
        Celula tmp = ultimo;
        ultimo = i;
        i.prox = null;
        return tmp.pokemon;
    }

    public void mostrar() {
        int pos = 0;
        for(Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.print("[" + pos + "] ");
            i.pokemon.imprimir(i.pokemon);
            pos++;
        }
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro; i.prox != null; i = i.prox, tamanho++);
        return tamanho;
    }
}

public class listaSequencial {
    public static Pokemon parsePokemon(String[] parts) {
        try {
            int id = Integer.parseInt(parts[0].trim());
            int gen = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            String description = parts[3].trim();
            String type1 = parts[4].trim();
            String type2 = parts[5].trim();
            String[] types = type2.isEmpty() ? new String[]{type1} : new String[]{type1, type2};
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> requestedIds = new ArrayList<>();

        listaPokemon lista = new listaPokemon();

        while (true) {
            String input = scanner.nextLine();

            if(input.equals("FIM")) break;

            requestedIds.add(input);
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader("pokemon.csv"));

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
                if(requestedIds.contains(String.valueOf(novoPokemon.getId()))) {
                    lista.inserirFim(novoPokemon);
                }
            }
            reader.close();
        } catch(IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        int n = scanner.nextInt();
        scanner.nextLine();

        for(int i = 0; i < n; i++) {
            String command = scanner.next();

            if(command.equals("II")) {
                String idProcurado = scanner.next();
                scanner.nextLine();
                boolean encontrado = false;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(("pokemon.csv")));
                    
                    String line;

                    while((line = reader.readLine()) != null) {
                        line = line.replace("\",,,", "\", , ,");
                        line = line.replace(",,", ", ,");
                        line = line.replace("\"", "");
                        line = line.replace(", ", ".");
                        line = line.replace(",", ";");
                        line = line.replace(".;[", "[; ;");
                        line = line.replace("]..;", "];0.0;0.0;");
                        line = line.replace("].; ;", "];0.0;0.0;");

                        String[] parts = line.split(";");

                        String idLido = parts[0];

                        if(idLido.equals(idProcurado)) {
                            Pokemon novoPokemon = parsePokemon(parts);
                            lista.inserirInicio(novoPokemon);
                            encontrado = true;
                            break;
                        }
                    }
                    reader.close();

                    if (!encontrado) {
                        System.err.println("Pokemon com o ID '" + idProcurado + "' não encontrado.");
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
                }
            } else if(command.equals("IF")) {
                String idProcurado = scanner.next();
                scanner.nextLine();
                boolean encontrado = false;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(("pokemon.csv")));
                    
                    String line;

                    while((line = reader.readLine()) != null) {
                        line = line.replace("\",,,", "\", , ,");
                        line = line.replace(",,", ", ,");
                        line = line.replace("\"", "");
                        line = line.replace(", ", ".");
                        line = line.replace(",", ";");
                        line = line.replace(".;[", "[; ;");
                        line = line.replace("]..;", "];0.0;0.0;");
                        line = line.replace("].; ;", "];0.0;0.0;");

                        String[] parts = line.split(";");

                        String idLido = parts[0];

                        if(idLido.equals(idProcurado)) {
                            Pokemon novoPokemon = parsePokemon(parts);
                            lista.inserirFim(novoPokemon);
                            encontrado = true;
                            break;
                        }
                    }
                    reader.close();

                    if (!encontrado) {
                        System.err.println("Pokemon com o ID '" + idProcurado + "' não encontrado.");
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
                }
            } else if(command.equals("I*")) {
                int pos = scanner.nextInt();
                String idProcurado = scanner.next();
                scanner.nextLine();
                boolean encontrado = false;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(("pokemon.csv")));
                    
                    String line;

                    while((line = reader.readLine()) != null) {
                        line = line.replace("\",,,", "\", , ,");
                        line = line.replace(",,", ", ,");
                        line = line.replace("\"", "");
                        line = line.replace(", ", ".");
                        line = line.replace(",", ";");
                        line = line.replace(".;[", "[; ;");
                        line = line.replace("]..;", "];0.0;0.0;");
                        line = line.replace("].; ;", "];0.0;0.0;");

                        String[] parts = line.split(";");

                        String idLido = parts[0];

                        if(idLido.equals(idProcurado)) {
                            Pokemon novoPokemon = parsePokemon(parts);
                            try {
                                lista.inserir(novoPokemon, pos);
                            } catch (Exception e) {
                                System.err.println("Erro ao inserir Pokemon na posição " + pos + ": " + e.getMessage());
                            }
                            encontrado = true;
                            break;
                        }
                    }
                    reader.close();

                    if (!encontrado) {
                        System.err.println("Pokemon com o ID '" + idProcurado + "' não encontrado.");
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
                }
            } else if (command.equals("RI")) {
                try {
                    Pokemon removido = lista.removerInicio();
                    System.out.println("(R)" + removido.getName());
                } catch (Exception e) {
                    System.err.println("Erro ao remover do início: " + e.getMessage());
                }
            } else if (command.equals("R*")) {
                int pos = scanner.nextInt();
                scanner.nextLine(); // Para consumir a nova linha
                try {
                    Pokemon removido = lista.remover(pos);
                    System.out.println("(R)" + removido.getName());
                } catch (Exception e) {
                    System.err.println("Erro ao remover na posição " + pos + ": " + e.getMessage());
                }
            } else if (command.equals("RF")) {
                try {
                    Pokemon removido = lista.removerFim();
                    System.out.println("(R)" + removido.getName());
                } catch (Exception e) {
                    System.err.println("Erro ao remover do fim: " + e.getMessage());
                }
            }
            
        }

        scanner.close();
        lista.mostrar();
    }
}