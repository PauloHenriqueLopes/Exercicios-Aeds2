#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int comparar(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

int main() {
    int quantidade;

    int bem = 0, mal = 0;

    char *listaNomes[100];

    do {
        printf("Digite a quantidade de pessoas (entre 1 e 100): ");
        scanf("%d", &quantidade);
        while (getchar() != '\n');
    } while (quantidade < 1 || quantidade > 100);


    for(int i = 0; i < quantidade; i++) {
        char entrada[100];

        fgets(entrada, 100, stdin);

        entrada[strcspn(entrada, "\n")] = '\0';

        if(entrada[0] == '+' || entrada[0] == '-') {
            listaNomes[i] = malloc(strlen(entrada));
            if(listaNomes[i] == NULL) {
                perror("Erro ao alocar memoria");
                return 1;
            }
        

            strcpy(listaNomes[i], entrada + 2);

            if(entrada[0] == '+') {
                bem++;
            } else {
                mal++;
            }
        } else {
        printf("Entrada invalida. Deve comecar com + ou -.\n");
        }
    }

    qsort(listaNomes, quantidade, sizeof(char *), comparar);


    for (int i = 0; i < quantidade; i++) {
        printf("%s\n", listaNomes[i]);
        free(listaNomes[i]);
    }

    printf("\nSe comporatam: %d | Nao se comportaram: %d\n", bem, mal);

}
