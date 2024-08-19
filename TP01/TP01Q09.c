#include <stdio.h>
#include <stdlib.h>

int main() {
    FILE *file;

    file = fopen("TP01Q09C.txt", "wb");

    if (file == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        exit(1);
    }

    int repeticao;

    scanf("%d", &repeticao);

    for(int i = 0; i < repeticao; i++) {
        double numero;
        scanf("%lf", &numero);
        fwrite(&numero, sizeof(double), 1, file);
    }

    fclose(file);

    file = fopen("TP01Q09C.txt", "rb");

    if (file == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        exit(1);
    }

    fseek(file, 0, SEEK_END);
    long long pointer = ftell(file);

    while (pointer > 0) {
        double value;
        pointer -= sizeof(double);
        fseek(file, pointer, SEEK_SET);
        fread(&value, sizeof(double), 1, file);

        if ((long long)value == value) {
            printf("%lld\n", (long long)value);
        } else {
            if (value == (long long)value) {
                printf("%lld\n", (long long)value);
            } else {
                printf("%g\n", value);
            }
        }
    }

    fclose(file);

    return(0);
}