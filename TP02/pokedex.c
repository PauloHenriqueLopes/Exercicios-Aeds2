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
        printf("%s\n", token);
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

void ordenacaoPorSelecaoRecursiva(Pokemon* listaPokemon, int i, int* comparacoes, int* movimentacoes) {
    if (i >= numIds) {
        return;
    }

    int menor = i;
    for (int j = i + 1; j < numIds; j++) {
        (*comparacoes)++;
        if (strcmp(listaPokemon[j].name, listaPokemon[menor].name) < 0) {
            menor = j;
        }
    }

    if (menor != i) {
        Pokemon temp = listaPokemon[i];
        listaPokemon[i] = listaPokemon[menor];
        listaPokemon[menor] = temp;
        (*movimentacoes) += 3;
    }

    ordenacaoPorSelecaoRecursiva(listaPokemon, i + 1, comparacoes, movimentacoes);
}

void insercaoPorCor(Pokemon* listaPokemon, int cor, int h, int* comparacoes, int* movimentacoes) {
    for (int i = (h + cor); i < numIds; i += h) {
        Pokemon temp = listaPokemon[i];
        int j = (i - h);
        float tempWeight = atof(temp.weight);

        while (j >= 0) {
            float currentWeight = atof(listaPokemon[j].weight);
            (*comparacoes)++;
            if (currentWeight > tempWeight || 
                (currentWeight == tempWeight && strcmp(listaPokemon[j].name, temp.name) > 0)) {
                listaPokemon[j + h] = listaPokemon[j];
                j -= h;
                (*movimentacoes)++;
            } else {
                break;
            }
        }
        listaPokemon[j + h] = temp;
        (*movimentacoes)++;
    }
}

void ordenacaoPorShellSort(Pokemon* listaPokemon, int* comparacoes, int* movimentacoes) {
    int h = 1;

    while (h < numIds) {
        h = h * 3 + 1;
    }

    while (h > 1) {
        h /= 3;
        for (int cor = 0; cor < h; cor++) {
            insercaoPorCor(listaPokemon, cor, h, comparacoes, movimentacoes);
        }
    }
}

void ordenacaoPorQuickSort(Pokemon* listaPokemon) {
    int comparacoes = 0;
    int movimentacoes = 0;
    clock_t start = clock();

    quickSortRec(listaPokemon, 0, (numIds - 1), &comparacoes, &movimentacoes);

    clock_t end = clock();
    double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

    FILE *logFile = fopen("829031_quicksort.txt", "w");
    if (logFile) {
        fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
        fclose(logFile);
    }
}

void quickSortRec(Pokemon* listaPokemon, int esq, int dir, int* comparacoes, int* movimentacoes) {
    int i = esq, j = dir;
    Pokemon pivo = listaPokemon[(esq + dir) / 2];

    while(i <= j) {
        while(atoi(listaPokemon[i].generation) < atoi(pivo.generation) || 
              (atoi(listaPokemon[i].generation) == atoi(pivo.generation) && strcmp(listaPokemon[i].name, pivo.name) < 0)) {
            i++;
            (*comparacoes)++;
        }
        while(atoi(listaPokemon[j].generation) > atoi(pivo.generation) || 
              (atoi(listaPokemon[j].generation) == atoi(pivo.generation) && strcmp(listaPokemon[j].name, pivo.name) > 0)) {
            j--;
            (*comparacoes)++;
        }
        if(i <= j) {
            Pokemon temp = listaPokemon[i];
            listaPokemon[i] = listaPokemon[j];
            listaPokemon[j] = temp;
            (*movimentacoes) += 3;
            i++;
            j--;
        }
    }
    if (esq < j) quickSortRec(listaPokemon, esq, j, comparacoes, movimentacoes);
    if (i < dir) quickSortRec(listaPokemon, i, dir, comparacoes, movimentacoes);
}

