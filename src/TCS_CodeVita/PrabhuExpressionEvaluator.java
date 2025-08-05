import java.util.*;

public class PrabhuExpressionEvaluator {
    static Map<String, Integer> numMap = new HashMap<String, Integer>();
    static Set<String> opSet = new HashSet<String>(Arrays.asList("add", "sub", "mul", "rem", "pow", "div"));

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
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
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
        int result;
        if (op.equals("add")) result = val1 + val2;
        else if (op.equals("sub")) result = val1 - val2;
        else if (op.equals("mul")) result = val1 * val2;
        else if (op.equals("rem")) {
            if (val2 == 0) return Integer.MIN_VALUE;
            result = val1 % val2;
        } else if (op.equals("pow")) {
            result = (int) Math.pow(val1, val2);
        } else if (op.equals("div")) {
            if (val2 == 0) return Integer.MIN_VALUE;
            result = val1 / val2;
        } else {
            return Integer.MIN_VALUE;
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
        List<String> tokens = new ArrayList<String>();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (opSet.contains(part) || isValidNumber(part)) tokens.add(part);
            else {
                System.out.println("expression evaluation stopped invalid words present");
                return;
            }
        }
        int[] idx = new int[1];
        idx[0] = 0;
        int res = eval(tokens, idx);
        if (idx[0] != tokens.size() || res == Integer.MIN_VALUE)
            System.out.println("expression is not complete or invalid");
        else
            System.out.println(res);
    }
}
