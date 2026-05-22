// SmartLogix - CO2 Implementation
// Topics Covered:
// 1. B-Tree (Simplified)
// 2. Fenwick Tree (Binary Indexed Tree)

import java.util.*;


// --------------------------------------------------
// SIMPLE B-TREE IMPLEMENTATION
// --------------------------------------------------
class BTreeDemo {

    // ArrayList used to simulate B-Tree storage
    ArrayList<Integer> records = new ArrayList<>();

    // INSERT OPERATION
    void insert(int value) {

        records.add(value);

        // Sorting simulates indexed storage
        Collections.sort(records);
    }

    // SEARCH OPERATION
    boolean search(int value) {

        return records.contains(value);
    }

    // DELETE OPERATION
    void delete(int value) {

        records.remove(Integer.valueOf(value));
    }

    // DISPLAY RECORDS
    void display() {

        System.out.println(records);
    }
}


// --------------------------------------------------
// FENWICK TREE (BINARY INDEXED TREE)
// --------------------------------------------------
class FenwickTree {

    int[] BIT;
    int size;

    // Constructor
    FenwickTree(int n) {

        size = n;

        BIT = new int[n + 1];
    }

    // UPDATE OPERATION
    // Adds value at given index
    void update(int index, int value) {

        while (index <= size) {

            BIT[index] += value;

            index += index & (-index);
        }
    }

    // PREFIX SUM QUERY
    int query(int index) {

        int sum = 0;

        while (index > 0) {

            sum += BIT[index];

            index -= index & (-index);
        }

        return sum;
    }

    // RANGE SUM QUERY
    int rangeQuery(int left, int right) {

        return query(right) - query(left - 1);
    }
}


// --------------------------------------------------
// MAIN CLASS
// --------------------------------------------------
public class Main {

    public static void main(String[] args) {

        // ==========================================
        // B-TREE IMPLEMENTATION
        // ==========================================
        System.out.println("===== B-TREE IMPLEMENTATION =====");

        BTreeDemo bt = new BTreeDemo();

        // Insert records
        bt.insert(40);
        bt.insert(10);
        bt.insert(30);
        bt.insert(20);
        bt.insert(50);

        // Display records
        System.out.println("\nRecords after insertion:");
        bt.display();

        // Search operation
        System.out.println("\nSearching for 30:");

        if (bt.search(30))
            System.out.println("Record Found");
        else
            System.out.println("Record Not Found");

        // Delete operation
        System.out.println("\nDeleting 20");

        bt.delete(20);

        System.out.println("Records after deletion:");
        bt.display();


        // ==========================================
        // FENWICK TREE IMPLEMENTATION
        // ==========================================
        System.out.println("\n\n===== FENWICK TREE =====");

        // Create Fenwick Tree of size 5
        FenwickTree ft = new FenwickTree(5);

        // Insert values
        ft.update(1, 10);
        ft.update(2, 20);
        ft.update(3, 30);
        ft.update(4, 40);
        ft.update(5, 50);

        // Prefix Sum Query
        System.out.println("\nPrefix Sum upto index 3:");

        System.out.println(ft.query(3));

        // Range Sum Query
        System.out.println("\nRange Sum from index 2 to 5:");

        System.out.println(ft.rangeQuery(2, 5));
    }
}