package TCS_CodeVita;

import java.util.*;

public class XFromY {
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd = false;
    }

    static void insert(TrieNode root, String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.isEnd = true;
    }

    static List<Integer> getMatches(TrieNode root, String X, int start) {
        List<Integer> ends = new ArrayList<>();
        TrieNode node = root;
        for (int i = start; i < X.length(); i++) {
            node = node.children.get(X.charAt(i));
            if (node == null)
                break;
            if (node.isEnd)
                ends.add(i + 1);
        }
        return ends;
    }

    static class State implements Comparable<State> {
        int totalCount;
        int stringFactor;

        State(int totalCount, int stringFactor) {
            this.totalCount = totalCount;
            this.stringFactor = stringFactor;
        }

        @Override
        public int compareTo(State other) {
            if (this.totalCount != other.totalCount)
                return Integer.compare(this.totalCount, other.totalCount);
            return Integer.compare(this.stringFactor, other.stringFactor);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String X = sc.nextLine();
        String Y = sc.nextLine();
        int S = sc.nextInt(), R = sc.nextInt();
        int n = X.length();

        TrieNode rootY = new TrieNode();
        TrieNode rootRevY = new TrieNode();
        for (int i = 0; i < Y.length(); i++) {
            insert(rootY, Y.substring(i));
        }
        String revY = new StringBuilder(Y).reverse().toString();
        for (int i = 0; i < revY.length(); i++) {
            insert(rootRevY, revY.substring(i));
        }

        final int INF = Integer.MAX_VALUE / 2;
        State[] dp = new State[n + 1];
        for (int i = 0; i <= n; i++)
            dp[i] = new State(INF, INF);
        dp[0] = new State(0, 0);

        for (int i = 0; i < n; i++) {
            if (dp[i].totalCount == INF)
                continue;

            List<Integer> matchesY = getMatches(rootY, X, i);
            for (int end : matchesY) {
                int newCount = dp[i].totalCount + 1;
                int newFactor = dp[i].stringFactor + S;
                State candidate = new State(newCount, newFactor);
                if (candidate.compareTo(dp[end]) < 0) {
                    dp[end] = candidate;
                }
            }

            List<Integer> matchesRevY = getMatches(rootRevY, X, i);
            for (int end : matchesRevY) {
                int newCount = dp[i].totalCount + 1;
                int newFactor = dp[i].stringFactor + R;
                State candidate = new State(newCount, newFactor);
                if (candidate.compareTo(dp[end]) < 0) {
                    dp[end] = candidate;
                }
            }
        }

        if (dp[n].totalCount == INF)
            System.out.println("Impossible");
        else
            System.out.println(dp[n].stringFactor);
    }
}
