#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool palindromoRecursivo(char string[200], int passo) {
    if((strlen(string) / 2) == passo) return true;
    else if(string[passo] == string[(strlen(string)) - passo - 1]) return palindromoRecursivo(string, (passo + 1));
    else return false;
}

int main() {
    char string[200];
    bool resposta;
    bool lista[200];
    int passo = 0;

    do{
        fgets(string, 200, stdin);

        string[strlen(string) - 1] = '\0';

        if(strcmp(string, "FIM") != 0) {
            resposta = palindromoRecursivo(string, 0);
            lista[passo] = resposta;
            passo++;
        }
    } while(strcmp(string, "FIM") != 0);

    for(int i = 0; i < passo; i++) {
        printf("%s\n", lista[i] == true ? "SIM" : "NAO");
    }
}