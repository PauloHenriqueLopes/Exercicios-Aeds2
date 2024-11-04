#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int compare(const void *a, const void *b) {
    const char *tokenA = *(const char **)a;
    const char *tokenB = *(const char **)b;

    int lenA = strlen(tokenA);
    int lenB = strlen(tokenB);

    // Ordena primeiro pelo tamanho (decrescente)
    if (lenA != lenB) {
        return lenB - lenA;  // Maior string primeiro
    }
    
    // Se o tamanho é igual, mantém a ordem de entrada
    return 0;
}

int main() {
    int quant;

    scanf("%d", &quant);
    getchar();

    if(quant < 1 || quant > 50) {
        printf("ERRO\n");
        exit(1);
    }

    for(int i = 0; i < quant; i++) {
        char string[55];
        fgets(string, sizeof(string), stdin);
        string[strcspn(string, "\n")] = '\0';

        char *token = strtok(string, " ");
        char *tokens[55];
        int quantTokens = 0;

        while (token) {
            tokens[quantTokens] = token;
            quantTokens++;
            token = strtok(NULL, " ");
        }

        qsort(tokens, quantTokens, sizeof(char *), compare);

        for (int j = 0; j < quantTokens; j++) {
            printf("%s ", tokens[j]);
        }
        printf("\n");
    }
    return 0;
}