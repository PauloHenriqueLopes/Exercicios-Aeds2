#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

bool palindromo(char s[25], int pos) {
    int tam = strlen(s);
    
    if(pos == tam/2){
        return true;
    } else if(s[pos] != s[tam - 1 - pos]) {
        return false;
    } else {
        palindromo(s, pos + 1);
    }
}

int main() {
    char string[25];
    
    fgets(string, 25, stdin);

    int len = strlen(string);
    if(len > 0 && string[len - 1] == '\n') {
      string[len - 1] = '\0';  
    }

    bool resp = palindromo(string, 0);

    if(resp) {
        printf("SIM");
    } else {
        printf("NAO");
    }
}