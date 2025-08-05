package TCS_CodeVita;

import java.util.*;

public class MaxExpertiseTeam {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int c = sc.nextInt();
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adj[u].add(v);
            adj[v].add(u);
        }
        int[] expertise = new int[n];
        for (int i = 0; i < n; i++) expertise[i] = sc.nextInt();

        boolean[] visited = new boolean[n];
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                Queue<Integer> q = new LinkedList<>();
                q.add(i);
                visited[i] = true;
                while (!q.isEmpty()) {
                    int curr = q.poll();
                    component.add(curr);
                    for (int nxt : adj[curr]) {
                        if (!visited[nxt]) {
                            visited[nxt] = true;
                            q.add(nxt);
                        }
                    }
                }
                int m = component.size();
                if (m <= 20) {
                    int maxSum = 0;
                    int size = 1 << m;
                    for (int mask = 0; mask < size; mask++) {
                        boolean valid = true;
                        int sum = 0;
                        for (int j = 0; j < m && valid; j++) {
                            if ((mask & (1 << j)) != 0) {
                                for (int k : adj[component.get(j)]) {
                                    int pos = component.indexOf(k);
                                    if (pos != -1 && (mask & (1 << pos)) != 0 && pos != j) {
                                        valid = false;
                                        break;
                                    }
                                }
                                sum += expertise[component.get(j)];
                            }
                        }
                        if (valid) maxSum = Math.max(maxSum, sum);
                    }
                    result += maxSum;
                } else {
                    boolean[] block = new boolean[m];
                    int maxVal = 0;
                    for (int rep = 0; rep < m; rep++) {
                        int currSum = 0;
                        Arrays.fill(block, false);
                        for (int j = 0; j < m; j++) {
                            if (!block[j]) {
                                currSum += expertise[component.get(j)];
                                for (int nei : adj[component.get(j)]) {
                                    int idx = component.indexOf(nei);
                                    if (idx != -1) block[idx] = true;
                                }
                                block[j] = true;
                            }
                        }
                        maxVal = Math.max(maxVal, currSum);
                        Collections.rotate(component, 1);
                    }
                    result += maxVal;
                }
            }
        }
        System.out.println(result);
    }
}
