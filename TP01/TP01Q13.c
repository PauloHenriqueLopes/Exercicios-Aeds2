#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

char* alteraçãoAleatoria(const char* string, int passo, char* stringAlterada, char x, char y) {

    if (passo >= strlen(string)) {
        stringAlterada[passo] = '\0'; 
        return stringAlterada;
    }

    if (string[passo] == x) {
        stringAlterada[passo] = y;
    } else {
        stringAlterada[passo] = string[passo];
    }

    return alteraçãoAleatoria(string, passo + 1, stringAlterada, x, y);
}

int main() {
    char string[200];
    char stringAlterada[200];
    char x , y;

    srand(time(NULL));

    do {
        fgets(string, 200, stdin);
        string[strlen(string) - 1] = '\0';

        x = 'a' + rand() % 26;
        y = 'a' + rand() % 26;

        if(strcmp(string, "FIM") != 0) {
            printf("%s\n", alteraçãoAleatoria(string, 0, stringAlterada, x, y));
        }
    } while(strcmp(string, "FIM") != 0);
    
}