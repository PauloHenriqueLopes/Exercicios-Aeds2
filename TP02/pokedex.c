#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

char ids[801][5];
int numIds = 0;
int numNomes = 0;

typedef struct {
    char id[8];
    char generation[5];
    char name[50];
    char description[100];
    char types[2][30];
    char abilities[1000];
    char weight[10];
    char height[10];
    char captureRate[10];
    bool isLegendary;
    char captureDate[15];
} Pokemon;

char * replace(
    char const * const original, 
    char const * const pattern, 
    char const * const replacement
) {
  size_t const replen = strlen(replacement);
  size_t const patlen = strlen(pattern);
  size_t const orilen = strlen(original);

  size_t patcnt = 0;
  const char * oriptr;
  const char * patloc;

  for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen) {
    patcnt++;
  }

  {
    size_t const retlen = orilen + patcnt * (replen - patlen);
    char * const returned = (char *) malloc( sizeof(char) * (retlen + 1) );

    if (returned != NULL)
    {
      char * retptr = returned;
      for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
      {
        size_t const skplen = patloc - oriptr;
        strncpy(retptr, oriptr, skplen);
        retptr += skplen;
        strncpy(retptr, replacement, replen);
        retptr += replen;
      }
      strcpy(retptr, oriptr);
    }
    return returned;
  }
}

void limparBuffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

void imprimir(Pokemon pokemon) {
    printf("[#%s -> %s: %s - ", pokemon.id, pokemon.name, pokemon.description);
    if(strcmp(pokemon.types[1], " ") == 0) {
        printf("['%s'] - ", pokemon.types[0]);
    } else {
        printf("['%s', '%s'] - ", pokemon.types[0], pokemon.types[1]);
    }
    printf("%s", pokemon.abilities);
    printf(" - ");
    printf("%skg - %sm - %s%% - %s - %s gen] - %s\n", pokemon.weight,
           pokemon.height, pokemon.captureRate,
           pokemon.isLegendary ? "true" : "false",
           pokemon.generation, pokemon.captureDate);
}

void ler() {
    while (numIds < 801) {
        scanf("%s", ids[numIds]);
        if (strcmp(ids[numIds], "FIM") == 0) {
            return;
        }
        numIds++;
    }
}

Pokemon parsePokemon(char *linha) {
    Pokemon pokemon;
    int contador = 1;
    char type1[30];
    char type2[30] = "";
    char *token = strtok(linha, ",");

    while (token) {
        switch (contador) {
            case 1:
                strcpy(pokemon.id, token);
                break;
            case 2:
                strcpy(pokemon.generation, token);
                break;
            case 3:
                strcpy(pokemon.name, token);
                break;
            case 4:
                strcpy(pokemon.description, token);
                break;
            case 5:
                strcpy(type1, token);
                break;
            case 6:
                strcpy(type2, token);
                break;
            case 7: {
                char *formataHabilidades;
                formataHabilidades = replace(token, ";", ", ");
                strcpy(pokemon.abilities, formataHabilidades);
                break;
            }
            case 8:
                if(strcmp(token, " ") == 0) {
                    strcpy(pokemon.weight, "0.0");
                }else {
                    strcpy(pokemon.weight, token);
                }
                break;
            case 9:
                if(strcmp(token, " ") == 0) {
                    strcpy(pokemon.height, "0.0");
                }else {
                    strcpy(pokemon.height, token);
                }
                break;
            case 10:
                strcpy(pokemon.captureRate, token);
                break;
            case 11:
                pokemon.isLegendary = strcmp(token, "0") != 0;
                break;
            case 12:
                strcpy(pokemon.captureDate, token);
                break;
            default:
                break;
        }
        token = strtok(NULL, ",");
        contador++;
    }

    strcpy(pokemon.types[0], type1);
    if (strlen(type2) > 0) {
        strcpy(pokemon.types[1], type2);
    } else {
        strcpy(pokemon.types[1], "");
    }

    return pokemon;
}

void lerNomes(char listaNomes[][100]) {
    char nome[100];
    limparBuffer();
    do {
        fgets(nome, sizeof(nome), stdin);
        nome[strcspn(nome, "\n")] = 0;
        if (strcmp(nome, "FIM") != 0) {
            strcpy(listaNomes[numNomes], nome);
            numNomes++;
        }
    } while (strcmp(nome, "FIM") != 0);
}   

void odernaPorNome(Pokemon* pokemons) {
    for(int i = 0; i < (numIds - 1); i++) {
        int menor = i;
        for(int  j = (i + 1);  j < numIds; j++) {
            if(strcmp(pokemons[menor].name, pokemons[j].name) > 0) {
                menor = j;
            }
        }
        Pokemon temp = pokemons[i];
        pokemons[i] = pokemons[menor];
        pokemons[menor] = temp;
    }
}

void pesquisaBinaria(Pokemon* listaPokemon, char listaNomes[][100]) {
    odernaPorNome(listaPokemon);  
    int comparacoes = 0;
    clock_t start = clock();

    FILE *logFile = fopen("829031_binaria.txt", "w");
    if (!logFile) {
        printf("Erro ao abrir o arquivo de log.\n");
        return;
    }

    for(int i = 0; i < numNomes; i++) {
        bool resp = false;
        int esq = 0, dir = (numIds - 1);

        while (esq <= dir) {
            int meio = (esq + dir) / 2;
            comparacoes++;
            int cmp = strcmp(listaPokemon[meio].name, listaNomes[i]); 

            if(cmp == 0) { 
                resp = true;
                break;
            } else if(cmp < 0) {
                esq = meio + 1;
            } else {
                dir = meio - 1;
            }
        }
        printf("%s\n", resp ? "SIM" : "NAO");
    }
        clock_t end = clock();
        double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

        fprintf(logFile, "829031\t%f\t%d\n", tempo_execucao, comparacoes);
    fclose(logFile);
}

int main() {
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    Pokemon listaPokemon[802];

    ler();

    char listaNomes[802][100];
    lerNomes(listaNomes);

    while (fgets(linha, sizeof(linha), file) != NULL) {
        linha[strcspn(linha, "\n")] = '\0';
        char compareString[400];
        char *auxstring;
        auxstring = replace(linha, ", ", ";");
        auxstring = replace(auxstring, ",,,", ", , ,");
        auxstring = replace(auxstring, ",,", ", ,");
        auxstring = replace(auxstring, "\"", "");

        strcpy(compareString, linha);
        char *idToken = strtok(compareString, ",");
        for (int i = 0; i < numIds; i++) {
            if (strcmp(ids[i], idToken) == 0) {
                listaPokemon[i] = parsePokemon(auxstring);
            }
        }
    }

    fclose(file);

    //metodos de Ordenacao/Pesquisa
    pesquisaBinaria(listaPokemon, listaNomes);

    // for (int j = 0; j < numIds; j++) {
    //     imprimir(listaPokemon[j]);
    // }

    return 0;
}
