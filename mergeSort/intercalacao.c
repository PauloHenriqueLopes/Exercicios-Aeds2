#include <stdio.h>

int main() {
    int array1[5] = {1, 2, 3, 4, 9};
    int array2[5] = {3, 5, 6, 7, 8};
    int array3[10];

    for(int i = 0, j = 0, c = 0; i < 10; i++) {
        if (j == 5) {
            array3[i] = array2[c];
            c++;
        } else if (c == 5) {
            array3[i] = array1[j];
            j++;
        } else if(array1[j] > array2[c]) {
            array3[i] = array2[c];
            c++;
        } else {
            array3[i] = array1[j];
            j++;
        }
    }

    for(int i = 0; i < 10; i++) { 
        printf("%d ", array3[i]);
    }

    return 0;
}