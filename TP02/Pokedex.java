import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public static void lerNomes(List<String> nomesPokemons, Scanner scanner) {
        String nome;
        do{
            nome = scanner.nextLine();
            if(!nome.equals("FIM")) {
                nomesPokemons.add(nome);
            }
        }while(!(nome.equals("FIM")));
    }

    public Pokemon parsePokemon(String linha) {
        char c;
        StringBuilder string = new StringBuilder();
        List<String> detalhes = new ArrayList<>(); 
        for(int  i = 0; i < linha.length(); i++) {
            c = linha.charAt(i);
            if(c != ',' && c != '\"') {
                string.append(c);
            } else if(c == ',') {
                detalhes.add(string.toString().trim());
                string.setLength(0);
            } else if(c == '\"') {
                for(int j = i+1; j < linha.length(); j++) {
                    c = linha.charAt(j);
                    if(c == '\"') {
                        i = j+1;
                        j = linha.length();
                    }else {
                        string.append(c);
                    }
                }
                detalhes.add(string.toString().trim());
                string.setLength(0);
            } 
            if(i == (linha.length() - 1)) {
                detalhes.add(string.toString().trim());
                i = linha.length();
            }
        } 

        int id = Integer.parseInt(detalhes.get(0).trim());
        int gen = Integer.parseInt(detalhes.get(1).trim());
        String name = detalhes.get(2).trim();
        String description = detalhes.get(3).trim();
        String type1 = "'" + detalhes.get(4).trim() + "'";
        String type2 = "'" + detalhes.get(5).trim() + "'";
        String[] types;
        if (type2.equals("''")) {
            types = new String[] { type1 };
        } else {
            types = new String[] { type1, type2 };
        }
        String habilidadesString = detalhes.get(6).replace("[", "").replace("]", "").trim();
        String[] habilidades = habilidadesString.split(",");
        Double peso = detalhes.get(7).trim().isEmpty() ? 0.0 : Double.parseDouble(detalhes.get(7).trim());
        Double altura = detalhes.get(8).trim().isEmpty() ? 0.0 : Double.parseDouble(detalhes.get(8).trim());
        int captura = Integer.parseInt(detalhes.get(9).trim());
        Boolean lendario = detalhes.get(10).equals("0") ? false : true;
        String data = detalhes.get(11).trim();

        return new Pokemon(id, gen, name, description, types, habilidades, peso, altura, captura, lendario, data);
    }

    public static void pesquisaSequencial(List<String> nomesPokemons, List<Pokemon> pokemons) {
        long startTime = System.nanoTime();
        int numComparacoes = 0;
    
        for (String nome : nomesPokemons) {
            boolean encontrado = false;
            for (Pokemon pokemon : pokemons) {
                numComparacoes++;
                if (nome.equals(pokemon.getName())) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
    
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        DecimalFormat df = new DecimalFormat("#.######");
        String formattedDuration = df.format(duration);
    
        // Criar arquivo de log
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("829031_sequencial.txt"))) {
            String log = "829031\t" + formattedDuration + "\t" + numComparacoes;
            writer.write(log);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void ordenacaoPorSelecao(List<Pokemon> pokemons) {
        long startTime = System.nanoTime();
        int movimentacoes = 0;
        int comparacoes = 0;

        for(int i = 0; i < pokemons.size(); i++) {
            int menorNome = i;
            for(int j = (i+1); j < pokemons.size(); j++) {
                comparacoes++;
                if(pokemons.get(j).name.compareTo(pokemons.get(menorNome).name) < 0) {
                    menorNome = j;
                }
            }

            if(menorNome != i) {
                Pokemon temp = pokemons.get(i);
                pokemons.set(i, pokemons.get(menorNome));
                pokemons.set(menorNome, temp);
                movimentacoes+=3;
            }
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        DecimalFormat df = new DecimalFormat("#.######");
        String formattedDuration = df.format(duration);

        for(Pokemon pokemon : pokemons) {
            pokemon.imprimir(pokemon);
        }

        // Criar arquivo de log
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("829031_selecao.txt"))) {
            String log = "829031\t" + comparacoes + "\t" + movimentacoes + "\t" + formattedDuration;
            writer.write(log);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void ordenacaoPorInsercao(List<Pokemon> pokemons) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long startTime = System.nanoTime();
        int movimentacoes = 0;
        int comparacoes = 0;
    
        for (int i = 1; i < pokemons.size(); i++) {
            int j = (i - 1);
            Pokemon temp = pokemons.get(i);
            Date tempDate = null;
    
            try {
                tempDate = sdf.parse(temp.getCaptureDate());
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
    
            while (j >= 0) {
                Date currentDate = null;
                try {
                    currentDate = sdf.parse(pokemons.get(j).getCaptureDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                
                comparacoes++;

                if (currentDate.compareTo(tempDate) > 0 || 
                    (currentDate.equals(tempDate) && pokemons.get(j).getName().compareTo(temp.getName()) > 0)) {
                    pokemons.set(j + 1, pokemons.get(j));
                    j--;
                    movimentacoes++;
                } else {
                    break;
                }
            }
            pokemons.set(j + 1, temp);
            movimentacoes++;
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        DecimalFormat df = new DecimalFormat("#.######");
        String formattedDuration = df.format(duration);
    
        for (Pokemon pokemon : pokemons) {
            pokemon.imprimir(pokemon);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("829031_insercao.txt"))) {
            String log = "829031\t" + comparacoes + "\t" + movimentacoes + "\t" + formattedDuration;
            writer.write(log);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void ordenacaoPorHeapSort(List<Pokemon> pokemons) {
        Pokemon[] array = new Pokemon[pokemons.size()];
        Pokemon[] temp = new Pokemon[pokemons.size() + 1];
        for(int i = 0; i < pokemons.size(); i++) {
            temp[i+1] = pokemons.get(i);
        }
        array = temp;

        for(int tamHeap = 2; tamHeap <= pokemons.size(); tamHeap++) {
            construir(tamHeap, array);
        }

        int tamHeap = pokemons.size();
        while (tamHeap > 1) {
            Pokemon temporario = temp[1];
            temp[1] = temp[tamHeap];
            temp[tamHeap] = temporario;
            tamHeap--;
            reconstruir(tamHeap, array);
        }

        temp = array;
        array = new Pokemon[pokemons.size()];
        for(int i = 0; i < pokemons.size(); i++) {
            array[i] = temp[i+1];
        }
        
        for(int i = 0; i < pokemons.size(); i++) {
            array[i].imprimir(array[i]);
        }
    }

    public static void construir(int tamHeap, Pokemon[] temp) {
        for(int  i = tamHeap; i > 1 && comparaPokemon(temp[i], temp[i / 2]) > 0; i/=2) {
            Pokemon pokemonTemp = temp[i];
            temp[i] = temp[i/2];
            temp[i/2] = pokemonTemp;
        }
    }
    
    public static void reconstruir(int tamHeap, Pokemon[] temp) {
        int i = 1;
        while (i <= (tamHeap / 2)) {
            int filho = getMaiorFilho(i, tamHeap, temp);
            if(comparaPokemon(temp[i], temp[filho]) < 0) {
                Pokemon pokemonTemp = temp[i];
                temp[i] = temp[filho];
                temp[filho] = pokemonTemp;
                i = filho;
            } else {
                break;
            }
        }
    }

    public static int getMaiorFilho(int i, int tamHeap, Pokemon[] temp) {
        if(2*i == tamHeap || comparaPokemon(temp[2 * i], temp[2 * i + 1]) > 0) {
            return 2*i;
        } else {
            return 2* i + 1;
        }
    }

    public static int comparaPokemon(Pokemon p1, Pokemon p2) {
        if(p1.getHeight() > p2.getHeight()) {
            return 1;
        } else if(p1.getHeight() < p2.getHeight()) {
            return -1;
        } else {
            return p1.getName().compareTo(p2.getName());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> idsPokemons = new ArrayList<>();
        List<Pokemon> listaPokemons = new ArrayList<>();
        List<String> linhaCsv = new ArrayList<>();
        String entrada;
        do{
            entrada = scanner.nextLine();
            if(!entrada.equals("FIM")) {
                idsPokemons.add(entrada);
            }
        }while(!(entrada.equals("FIM")));

        //nomes dos pokemons
        // List<String> nomesPokemons = new ArrayList<>();
        // lerNomes(nomesPokemons, scanner);


        Pokedex pokedex = new Pokedex();

        try(Scanner lerArquivo = new Scanner(new File("/tmp/pokemon.csv"));) {
            lerArquivo.nextLine();
            while (lerArquivo.hasNextLine()) {
                String linha = lerArquivo.nextLine();
                linhaCsv.add(linha);
            }
            lerArquivo.close();
        } catch(FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado: " + e.getMessage());
        }

        for(int i = 0; i < idsPokemons.size(); i++) {
            for(int j = 0; j < linhaCsv.size(); j++) {
                String[] parts = linhaCsv.get(j).split(",");
                if(idsPokemons.get(i).equals(parts[0])) {
                    listaPokemons.add(pokedex.parsePokemon(linhaCsv.get(j)));
                    j = linhaCsv.size();
                }
            }
        }

        scanner.close();

        //metodos de Ordenacao/Pesquisa
        // pesquisaSequencial(nomesPokemons, listaPokemons);
        // ordenacaoPorSelecao(listaPokemons);
        // ordenacaoPorInsercao(listaPokemons);
        // ordenacaoPorHeapSort(listaPokemons);
        ordenacaoPorCountingSort(listaPokemons);
        
        
        // for (Pokemon pokemon : listaPokemons) {
        //     pokemon.imprimir(pokemon);
        // }
    }
}