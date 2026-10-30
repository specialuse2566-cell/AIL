import java.util.*;

class Edge {
    String dest;
    int cost;

    Edge(String dest, int cost) {
        this.dest = dest;
        this.cost = cost;
    }
}

public class SearchComparison {

    static HashMap<String, List<Edge>> graph = new HashMap<>();
    static HashMap<String, Integer> heuristic = new HashMap<>();

    // Create Undirected Graph
    static void addEdge(String src, String dest, int cost) {
        graph.putIfAbsent(src, new ArrayList<>());
        graph.putIfAbsent(dest, new ArrayList<>());

        graph.get(src).add(new Edge(dest, cost));
        graph.get(dest).add(new Edge(src, cost));
    }

    // Print Path
    static void printPath(HashMap<String, String> parent, String goal) {

        Stack<String> path = new Stack<>();

        while (goal != null) {
            path.push(goal);
            goal = parent.get(goal);
        }

        while (!path.isEmpty()) {
            System.out.print(path.pop());

            if (!path.isEmpty())
                System.out.print(" -> ");
        }

        System.out.println();
    }

    // ---------------- DIJKSTRA ----------------

    static void dijkstra(String start, String goal) {

        HashMap<String, Integer> dist = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();

        for (String city : graph.keySet())
            dist.put(city, Integer.MAX_VALUE);

        dist.put(start, 0);

        PriorityQueue<String> pq =
                new PriorityQueue<>(Comparator.comparingInt(dist::get));

        pq.add(start);

        while (!pq.isEmpty()) {

            String curr = pq.poll();

            for (Edge e : graph.get(curr)) {

                int newCost = dist.get(curr) + e.cost;

                if (newCost < dist.get(e.dest)) {

                    dist.put(e.dest, newCost);
                    parent.put(e.dest, curr);

                    pq.remove(e.dest);
                    pq.add(e.dest);
                }
            }
        }

        System.out.println("===== DIJKSTRA =====");
        System.out.println("Cost : " + dist.get(goal));
        System.out.print("Path : ");
        printPath(parent, goal);
    }

    // ---------------- A STAR ----------------

    static void aStar(String start, String goal) {

        HashMap<String, Integer> gCost = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();

        for (String city : graph.keySet())
            gCost.put(city, Integer.MAX_VALUE);

        gCost.put(start, 0);

        PriorityQueue<String> pq =
                new PriorityQueue<>((a, b) ->
                        (gCost.get(a) + heuristic.get(a))
                                - (gCost.get(b) + heuristic.get(b)));

        pq.add(start);

        while (!pq.isEmpty()) {

            String curr = pq.poll();

            if (curr.equals(goal))
                break;

            for (Edge e : graph.get(curr)) {

                int newCost = gCost.get(curr) + e.cost;

                if (newCost < gCost.get(e.dest)) {

                    gCost.put(e.dest, newCost);
                    parent.put(e.dest, curr);

                    pq.remove(e.dest);
                    pq.add(e.dest);
                }
            }
        }

        System.out.println("\n===== A* SEARCH =====");
        System.out.println("Cost : " + gCost.get(goal));
        System.out.print("Path : ");
        printPath(parent, goal);
    }

    public static void main(String[] args) {

        // Graph (Created Only Once)

        addEdge("A", "B", 4);
        addEdge("A", "C", 2);
        addEdge("B", "C", 1);
        addEdge("B", "D", 5);
        addEdge("C", "D", 8);
        addEdge("C", "E", 10);
        addEdge("D", "E", 2);
        addEdge("D", "F", 6);
        addEdge("E", "F", 3);

        // Heuristic values (Estimated distance to Goal F)

        heuristic.put("A", 10);
        heuristic.put("B", 8);
        heuristic.put("C", 7);
        heuristic.put("D", 4);
        heuristic.put("E", 2);
        heuristic.put("F", 0);

        String source = "A";
        String destination = "F";

        dijkstra(source, destination);
        aStar(source, destination);
    }
}