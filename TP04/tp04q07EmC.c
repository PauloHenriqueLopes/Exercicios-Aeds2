#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

#define TAM_TAB 21


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

typedef struct {
    Pokemon pokemon;
    struct Celula* prox; 
} Celula;

typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tam;
} ListaSequencial;

typedef struct {
    ListaSequencial* tabela[TAM_TAB];
} HashIndireto;

int calcularHash(const char* nome) {
    int somaASCII = 0;
    for (int i = 0; nome[i] != '\0'; i++) {
        somaASCII += nome[i];
    }
    return somaASCII % TAM_TAB;
}

void inicializarLista(ListaSequencial* lista) {
    lista -> primeiro = NULL;
    lista -> ultimo = NULL;
    lista -> tam = 0;
}

void inicializarTabela(HashIndireto* hash) {
    for (int i = 0; i < TAM_TAB; i++) {
        hash->tabela[i] = (ListaSequencial*)malloc(sizeof(ListaSequencial));
        inicializarLista(hash->tabela[i]);
    }
}

Celula* novaCelula(Pokemon pokemon) {
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    if(nova == NULL) {
        printf("Falha na alocacao da celula");
        exit(1);
    }
    nova -> pokemon = pokemon;
    nova -> prox = NULL;
    return nova;
}

void inserirNaLista(ListaSequencial* lista, Pokemon pokemon) {
    Celula* nova = novaCelula(pokemon);
    if (lista->ultimo != NULL) {
        lista->ultimo->prox = nova;
    } else {
        lista->primeiro = nova;
    }
    lista->ultimo = nova;
    lista->tam++;
}

void inserirNaTabela(HashIndireto* hash, Pokemon pokemon) {
    int indice = calcularHash(pokemon.name);
    inserirNaLista(hash->tabela[indice], pokemon);
}

bool buscarNaTabela(HashIndireto* hash, const char* nome) {
    int indice = calcularHash(nome);
    ListaSequencial* lista = hash->tabela[indice];
    Celula* atual = lista->primeiro;

    while (atual != NULL) {
        if (strcmp(atual->pokemon.name, nome) == 0) {
            printf("(Posicao: %d) ", indice);
            return true;
        }
        atual = atual->prox;
    }
    return false; 
}

char * replace(char const * const original, char const * const pattern, char const * const replacement) {
    size_t const replen = strlen(replacement);
    size_t const patlen = strlen(pattern);
    size_t const orilen = strlen(original);

    size_t patcnt = 0;
    const char * oriptr;
    const char * patloc;

    for (oriptr = original; (patloc = strstr(oriptr, pattern)); oriptr = patloc + patlen) {
        patcnt++;
    }

    size_t const retlen = orilen + patcnt * (replen - patlen);
    char * const returned = (char *) malloc(sizeof(char) * (retlen + 1));

    if (returned != NULL) {
        char * retptr = returned;
        for (oriptr = original; (patloc = strstr(oriptr, pattern)); oriptr = patloc + patlen) {
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

int main() {
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    Pokemon listaPokemon[803];
    HashIndireto hash;
    inicializarTabela(&hash);

    ler();
    char listaNomes[802][100];
    lerNomes(listaNomes);

    int cont = 0;
    while (fgets(linha, sizeof(linha), file) != NULL) {
        linha[strcspn(linha, "\n")] = '\0';
        char compareString[400];
        char *auxstring;
        auxstring = replace(linha, ", ", ";");
        auxstring = replace(auxstring, ",,,", ", , ,");
        auxstring = replace(auxstring, ",,", ", ,");
        auxstring = replace(auxstring, "\"", "");

        strcpy(compareString, linha);
        listaPokemon[cont] = parsePokemon(auxstring);
        cont++;
    }

    for (int i = 0; i < numIds; i++) {
        for(int j = 0; j < cont; j++) {
            if(strcmp(ids[i], listaPokemon[j].id) == 0) {
                inserirNaTabela(&hash, listaPokemon[j]);
            }
        }
    }
    
    fclose(file);

    for(int i = 0; i < numNomes; i++) {
        printf("=> %s: ", listaNomes[i]);
        bool resp = buscarNaTabela(&hash, listaNomes[i]);
        if(resp == true) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}