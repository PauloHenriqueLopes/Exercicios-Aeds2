#include <stdio.h>
#include <string.h>

void limparBuffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

void toLower(char str[]) {
    for (int i = 0; str[i]; i++) {
        str[i] = tolower(str[i]);
    }
}

int contaToken(char string[], char token[]) {
    int contador = 0;
    int tokenLen = strlen(token);
    
    for (int i = 0; i <= strlen(string) - tokenLen; i++) {
        int j;
        for (j = 0; j < tokenLen; j++) {
            if (string[i + j] != token[j]) {
                break;
            }
        }
        
        if (j == tokenLen) {
            contador++;
            i += tokenLen - 1;
        }
    }
    return contador;

}

int main() {
    int quantidade;
    int contador = 0;
    scanf("%d" , &quantidade);
    
    limparBuffer();

    char token[50];
    fgets(token, sizeof(token), stdin);
    token[strcspn(token, "\n")] = '\0';

    toLower(token);

    for(int i = 0; i < quantidade; i++) {
        char string[200];
        fgets(string, sizeof(string), stdin);
        string[strcspn(string, "\n")] = '\0';
        
        toLower(string);

        contador += contaToken(string, token);
    }

    printf("%d\n", contador);

    return 0;
}