void ordenacaoPorBolha(Pokemon* listaPokemon) {
    int comparacoes = 0;
    int movimentacoes = 0;
    clock_t start = clock();

    int i, j;
    for(i = (numIds - 1); i > 0; i--) {
        for(j = 0; j < i; j++) {
            comparacoes++;
            if(atoi(listaPokemon[j].id) > atoi(listaPokemon[j + 1].id)) {
                Pokemon temp = listaPokemon[j];
                listaPokemon[j] = listaPokemon[j + 1];
                listaPokemon[j + 1] = temp;
                movimentacoes += 3;
            }
        }
    }

    clock_t end = clock();
    double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

    FILE *logFile = fopen("829031_bolha.txt", "w");
    if (logFile) {
        fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
        fclose(logFile);
    }
}

void countingSort(Pokemon* listaPokemon, int n, int exp, int* comparacoes, int* movimentacoes) {
    Pokemon output[n];
    int count[256] = {0};

    for (int i = 0; i < n; i++) {
        int index = (exp < strlen(listaPokemon[i].abilities)) ? listaPokemon[i].abilities[exp] : 0;
        count[index]++;
        (*movimentacoes)++;
    }

    for (int i = 1; i < 256; i++) {
        count[i] += count[i - 1];
        (*comparacoes)++;
    }

    for (int i = n - 1; i >= 0; i--) {
        int index = (exp < strlen(listaPokemon[i].abilities)) ? listaPokemon[i].abilities[exp] : 0;
        output[count[index] - 1] = listaPokemon[i];
        count[index]--;
        (*movimentacoes)++; 
    }

    for (int i = 0; i < n; i++) {
        listaPokemon[i] = output[i];
        (*movimentacoes)++; 
    }
}

void ordenacaoPorRadixSort(Pokemon* listaPokemon) {
    int comparacoes = 0;
    int movimentacoes = 0;
    clock_t start = clock();

    int n = numIds;
    int maxLength = 0;
    for (int i = 0; i < n; i++) {
        if (strlen(listaPokemon[i].abilities) > maxLength) {
            maxLength = strlen(listaPokemon[i].abilities);
        }
    }

    for (int exp = maxLength - 1; exp >= 0; exp--) {
        countingSort(listaPokemon, n, exp, &comparacoes, &movimentacoes);
    }

    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            (comparacoes)++;
            if (strcmp(listaPokemon[i].abilities, listaPokemon[j].abilities) == 0 && 
                strcmp(listaPokemon[i].name, listaPokemon[j].name) > 0) {
                Pokemon temp = listaPokemon[i];
                listaPokemon[i] = listaPokemon[j];
                listaPokemon[j] = temp;
                movimentacoes += 3;
            }
        }
    }

    clock_t end = clock();
    double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

    FILE *logFile = fopen("829031_radixsort.txt", "w");
    if (logFile) {
        fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
        fclose(logFile);
    }
}

int comparaDatas(const char* data1, const char* data2) {
    int dia1 = atoi(data1);
    int mes1 = atoi(data1 + 3);
    int ano1 = atoi(data1 + 6);

    int dia2 = atoi(data2);
    int mes2 = atoi(data2 + 3);
    int ano2 = atoi(data2 + 6);

    if (ano1 != ano2) {
        return ano1 - ano2; 
    }
    if (mes1 != mes2) {
        return mes1 - mes2;
    }
    return dia1 - dia2;
}

void ordenacaoParcialPorInsercao(Pokemon* listaPokemon) {
    int limite = 10;
    for (int i = 1; i < numIds; i++) {
        Pokemon temp = listaPokemon[i];
        int j = (i - 1);

        while (j >= 0 && comparaDatas(listaPokemon[j].captureDate, temp.captureDate) > 0) {
            listaPokemon[j + 1] = listaPokemon[j];
            j--;
        }
        listaPokemon[j + 1] = temp;
    }

    for (int j = 0; j < limite; j++) {
        imprimir(listaPokemon[j]);
    }
}

void construir(Pokemon* listaPokemons, int tamHeap) {
    for (int i = tamHeap; i > 1 && 
        (atof(listaPokemons[i].height) > atof(listaPokemons[i/2].height) || 
         (atof(listaPokemons[i].height) == atof(listaPokemons[i/2].height) && strcmp(listaPokemons[i].name, listaPokemons[i/2].name) > 0)); 
         i /= 2) {
        Pokemon temporario = listaPokemons[i];
        listaPokemons[i] = listaPokemons[i / 2];
        listaPokemons[i / 2] = temporario;
    }
}

