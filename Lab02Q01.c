#include <stdio.h>
#include <string.h>

void mostrarConcatenacao(char stringFinal[]) {
    printf("%s\n", stringFinal);
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

void lerStrings(char str1[], char str2[], int maxLen) {
    char buffer[2 * maxLen];
    if (fgets(buffer, sizeof(buffer), stdin) != NULL) {
        buffer[strcspn(buffer, "\n")] = '\0';
        
        char *spacePos = strchr(buffer, ' ');
        if (spacePos != NULL) {
            *spacePos = '\0';
            strncpy(str1, buffer, maxLen - 1);
            str1[maxLen - 1] = '\0';
            
            strncpy(str2, spacePos + 1, maxLen - 1);
            str2[maxLen - 1] = '\0';
        } else {
            strncpy(str1, buffer, maxLen - 1);
            str1[maxLen - 1] = '\0';
            str2[0] = '\0';
        }
    }
}

int main() {
    char string1[200];
    char string2[200];
    char resultado[400];

    while (1) {
        lerStrings(string1, string2, 200);
        if (strlen(string1) == 0 && strlen(string2) == 0) {
            break;
        }

        concatenaString(string1, string2, resultado);
        mostrarConcatenacao(resultado);
    }

    return 0;
}
