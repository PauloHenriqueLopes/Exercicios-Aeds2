#include <stdio.h>
#include <ctype.h>
#include <string.h>

int contaMaiusculaRecursiva(char string[25], int passo, int contador) {
    int tam = strlen(string);
    if(tam == passo) {
        return contador;
    } else if(isupper(string[passo])) {
        return contaMaiusculaRecursiva(string, (passo + 1), (contador + 1));
    } else {
        return contaMaiusculaRecursiva(string, (passo + 1), contador);
    }
}

int main() {
    char string[100];
    int lista[25];
    int passo = 0;

    do{ 
        fgets(string, 25, stdin);
        string[strlen(string) - 1] = '\0';
        if(strcmp(string, "FIM") != 0){
            lista[passo] = contaMaiusculaRecursiva(string, 0, 0);
            passo++;
        }
    } while(strcmp(string, "FIM") != 0);

    for(int i = 0; i < passo; i++) {
        printf("%d\n", lista[i]);
    }
}