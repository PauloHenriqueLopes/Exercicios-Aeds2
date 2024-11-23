#include <stdio.h>
#include <stdlib.h>

typedef struct Celula {
    int elemento;
    struct Celula* prox;
} Celula;

typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tam;
} ListaSequencial;

void start(ListaSequencial* lista) {
    lista->primeiro = (Celula*)malloc(sizeof(Celula)); // Inicializa uma célula "cabeça"
    lista->primeiro->prox = NULL;
    lista->ultimo = lista->primeiro;
    lista->tam = 0;
}

Celula* novaCelula(int elemento) {
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

void inserir(int elemento, ListaSequencial* lista) {
    Celula* nova = novaCelula(elemento);
    lista->ultimo->prox = nova;
    lista->ultimo = nova;
    lista->tam++;
}

void remover(ListaSequencial* lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("ERRO: lista vazia\n");
        return;
    }
    Celula* remover = lista->primeiro->prox;
    lista->primeiro->prox = remover->prox;
    if (remover == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }
    printf("(R) %d\n", remover->elemento);
    free(remover);
    lista->tam--;
}

void meiose(ListaSequencial* lista) {
    Celula* i = lista->primeiro->prox;
    while (i != NULL) {
        int novoValor = i->elemento / 2;
        Celula* nova = novaCelula(novoValor);
        nova->prox = i->prox;
        i->elemento = novoValor;
        i->prox = nova;
        i = nova->prox;
    }
}

void mostrar(ListaSequencial* lista) {
    Celula* i;
    for (i = lista->primeiro->prox; i != NULL; i = i->prox) {
        printf("%d ", i->elemento);
    }
    printf("\n");
}

int main() {
    ListaSequencial lista;
    start(&lista);

    inserir(8, &lista);
    inserir(5, &lista);
    inserir(6, &lista);
    inserir(1, &lista);

    printf("Depois de inserir: \n");
    mostrar(&lista);

    meiose(&lista);

    printf("Depois da meiose: \n");  
    mostrar(&lista);

    return 0;
}
