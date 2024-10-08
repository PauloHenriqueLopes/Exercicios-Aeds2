#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int numUltrapassagens(int *posicao, int *posicaoFinal, int corredores) {
    int ultrapassagens = 0;
    for(int i = 0; i < corredores; i++) {
        int corredor = posicaoFinal[i];

        int posArray = -1;
        for(int k = 0; k < corredores; k++) {
            if(corredor == posicao[k]) {
                posArray = k;
                k = corredores;
            }
        }
        
        while(posArray > i) {
            int temp = posicao[posArray];
            posicao[posArray] = posicao[posArray - 1];
            posicao[posArray - 1] = temp;
            ultrapassagens++;
            posArray--; 
        }
    }

    return ultrapassagens;
}

int main() {
    char entrada[100];
    for(int  i = 0; i < 3; i++) {
        fgets(entrada, 100, stdin);
        entrada[strcspn(entrada, "\n")] = 0;

        if (strlen(entrada) > 0) {
            int corredores = atoi(entrada);

            if (corredores > 0) {
                char posicaoChar[corredores * 10];
                int posicao[corredores];

                fgets(posicaoChar, sizeof(posicaoChar), stdin);

                char *token = strtok(posicaoChar, " ");
                for (int i = 0; i < corredores; i++) {
                    if (token != NULL) {
                        posicao[i] = atoi(token);
                        token = strtok(NULL, " ");
                    }
                }

                char posicaoFinalChar[corredores * 10];
                int posicaoFinal[corredores];

                fgets(posicaoFinalChar, sizeof(posicaoFinalChar), stdin);

                char *token2 = strtok(posicaoFinalChar, " ");
                for (int i = 0; i < corredores; i++) {
                    if (token2 != NULL) {
                        posicaoFinal[i] = atoi(token2);
                        token2 = strtok(NULL, " ");
                    }
                }
                
                int quant = numUltrapassagens(posicao, posicaoFinal, corredores);
                printf("%d\n", quant);
            } else {
                printf("Número de corredores deve ser maior que zero.\n");
            }
        }
    }
}