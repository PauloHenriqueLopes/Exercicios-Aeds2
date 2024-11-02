#include <stdio.h>

void intercalar(int esq, int meio, int dir, int array[]) {
    int nEsq = (meio + 1) - esq;
    int nDir = dir - meio;

    int arrayEsq[nEsq+1];
    int arrayDir[nDir+1];

    arrayEsq[nEsq] = arrayDir[nDir] = 0x7FFFFFFF;

    int iEsq, iDir, i;

    for(iEsq = 0; iEsq < nEsq; iEsq++) {
        arrayEsq[iEsq] = array[esq + iEsq];
    }

    for(iDir = 0; iDir < nDir; iDir++) {
        arrayDir[iDir] = array[(meio + 1) + iDir];
    }

    for(iEsq = iDir = 0, i = esq; i <= dir; i++) {
        array[i] = (arrayEsq[iEsq] <= arrayDir[iDir]) ? arrayEsq[iEsq++] : arrayDir[iDir++];
    }
}

void mergeSort(int esq, int dir, int array[]) {
    if(esq < dir) {
        int meio = (esq + dir) / 2;
        mergeSort(esq, meio, array);
        mergeSort(meio + 1, dir, array);
        intercalar(esq, meio, dir, array);
    }
}

int main() {
    int array[18] = {12, 4, 8, 2, 14, 17, 6, 18, 10, 16, 15, 5, 13, 9, 1, 11, 7, 3};
    int tamanho = sizeof(array) / sizeof(array[0]);

    mergeSort(0, tamanho - 1, array);

    for(int i = 0; i < tamanho; i++) {
        printf("%d ", array[i]);
    }

    return 0;
}
