public class TP01Q14 {

    public static void main(String[] args) {
        processInput();
    }

    public static void processInput() {
        int numVariables = MyIO.readInt();
        while (numVariables != 0) {
            int[] valuesArray = createArray(numVariables);
            String expression = MyIO.readLine();
            String updatedExpression = replaceVariables(expression, valuesArray);
            MyIO.println(evaluateExpression(updatedExpression));
            numVariables = MyIO.readInt();
        }
    }

    public static int[] createArray(int size) {
        int[] array = new int[size];
        for (int index = 0; index < size; index++) {
            array[index] = MyIO.readInt();
        }
        return array;
    }

    public static String replaceVariables(String expression, int[] values) {
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < expression.length(); index++) {
            int variableIndex = 0;
            while (variableIndex < values.length && (char) ('A' + variableIndex) != expression.charAt(index)) {
                variableIndex++;
            }
            if (variableIndex >= values.length) {
                result.append(expression.charAt(index));
            } else {
                result.append((char) (values[variableIndex] + '0'));
            }
        }
        return result.toString();
    }

    public static char evaluateExpression(String updatedExpression) {
        String[] operators = {
            " ",
            "not(0)",
            "not(1)",
            "and(0,0)",
            "and(1,1)",
            "and(0,1)",
            "and(1,0)",
            "and(0,0,",
            "and(1,1,",
            "and(0,1,",
            "and(1,0,",
            "or(0,0)",
            "or(1,1)",
            "or(0,1)",
            "or(1,0)",
            "or(0,0,",
            "or(1,1,",
            "or(0,1,",
            "or(1,0,"
        };

        String[] replacements = {
            "",
            "1",
            "0",
            "0",
            "1",
            "0",
            "0",
            "and(and(0,0),",
            "and(and(1,1),",
            "and(and(0,1),",
            "and(and(1,0),",
            "0",
            "1",
            "1",
            "1",
            "or(or(0,0),",
            "or(or(1,1),",
            "or(or(0,1),",
            "or(or(1,0),"
        };

        while (updatedExpression.charAt(0) != '0' && updatedExpression.charAt(0) != '1') {
            for (int i = 0; i < operators.length; i++) {
                updatedExpression = replaceSubstring(updatedExpression, operators[i], replacements[i]);
            }
        }
        return updatedExpression.charAt(0);
    }

    public static String replaceSubstring(String original, String target, String replacement) {
        StringBuilder updated = new StringBuilder();
        for (int index = 0; index < original.length(); index++) {
            if (isMatch(original, target, index)) {
                updated.append(replacement);
                index += target.length() - 1;
            } else {
                updated.append(original.charAt(index));
            }
        }
        return updated.toString();
    }

    public static boolean isMatch(String text, String target, int start) {
        boolean isMatch = false;
        int end = start;
        if (text.length() - start >= target.length() && text.charAt(start) == target.charAt(0)) {
            while (end < text.length() && end - start < target.length() && text.charAt(end) == target.charAt(end - start)) {
                end++;
            }
            isMatch = (end - start) >= target.length();
        }
        return isMatch;
    }
}
