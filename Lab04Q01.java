import java.util.*;

public class Lab04Q01 {

    private static int mod(int a, int m) {
        int result = a % m;
        if (result < 0) {
            result += m;
        }
        return result;
    }

    private static class CustomComparator implements Comparator<Integer> {
        private final int m;

        public CustomComparator(int m) {
            this.m = m;
        }

        @Override
        public int compare(Integer a, Integer b) {
            int modA = mod(a, m);
            int modB = mod(b, m);

            if (modA != modB) {
                return Integer.compare(modA, modB);
            }

            if ((a % 2 != 0) && (b % 2 == 0)) {
                return -1;
            }
            if ((a % 2 == 0) && (b % 2 != 0)) {
                return 1;
            }

            if (a % 2 != 0 && b % 2 != 0) {
                return Integer.compare(b, a);
            }
            if (a % 2 == 0 && b % 2 == 0) {
                return Integer.compare(a, b);
            }

            return 0; 
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            if (N == 0 && M == 0) {
                break;
            }

            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                numbers.add(scanner.nextInt());
            }

            numbers.sort(new CustomComparator(M));

            System.out.println(N + " " + M);
            for (int num : numbers) {
                System.out.println(num);
            }
        }

        scanner.close();
    }
}
