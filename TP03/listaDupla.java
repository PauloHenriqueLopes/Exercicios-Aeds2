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

class CelulaDupla {
    public Pokemon pokemon;
    public CelulaDupla prox;
    public CelulaDupla ant;

    public CelulaDupla(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.prox = null;
        this.ant = null;
    }
}

class listaPokemon {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    public listaPokemon() {
        primeiro = new CelulaDupla(null);
        ultimo = primeiro;
    }

    public void inserirInicio(Pokemon pokemon) {
        CelulaDupla nova = new CelulaDupla(pokemon);
        if (primeiro == null) {
            primeiro = nova;
            ultimo = nova;
        } else {
            nova.prox = primeiro;
            primeiro.ant = nova;
            primeiro = nova;
        }
    }

    public void inserirFim(Pokemon pokemon) {
        CelulaDupla nova = new CelulaDupla(pokemon);
        if (ultimo == null) {
            primeiro = nova;
            ultimo = nova;
        } else {
            ultimo.prox = nova;
            nova.ant = ultimo;
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
            CelulaDupla i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            CelulaDupla nova = new CelulaDupla(pokemon);   
            nova.prox = i.prox;
            nova.prox.ant = nova;
            nova.ant = i;
            i.prox = nova;
        }
    }

    public Pokemon removerInicio() throws Exception {
        if (primeiro == null) {
            throw new Exception("Erro ao remover (vazia)!");
        }
        Pokemon removido = primeiro.pokemon;
        primeiro = primeiro.prox;
        if (primeiro != null) {
            primeiro.ant = null;
        } else {
            ultimo = null;
        }
        return removido;
    }

    public Pokemon removerFim() throws Exception {
        if (ultimo == null) {
            throw new Exception("Erro ao remover (vazia)!");
        }
        Pokemon removido = ultimo.pokemon;
        ultimo = ultimo.ant;
        if (ultimo != null) {
            ultimo.prox = null;
        } else {
            primeiro = null;
        }
        return removido;
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
            CelulaDupla i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            CelulaDupla tmp = i.prox;
            i.prox = tmp.prox;
            tmp.prox.ant = i;
            if (tmp == ultimo) {
                ultimo = i;
            }
            tmp.prox = null;
            tmp.ant = null;
            return tmp.pokemon;
        }
    }

    public void mostrar() {
        int pos = 0;
        for(CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            System.out.print("[" + pos + "] ");
            i.pokemon.imprimir(i.pokemon);
            pos++;
        }
    }

    public int tamanho() {
        int tamanho = 0;
        for (CelulaDupla i = primeiro; i.prox != null; i = i.prox, tamanho++);
        return tamanho;
    }

    public void quickSort() {
        quickSort(primeiro.prox, ultimo);
    }
    
    private void quickSort(CelulaDupla low, CelulaDupla high) {
        if (low != null && high != null && low != high && low != high.prox) {
            CelulaDupla pivot = partition(low, high);
            quickSort(low, pivot.ant); 
            quickSort(pivot.prox, high);
        }
    }
    
    private CelulaDupla partition(CelulaDupla low, CelulaDupla high) {
        Pokemon pivot = high.pokemon;
        CelulaDupla i = low.ant;
    
        for (CelulaDupla j = low; j != high; j = j.prox) {
            if (comparePokemons(j.pokemon, pivot) < 0) {
                i = (i == null) ? low : i.prox;
                swap(i, j);
            }
        }
        i = (i == null) ? low : i.prox; 
        swap(i, high);
        return i;
    }
    
    private int comparePokemons(Pokemon p1, Pokemon p2) {
        if (p1.getGeneration() != p2.getGeneration()) {
            return Integer.compare(p1.getGeneration(), p2.getGeneration());
        }
        return p1.getName().compareTo(p2.getName());
    }
    
    private void swap(CelulaDupla a, CelulaDupla b) {
        Pokemon temp = a.pokemon;
        a.pokemon = b.pokemon;
        b.pokemon = temp;
    }
}

public class listaDupla {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> requestedIds = new ArrayList<>();

        listaPokemon lista = new listaPokemon();
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

        for(int i = 0; i < requestedIds.size(); i++) {
            for(Pokemon j : listaPokemon) {
                if(Integer.parseInt(requestedIds.get(i)) == j.getId()) {
                    lista.inserirFim(j);
                }
            }
        }

        // int n = scanner.nextInt();
        // scanner.nextLine();
   
        // for(int i = 0; i < n; i++) {
        //     String command = scanner.next();
        //     int idProcurado;

        //     if(command.equals("II")) {
        //         idProcurado = scanner.nextInt();
        //         scanner.nextLine();
                
        //         for(Pokemon pokemon : listaPokemon) {
        //             if(pokemon.getId() == idProcurado) {
        //                 lista.inserirInicio(pokemon);
        //                 break;
        //             }
        //         }
        //     } else if(command.equals("IF")) {
        //         idProcurado = scanner.nextInt();
        //         scanner.nextLine();
        
        //         for(Pokemon pokemon : listaPokemon) {
        //             if(pokemon.getId() == idProcurado) {
        //                 lista.inserirFim(pokemon);
        //                 break;
        //             }
        //         }
        //     } else if(command.equals("I*")) {
        //         int pos = scanner.nextInt();
        //         idProcurado = scanner.nextInt();
        //         scanner.nextLine();
                
        //         boolean encontrado = false;
        //         for(Pokemon pokemon : listaPokemon) {
        //             if(pokemon.getId() == idProcurado) {
        //                 try {
        //                     lista.inserir(pokemon, pos); 
        //                     encontrado = true;
        //                 } catch (Exception e) {
        //                     System.err.println("Erro ao inserir na posição " + pos + ": " + e.getMessage());
        //                 }
        //                 break;
        //             }
        //         }
        //         if (!encontrado) {
        //             System.out.println("Pokémon com ID " + idProcurado + " não encontrado.");
        //         }
        //     } else if (command.equals("RI")) {
        //         try {
        //             Pokemon removido = lista.removerInicio();
        //             System.out.println("(R) " + removido.getName());
        //         } catch (Exception e) {
        //             System.err.println("Erro ao remover do início: " + e.getMessage());
        //         }
        //     } else if (command.equals("R*")) {
        //         int pos = scanner.nextInt();
        //         scanner.nextLine();
        //         try {
        //             Pokemon removido = lista.remover(pos);
        //             System.out.println("(R) " + removido.getName());
        //         } catch (Exception e) {
        //             System.err.println("Erro ao remover na posição " + pos + ": " + e.getMessage());
        //         }
        //     } else if (command.equals("RF")) {
        //         try {
        //             Pokemon removido = lista.removerFim();
        //             System.out.println("(R) " + removido.getName());
        //         } catch (Exception e) {
        //             System.err.println("Erro ao remover do fim: " + e.getMessage());
        //         }
        //     }
        // }

        lista.quickSort();

        scanner.close();
        lista.mostrar();
    }
}