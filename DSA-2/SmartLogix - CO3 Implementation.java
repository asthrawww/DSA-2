// SmartLogix - Logistics & Supply Chain Optimization System
// CO3 Implementation
//
// Topics Covered:
// 1. BFS (Breadth First Search)
// 2. DFS (Depth First Search)
// 3. Prim's Minimum Spanning Tree (MST)
//
// Application:
// - Warehouses and Distribution Centers are represented as graph nodes.
// - Delivery routes are represented as graph edges.
// - BFS checks connectivity of delivery centers.
// - DFS explores the complete logistics network.
// - Prim's MST finds the minimum-cost transportation network.

import java.util.*;

// --------------------------------------------------
// LOGISTICS GRAPH CLASS
// --------------------------------------------------
class LogisticsGraph {

    // Number of locations
    private int vertices;

    // Adjacency List
    private LinkedList<Integer>[] adj;

    // Logistics Locations
    String[] locations = {
            "Hyderabad Warehouse",
            "Mumbai Hub",
            "Chennai Distribution Center",
            "Bangalore Hub",
            "Pune Warehouse"
    };

    // Constructor
    LogisticsGraph(int v) {

        vertices = v;

        adj = new LinkedList[v];

        // Initialize adjacency list
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Add a transportation route
    void addRoute(int source, int destination) {

        adj[source].add(destination);
        adj[destination].add(source);
    }

    // --------------------------------------------------
    // BFS TRAVERSAL
    // --------------------------------------------------
    void BFS(int start) {

        boolean[] visited = new boolean[vertices];

        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;

        queue.add(start);

        System.out.println("\n===== BFS Delivery Route Traversal =====");

        while (!queue.isEmpty()) {

            int current = queue.poll();

            System.out.println(locations[current]);

            // Visit all neighboring locations
            for (int neighbour : adj[current]) {

                if (!visited[neighbour]) {

                    visited[neighbour] = true;

                    queue.add(neighbour);
                }
            }
        }
    }

    // --------------------------------------------------
    // DFS TRAVERSAL
    // --------------------------------------------------
    void DFS(int node, boolean[] visited) {

        visited[node] = true;

        System.out.println(locations[node]);

        // Visit all connected locations
        for (int neighbour : adj[node]) {

            if (!visited[neighbour]) {

                DFS(neighbour, visited);
            }
        }
    }
}

// --------------------------------------------------
// PRIM'S MST CLASS
// --------------------------------------------------
class PrimsLogistics {

    static final int V = 5;

    // Find vertex with minimum key value
    int minKey(int key[], boolean mstSet[]) {

        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {

            if (!mstSet[v] && key[v] < min) {

                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // Display MST
    void printMST(int parent[], int graph[][]) {

        String[] locations = {
                "Hyderabad",
                "Mumbai",
                "Chennai",
                "Bangalore",
                "Pune"
        };

        int totalCost = 0;

        System.out.println("\n===== Optimal Logistics Network (MST) =====");

        for (int i = 1; i < V; i++) {

            System.out.println(
                    locations[parent[i]]
                            + " --> "
                            + locations[i]
                            + " | Cost = "
                            + graph[i][parent[i]]
            );

            totalCost += graph[i][parent[i]];
        }

        System.out.println("\nTotal Transportation Cost = " + totalCost);
    }

    // Prim's Algorithm
    void primMST(int graph[][]) {

        int parent[] = new int[V];

        int key[] = new int[V];

        boolean mstSet[] = new boolean[V];

        // Initialize arrays
        Arrays.fill(key, Integer.MAX_VALUE);

        key[0] = 0;

        parent[0] = -1;

        // Build MST
        for (int count = 0; count < V - 1; count++) {

            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < V; v++) {

                if (graph[u][v] != 0 &&
                        !mstSet[v] &&
                        graph[u][v] < key[v]) {

                    parent[v] = u;

                    key[v] = graph[u][v];
                }
            }
        }

        printMST(parent, graph);
    }
}

// --------------------------------------------------
// MAIN CLASS
// --------------------------------------------------
public class Main {

    public static void main(String[] args) {

        // Create logistics network graph
        LogisticsGraph network = new LogisticsGraph(5);

        // --------------------------------------------------
        // ADD DELIVERY ROUTES
        // --------------------------------------------------
        network.addRoute(0, 1); // Hyderabad -> Mumbai
        network.addRoute(0, 2); // Hyderabad -> Chennai
        network.addRoute(1, 3); // Mumbai -> Bangalore
        network.addRoute(2, 4); // Chennai -> Pune

        // --------------------------------------------------
        // BFS TRAVERSAL
        // --------------------------------------------------
        network.BFS(0);

        // --------------------------------------------------
        // DFS TRAVERSAL
        // --------------------------------------------------
        System.out.println("\n===== DFS Delivery Network Traversal =====");

        boolean visited[] = new boolean[5];

        network.DFS(0, visited);

        // --------------------------------------------------
        // TRANSPORTATION COST MATRIX
        // Each value represents transportation cost
        // between two logistics centers
        // --------------------------------------------------
        int cost[][] = {
                {0, 4, 2, 0, 0},
                {4, 0, 1, 5, 0},
                {2, 1, 0, 8, 10},
                {0, 5, 8, 0, 2},
                {0, 0, 10, 2, 0}
        };

        // --------------------------------------------------
        // PRIM'S MST
        // --------------------------------------------------
        PrimsLogistics p = new PrimsLogistics();

        p.primMST(cost);
    }
}
