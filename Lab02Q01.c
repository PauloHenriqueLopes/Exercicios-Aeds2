#include <stdio.h>
#include <string.h>

void mostrarConcatenacao(char stringFinal[]) {
    printf("%s", stringFinal);
}

void concatenaString(char string1[], char string2[], char resultado[]) {
    int tam1 = strlen(string1);
    int tam2 = strlen(string2);
    int i, j = 0;

    for (i = 0; i < tam1 && i < tam2; i++) {
        resultado[j++] = string1[i];
        resultado[j++] = string2[i];
    }

    while (i < tam1) {
        resultado[j++] = string1[i++];
    }

    while (i < tam2) {
        resultado[j++] = string2[i++];
    }

    resultado[j] = '\0';
}

void lerString(char str[], int maxLen) {
    fgets(str, maxLen, stdin);

    str[strcspn(str, "\n")] = '\0';
}

int main() {
    char string1[200];
    char string2[200];
    char resultado[200 * 2];

    lerString(string1, 200);
    lerString(string2, 200);

    concatenaString(string1, string2, resultado);

    mostrarConcatenacao(resultado);

    return 0;
}
