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

typedef struct {
    Pokemon pokemon;
     struct Celula* prox; 
} Celula;

typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tam;
} ListaSequencial;


void start(ListaSequencial* lista) {
    lista -> primeiro = NULL;
    lista -> ultimo = NULL;
    lista -> tam = 0;
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

void inserirInicio(ListaSequencial* lista, Pokemon pokemon) {
    Celula* nova = novaCelula(pokemon);
    nova->prox = lista->primeiro;
    lista->primeiro = nova;
    if(lista->ultimo == NULL) {
        lista->ultimo = nova;
    }
    lista->tam++;
}

void inserirFim(ListaSequencial* lista, Pokemon pokemon) {
    Celula* nova = novaCelula(pokemon);
    if(lista->tam != 0) {
        lista->ultimo->prox = nova;
    }
    lista->ultimo = nova;
    if(lista->primeiro == NULL) {
        lista->primeiro = nova;
    }
    lista->tam++;
}

void inserir(ListaSequencial* lista, Pokemon pokemon, int pos) {
    if(pos > lista->tam) {
        printf("ERRO");
        exit(1);
    }
    Celula* j;
    int i = 0;
    for(j = lista->primeiro, i = 0; i < pos - 1; i++, j = j->prox);

    Celula* tmp = j->prox;
    Celula* nova = novaCelula(pokemon);
    j->prox = nova;
    nova->prox = tmp;
    lista->tam++;
}


void removerInicio(ListaSequencial* lista) {
    if(lista->primeiro == NULL) {
        printf("Erro, Lista vazia");
        exit(1);
    }

    Celula* removida = lista->primeiro;
    printf("(R) %s\n", removida->pokemon.name);
    free(removida);
    lista->primeiro = lista->primeiro->prox;
    lista->tam--;
}

void removerFim(ListaSequencial* lista) {
    if (lista->primeiro == NULL) {
        printf("Erro, Lista vazia");
        exit(1);
    }
    if (lista->primeiro == lista->ultimo) {
        printf("(R) %s\n", lista->primeiro->pokemon.name);
        free(lista->primeiro);
        lista->primeiro = lista->ultimo = NULL;
    } else {
        Celula* i;
        for (i = lista->primeiro; i->prox != lista->ultimo; i = i->prox);
        printf("(R) %s\n", lista->ultimo->pokemon.name);
        free(lista->ultimo);
        lista->ultimo = i;
        lista->ultimo->prox = NULL;
    }
    lista->tam--;
}

void remover(ListaSequencial* lista, int pos) {
    if(lista->primeiro == NULL || pos > lista->tam) {
        printf("Erro");
        exit(1);
    }
    int i;
    Celula* j;
    for(j = lista->primeiro, i = 0; i < pos - 1; i++, j = j->prox);

    Celula* tmp = j->prox;
    j->prox = tmp->prox;
    printf("(R) %s\n", tmp->pokemon.name);
    free(tmp);
    lista->tam--;
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

int main() {
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    ListaSequencial lista;
    Pokemon listaPokemon[803];
    start(&lista);

    ler();

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
                inserirFim(&lista, listaPokemon[j]);
            }
        }
    }
    
    
    int n;
    scanf("%d", &n);
    limparBuffer();

    for (int i = 0; i < n; i++) {
        char comando[20];
        fgets(comando, sizeof(comando), stdin);
        comando[strcspn(comando, "\n")] = '\0';

        char* token = strtok(comando, " ");
        char* id;
        int pos;

        if(strcmp(token, "II") == 0) {
            id = strtok(NULL, " ");
            for (int j = 0; j < sizeof(listaPokemon); j++) {
                if (strcmp(listaPokemon[j].id, id) == 0) {
                    inserirInicio(&lista, listaPokemon[j]);
                    break;
                }
            }           
        } else if(strcmp(token, "IF") == 0) {
            id = strtok(NULL, " ");
            for (int j = 0; j < sizeof(listaPokemon); j++) {
                if (strcmp(listaPokemon[j].id, id) == 0) {
                    inserirFim(&lista, listaPokemon[j]);
                    break;
                }
            }
        } else if(strcmp(token, "I*") == 0) {
            pos = atoi(strtok(NULL, " "));
            id = strtok(NULL, " ");
            for (int j = 0; j < sizeof(listaPokemon); j++) {
                if (strcmp(listaPokemon[j].id, id) == 0) {
                    inserir(&lista, listaPokemon[j], pos);
                    break;
                }
            }
        } else if(strcmp(token, "RI") == 0) {
            removerInicio(&lista);
        } else if(strcmp(token, "RF") == 0) {
            removerFim(&lista);
        } else if(strcmp(token, "R*") == 0) {
            pos = atoi(strtok(NULL, " "));
            remover(&lista, pos);
        }
    }

    fclose(file);

    Celula* j;
    int posicao;
    for (j = lista.primeiro, posicao = 0; j != NULL; j = j->prox, posicao++) {
        printf("[%d] ", posicao);
        imprimir(j->pokemon);
    }

    return 0;
}