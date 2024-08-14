#include <stdio.h>
#include <ctype.h>
#include <string.h>

int contaMaiuscula(char string[25]) {
    int contador = 0;
    int tam = strlen(string);

    for(int i = 0; i < tam; i++) {
        if(isupper(string[i])) {
            contador++;
        } 
    }

    return contador;
}

int main() {
    char string[25];
    int lista[25];
    int passo = 0;

    do{
        fgets(string, 25, stdin);
        string[(strlen(string) - 1)] = '\0';
        if(strcmp(string, "FIM") != 0) {
            lista[passo] = contaMaiuscula(string);
            passo++;
        }
    } while(strcmp(string, "FIM") != 0);

    for(int i = 0; i < passo; i++) {
        printf("%d\n", lista[i]);
    }

}