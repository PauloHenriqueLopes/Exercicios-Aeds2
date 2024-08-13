#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

int main() {
    char string[25];
    bool palindromo = true;

    fgets(string, 25, stdin);

    size_t len = strlen(string);
    if (len > 0 && string[len - 1] == '\n') {
        string[len - 1] = '\0';
    }

    int tam = strlen(string);

    for(int i = 0; i <= tam/2; i++) {
        if(string[i] != string[tam - i - 1]) {
            palindromo = false;
        }
    }

    if(palindromo) {
        printf("SIM");
    } else {
        printf("NAO");
    }
}