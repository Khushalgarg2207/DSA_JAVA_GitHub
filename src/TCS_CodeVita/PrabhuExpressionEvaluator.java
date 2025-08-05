package TCS_CodeVita;

import java.util.*;

public class PrabhuExpressionEvaluator {
    static Map<String, Integer> numMap = new HashMap<>();
    static Set<String> opSet = new HashSet<>(Arrays.asList("add", "sub", "mul", "rem", "pow", "div"));

    static {
        numMap.put("zero", 0);
        numMap.put("one", 1);
        numMap.put("two", 2);
        numMap.put("three", 3);
        numMap.put("four", 4);
        numMap.put("five", 5);
        numMap.put("six", 6);
        numMap.put("seven", 7);
        numMap.put("eight", 8);
        numMap.put("nine", 9);
    }

    static int parseNumber(String s) {
        if (!s.contains("c")) s = "c" + s;
        String[] parts = s.split("c");
        int result = 0;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            if (!numMap.containsKey(part)) return -1;
            result = result * 10 + numMap.get(part);
        }
        return result;
    }

    static boolean isValidNumber(String s) {
        return parseNumber(s) != -1;
    }

    static int eval(List<String> tokens, int[] idx) {
        if (idx[0] >= tokens.size()) return Integer.MIN_VALUE;
        String tok = tokens.get(idx[0]);
        if (!opSet.contains(tok)) {
            int val = parseNumber(tok);
            idx[0]++;
            return val;
        }
        String op = tok;
        idx[0]++;
        int val1 = eval(tokens, idx);
        if (val1 == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        int val2 = eval(tokens, idx);
        if (val2 == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        int val3, result;
        switch (op) {
            case "add": result = val1 + val2; break;
            case "sub": result = val1 - val2; break;
            case "mul": result = val1 * val2; break;
            case "rem":
                if (val2 == 0) return Integer.MIN_VALUE;
                result = val1 % val2; break;
            case "pow": result = (int) Math.pow(val1, val2); break;
            case "div":
                if (val2 == 0) return Integer.MIN_VALUE;
                result = val1 / val2; break;
            default: return Integer.MIN_VALUE;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expr = sc.nextLine().trim();
        if (expr.length() == 0) {
            System.out.println("expression is not complete or invalid");
            return;
        }
        String[] parts = expr.split("\\s+");
        List<String> tokens = new ArrayList<>();
        for (String part : parts) {
            if (opSet.contains(part) || isValidNumber(part)) tokens.add(part);
            else {
                System.out.println("expression evaluation stopped invalid words present");
                return;
            }
        }
        int[] idx = {0};
        int res = eval(tokens, idx);
        if (idx[0] != tokens.size() || res == Integer.MIN_VALUE)
            System.out.println("expression is not complete or invalid");
        else
            System.out.println(res);
    }
}

