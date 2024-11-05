#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

char ids[801][5];
int numIds = 0;
int numNomes = 0;

int comparacoes = 0;
int movimentacoes = 0;

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

typedef struct CelulaDupla {
    Pokemon pokemon;
    struct CelulaDupla* prox;
    struct CelulaDupla* ant;
} CelulaDupla;

typedef struct {
    CelulaDupla* primeiro;
    CelulaDupla* ultimo;
    int tam;
} ListaDupla;

void start(ListaDupla* lista) {
    lista->primeiro = NULL;
    lista->ultimo = NULL;
    lista->tam = 0;
}

CelulaDupla* novaCelula(Pokemon pokemon) {
    CelulaDupla* nova = (CelulaDupla*)malloc(sizeof(CelulaDupla));
    if (nova == NULL) {
        printf("Falha na alocacao da celula");
        exit(1);
    }
    nova->pokemon = pokemon;
    nova->prox = NULL;
    nova->ant = NULL;
    return nova;
}

void inserirInicio(ListaDupla* lista, Pokemon pokemon) {
    CelulaDupla* nova = novaCelula(pokemon);
    if (lista->primeiro != NULL) {
        nova->prox = lista->primeiro;
        lista->primeiro->ant = nova;
    }
    lista->primeiro = nova;
    if (lista->ultimo == NULL) {
        lista->ultimo = nova;
    }
    lista->tam++;
}

void inserirFim(ListaDupla* lista, Pokemon pokemon) {
    CelulaDupla* nova = novaCelula(pokemon);
    if (lista->ultimo != NULL) {
        lista->ultimo->prox = nova;
        nova->ant = lista->ultimo;
    }
    lista->ultimo = nova;
    if (lista->primeiro == NULL) {
        lista->primeiro = nova;
    }
    lista->tam++;
}

void inserir(ListaDupla* lista, Pokemon pokemon, int pos) {
    if (pos > lista->tam) {
        printf("ERRO");
        exit(1);
    }
    CelulaDupla* j = lista->primeiro;
    for (int i = 0; i < pos - 1 && j != NULL; i++, j = j->prox);

    CelulaDupla* nova = novaCelula(pokemon);
    nova->prox = j->prox;
    nova->ant = j;
    if (j->prox != NULL) {
        j->prox->ant = nova;
    }
    j->prox = nova;
    if (nova->prox == NULL) {
        lista->ultimo = nova;
    }
    lista->tam++;
}

void removerInicio(ListaDupla* lista) {
    if (lista->primeiro == NULL) {
        printf("Erro, Lista vazia");
        exit(1);
    }
    CelulaDupla* removida = lista->primeiro;
    printf("(R) %s\n", removida->pokemon.name);
    lista->primeiro = removida->prox;
    if (lista->primeiro != NULL) {
        lista->primeiro->ant = NULL;
    } else {
        lista->ultimo = NULL;
    }
    free(removida);
    lista->tam--;
}

void removerFim(ListaDupla* lista) {
    if (lista->ultimo == NULL) {
        printf("Erro, Lista vazia");
        exit(1);
    }
    CelulaDupla* removida = lista->ultimo;
    printf("(R) %s\n", removida->pokemon.name);
    lista->ultimo = removida->ant;
    if (lista->ultimo != NULL) {
        lista->ultimo->prox = NULL;
    } else {
        lista->primeiro = NULL;
    }
    free(removida);
    lista->tam--;
}

void remover(ListaDupla* lista, int pos) {
    if (lista->primeiro == NULL || pos > lista->tam) {
        printf("Erro");
        exit(1);
    }
    CelulaDupla* j = lista->primeiro;
    for (int i = 0; i < pos - 1 && j != NULL; i++, j = j->prox);

    CelulaDupla* removida = j->prox;
    printf("(R) %s\n", removida->pokemon.name);
    j->prox = removida->prox;
    if (removida->prox != NULL) {
        removida->prox->ant = j;
    } else {
        lista->ultimo = j;
    }
    free(removida);
    lista->tam--;
}

void imprimir(Pokemon pokemon) {
    printf("[#%s -> %s: %s - ", pokemon.id, pokemon.name, pokemon.description);
    if (strcmp(pokemon.types[1], " ") == 0) {
        printf("['%s'] - ", pokemon.types[0]);
    } else {
        printf("['%s', '%s'] - ", pokemon.types[0], pokemon.types[1]);
    }
    printf("%s - %skg - %sm - %s%% - %s - %s gen] - %s\n", pokemon.abilities, pokemon.weight, pokemon.height, pokemon.captureRate, pokemon.isLegendary ? "true" : "false", pokemon.generation, pokemon.captureDate);
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

void trocar(CelulaDupla *a, CelulaDupla *b) {
    Pokemon temp = a->pokemon;
    a->pokemon = b->pokemon;
    b->pokemon = temp;
    movimentacoes++;
}

CelulaDupla* particionar(CelulaDupla* baixo, CelulaDupla* alto) {
    char* pivoGeracao = alto->pokemon.generation;
    char* pivoNome = alto->pokemon.name;
    CelulaDupla* i = baixo->ant;

    for (CelulaDupla* j = baixo; j != alto; j = j->prox) {
        comparacoes++;
        int cmpGeracao = strcmp(j->pokemon.generation, pivoGeracao);
        if (cmpGeracao < 0 || (cmpGeracao == 0 && strcmp(j->pokemon.name, pivoNome) < 0)) {
            i = (i == NULL) ? baixo : i->prox;
            trocar(i, j);
        }
    }
    i = (i == NULL) ? baixo : i->prox;
    trocar(i, alto);
    return i;
}

void quicksortRecursivo(CelulaDupla* baixo, CelulaDupla* alto) {
    if (alto != NULL && baixo != alto && baixo != alto->prox) {
        CelulaDupla* pivo = particionar(baixo, alto);
        quicksortRecursivo(baixo, pivo->ant);
        quicksortRecursivo(pivo->prox, alto);
    }
}

void quicksort(ListaDupla* lista) {
    if (lista->primeiro == NULL || lista->ultimo == NULL) {
        return;
    }
    quicksortRecursivo(lista->primeiro, lista->ultimo);
}

void salvarLog(int comparacoes, int movimentacoes, double tempo_execucao) {
    FILE *logFile = fopen("829031_quicksort2.txt", "w");
    if (logFile) {
        fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
        fclose(logFile);
    } else {
        printf("Erro ao criar o arquivo de log.\n");
    }
}

int main() {
    FILE *file = fopen("/tmp/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    ListaDupla lista;
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

    CelulaDupla* j;

    clock_t inicio = clock();
    quicksort(&lista);
    clock_t fim = clock();
    double tempo_execucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    salvarLog(comparacoes, movimentacoes, tempo_execucao);

    for (j = lista.primeiro; j != NULL; j = j->prox) {
        imprimir(j->pokemon);
    }

    return 0;
}