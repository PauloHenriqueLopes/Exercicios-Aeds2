#include <stdio.h>
#include <string.h>

int palindromo(char string[100], int passo) {
    int tam = strlen(string);

    if(passo == tam/2){
        return 1;
    } else if(string[passo] != string[tam - passo - 1]) {
        return 0;
    } else {
        return palindromo(string, (passo + 1));
    }
}

int main() {
    char string[100];
    int lista[100];
    int passo = 0;

    do{
        fgets(string, 100, stdin);

        string[strlen(string) - 1] = '\0';

        if(strcmp(string, "FIM") != 0) { 
            lista[passo] = palindromo(string, 0);
            passo++;
        }
    } while(strcmp(string, "FIM") != 0);

    for(int i = 0; i < passo; i++) {
        if(lista[i] == 1){
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }
}