int getMaiorFilho(Pokemon* listaPokemons, int i, int tamHeap) {
    int filho;
    if (2 * i + 1 <= tamHeap && 
        (atof(listaPokemons[2 * i].height) < atof(listaPokemons[2 * i + 1].height) || 
         (atof(listaPokemons[2 * i].height) == atof(listaPokemons[2 * i + 1].height) && strcmp(listaPokemons[2 * i].name, listaPokemons[2 * i + 1].name) < 0))) {
        filho = 2 * i + 1;
    } else {
        filho = 2 * i;
    }
    return filho;
}

void reconstruir(Pokemon* listaPokemon, int tamHeap) {
    int i = 1;
    while (i <= (tamHeap / 2)) {
        int filho = getMaiorFilho(listaPokemon, i, tamHeap);
        if (atof(listaPokemon[i].height) < atof(listaPokemon[filho].height) || 
            (atof(listaPokemon[i].height) == atof(listaPokemon[filho].height) && strcmp(listaPokemon[i].name, listaPokemon[filho].name) < 0)) {
            Pokemon temporario = listaPokemon[i];
            listaPokemon[i] = listaPokemon[filho];
            listaPokemon[filho] = temporario;
            i = filho;
        } else {
            i = tamHeap;
        }
    }
}

void ordencacaoParcialPorHeapSort(Pokemon* listaPokemon) {
    Pokemon temp[10]; 

    for (int i = 0; i < 10 && i < numIds; i++) {
        temp[i] = listaPokemon[i];
    }

    for (int i = 10; i >= 1; i--) {
        construir(temp, i);
    }

    for(int i = 10; i < numIds; i++) {
        if (atof(listaPokemon[i].height) < atof(temp[0].height) || 
            (atof(listaPokemon[i].height) == atof(temp[0].height) && strcmp(listaPokemon[i].name, temp[0].name) < 0)) {
                Pokemon temporario = temp[0];
                temp[0] = listaPokemon[i];
                listaPokemon[i] = temporario;
                reconstruir(temp, 10);
            }
    }

    for (int i = 0; i < 10; i++) {
        imprimir(temp[i]);
    }
}

int main() {
    FILE *file = fopen("/home/paulo/Documents/programacao/Exercicios-Aeds2/TP02/pokemon.csv", "r");

    if (!file) {
        printf("Não foi possível abrir o arquivo.");
        return 1;
    }

    char linha[400];
    Pokemon listaPokemon[802];

    ler();
    // char listaNomes[802][100];
    // lerNomes(listaNomes);

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
    // pesquisaBinaria(listaPokemon, listaNomes);

    //Sequencial Recursiva:
    // int comparacoes = 0;
    // int movimentacoes = 0;
    // clock_t start = clock();

    // ordenacaoPorSelecaoRecursiva(listaPokemon, 0, &comparacoes, &movimentacoes);

    // clock_t end = clock();
    // double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

    // FILE *logFile = fopen("829031_selecaoRecursiva.txt", "w");
    // if (logFile) {
    //     fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
    //     fclose(logFile);
    // }

    //shell Sort:
    // int comparacoes = 0;
    // int movimentacoes = 0;
    // clock_t start = clock();

    // ordenacaoPorShellSort(listaPokemon, &comparacoes, &movimentacoes);

    // clock_t end = clock();
    // double tempo_execucao = (double)(end - start) / CLOCKS_PER_SEC;

    // FILE *logFile = fopen("829031_shellsort.txt", "w");
    // if (logFile) {
    //     fprintf(logFile, "829031\t%d\t%d\t%f\n", comparacoes, movimentacoes, tempo_execucao);
    //     fclose(logFile);
    // }


    //Quick Sort:
    // ordenacaoPorQuickSort(listaPokemon);

    //Bolha:
    // ordenacaoPorBolha(listaPokemon);

    //Radix Sort:
    // ordenacaoPorRadixSort(listaPokemon);

    //Ordenacao Parcial por Insercao:
    // ordenacaoParcialPorInsercao(listaPokemon);

    //Ordenacao Parcial por HeapSort:
    // ordencacaoParcialPorHeapSort(listaPokemon);

    for (int j = 0; j < numIds; j++) {
        imprimir(listaPokemon[j]);
    }

    return 0;
}
