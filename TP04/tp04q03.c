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

typedef struct No {
    Pokemon pokemon;
    struct No* dir;
    struct No* esq;
    int nivel;
} No;

No* raiz;

void start() {
    raiz = NULL;
}

No* newNo(Pokemon pokemon) {
    No* novo = (No*)malloc(sizeof(No));
    if (novo == NULL) {
        printf("Erro de alocação de memória\n");
        exit(1);
    }
    novo->pokemon = pokemon;
    novo->dir = NULL;
    novo->esq = NULL;
    novo->nivel = 0;
    return novo;
}

int getNivel(No* no) {
    return(no == NULL) ? 0 : no->nivel;
}

int max(No* dir, No* esq) {
    int nivelDir = dir != NULL ? dir->nivel : 0;
    int nivelEsq = esq != NULL ? esq->nivel : 0;
    return nivelDir > nivelEsq ? nivelDir : nivelEsq;
}

void setNivel(No* no) {
    no->nivel = 1 + max(getNivel(no->esq), getNivel(no->dir));
}

No* rotacionarDir(No* no) {
    if (no == NULL || no->esq == NULL) {
        printf("Erro: rotacionarDir em nó NULL ou sem filho à esquerda\n");
        return no;
    }

    No* noEsq = no->esq;
    No* noEsqDir = noEsq->dir;

    noEsq->dir = no;
    no->esq = noEsqDir;

    // Atualiza o nível dos nós após a rotação
    setNivel(no);
    setNivel(noEsq);

    return noEsq;
}

No* rotacionarEsq(No* no) {
    if (no == NULL || no->dir == NULL) {
        printf("Erro: rotacionarEsq em nó NULL ou sem filho à direita\n");
        return no;
    }

    No* noDir = no->dir;
    No* noDirEsq = noDir->esq;

    noDir->esq = no;
    no->dir = noDirEsq;

    // Atualiza o nível dos nós após a rotação
    setNivel(no);
    setNivel(noDir);

    return noDir;
}

No* balancear(No* no) {
    if (no == NULL) {
        printf("Erro: Nó NULL encontrado durante balanceamento\n");
        return NULL;
    }

    int fator = getNivel(no->dir) - getNivel(no->esq);
    printf("Balanceando nó: %s, fator: %d\n", no->pokemon.name, fator);

    if (abs(fator) <= 1) {
        setNivel(no);  // O nó está balanceado, apenas ajusta o nível
    } else if (fator == 2) {
        int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
        printf("Fator do filho direito: %d\n", fatorFilhoDir);

        if (fatorFilhoDir == -1) {
            no->dir = rotacionarDir(no->dir); // Rotação dupla à esquerda
        }
        no = rotacionarEsq(no); // Rotação simples à esquerda
    } else if (fator == -2) {
        int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
        printf("Fator do filho esquerdo: %d\n", fatorFilhoEsq);

        if (fatorFilhoEsq == 1) {
            no->esq = rotacionarEsq(no->esq); // Rotação dupla à direita
            no = rotacionarDir(no); // Rotação simples à direita
        }
    }

    setNivel(no);
    return no;
}


void inserirRec(Pokemon pokemon, No** i) {
    if (*i == NULL) {
        *i = newNo(pokemon);
    } else if (strcmp(pokemon.name, (*i)->pokemon.name) < 0) {
        inserirRec(pokemon, &(*i)->esq);
    } else if (strcmp(pokemon.name, (*i)->pokemon.name) > 0) {
        inserirRec(pokemon, &(*i)->dir);
    } else {
        printf("Erro ao inserir");
        return;
    }
    *i = balancear(*i); 
}

void inserir(Pokemon pokemon) {
    inserirRec(pokemon, &raiz);
}

bool pesquisarRec(char name[], No* i) {
    bool resp;
    if(i == NULL) {
        resp = false;
    } else if(strcmp(name, i->pokemon.name) == 0) {
        resp = true;
    } else if(strcmp(name, i->pokemon.name) < 0) {
        printf("esq ");
        resp = pesquisarRec(name, i->esq);
    } else {
        printf("dir ");
        resp = pesquisarRec(name, i->dir);
    }
    return resp;
}

bool pesquisar(char name[]) {
    printf("raiz ");
    return pesquisarRec(name, raiz);
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

void ler() {
    while (numIds < 801) {
        scanf("%4s", ids[numIds]);
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
    FILE *file = fopen("/home/paulo/Documents/programacao/Exercicios-Aeds2/TP04/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    Pokemon listaPokemon[808];

    start();

    ler();
    char listaNomes[802][100];
    lerNomes(listaNomes);

    int cont = 0;
    while (fgets(linha, sizeof(linha), file) != NULL) {
        linha[strcspn(linha, "\n")] = '\0';
        char *auxstring;
        auxstring = replace(linha, ", ", ";");
        auxstring = replace(auxstring, ",,,", ", , ,");
        auxstring = replace(auxstring, ",,", ", ,");
        auxstring = replace(auxstring, "\"", "");

        listaPokemon[cont] = parsePokemon(auxstring);
        cont++;
    }

    printf("AQUI\n");

    for (int i = 0; i < numIds; i++) {
        for(int j = 0; j < cont; j++) {
            if(strcmp(ids[i], listaPokemon[j].id) == 0) {
                inserir(listaPokemon[j]);
                j = cont;
            }
        }
    }

    fclose(file);

    for(int i = 0; i < numNomes; i++) {
        printf("%s\n", listaNomes[i]);
        bool resp = pesquisar(listaNomes[i]);
        if(resp == true) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}