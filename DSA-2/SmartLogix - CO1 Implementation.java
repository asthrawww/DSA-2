// SmartLogix - CO1 Implementation
// Topics Covered:
// 1. Binary Search Tree (BST)
// 2. AVL Tree (Self Balancing Tree)


// --------------------------------------------------
// SHIPMENT CLASS
// --------------------------------------------------
class Shipment {

    int shipmentID;
    String source;
    String destination;
    double weight;

    // Constructor
    Shipment(int id, String src, String dest, double wt) {

        shipmentID = id;
        source = src;
        destination = dest;
        weight = wt;
    }

    // Display shipment details
    void display() {

        System.out.println(
                "ID: " + shipmentID +
                " | Source: " + source +
                " | Destination: " + destination +
                " | Weight: " + weight
        );
    }
}


// --------------------------------------------------
// BST NODE
// --------------------------------------------------
class BSTNode {

    Shipment data;
    BSTNode left, right;

    BSTNode(Shipment s) {

        data = s;
        left = right = null;
    }
}


// --------------------------------------------------
// BINARY SEARCH TREE
// --------------------------------------------------
class BST {

    BSTNode root;

    // INSERT OPERATION
    BSTNode insert(BSTNode root, Shipment s) {

        // Create node if tree is empty
        if (root == null)
            return new BSTNode(s);

        // Insert in left subtree
        if (s.shipmentID < root.data.shipmentID)
            root.left = insert(root.left, s);

        // Insert in right subtree
        else
            root.right = insert(root.right, s);

        return root;
    }

    // INORDER TRAVERSAL
    void inorder(BSTNode root) {

        if (root != null) {

            inorder(root.left);

            root.data.display();

            inorder(root.right);
        }
    }

    // SEARCH OPERATION
    BSTNode search(BSTNode root, int id) {

        // Shipment found or tree empty
        if (root == null || root.data.shipmentID == id)
            return root;

        // Search left subtree
        if (id < root.data.shipmentID)
            return search(root.left, id);

        // Search right subtree
        return search(root.right, id);
    }

    // FIND MINIMUM NODE
    BSTNode findMin(BSTNode root) {

        while (root.left != null)
            root = root.left;

        return root;
    }

    // DELETE OPERATION
    BSTNode delete(BSTNode root, int id) {

        if (root == null)
            return root;

        // Move left
        if (id < root.data.shipmentID)
            root.left = delete(root.left, id);

        // Move right
        else if (id > root.data.shipmentID)
            root.right = delete(root.right, id);

        // Node found
        else {

            // One child or no child
            if (root.left == null)
                return root.right;

            if (root.right == null)
                return root.left;

            // Two children
            BSTNode minNode = findMin(root.right);

            root.data = minNode.data;

            root.right = delete(root.right,
                    minNode.data.shipmentID);
        }

        return root;
    }
}


// --------------------------------------------------
// AVL TREE NODE
// --------------------------------------------------
class AVLNode {

    int key;
    int height;

    AVLNode left, right;

    AVLNode(int value) {

        key = value;
        height = 1;
    }
}


// --------------------------------------------------
// AVL TREE IMPLEMENTATION
// --------------------------------------------------
class AVLTree {

    AVLNode root;

    // GET HEIGHT
    int height(AVLNode n) {

        if (n == null)
            return 0;

        return n.height;
    }

    // GET BALANCE FACTOR
    int getBalance(AVLNode n) {

        if (n == null)
            return 0;

        return height(n.left) - height(n.right);
    }

    // RIGHT ROTATION
    AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left),
                height(y.right)) + 1;

        x.height = Math.max(height(x.left),
                height(x.right)) + 1;

        return x;
    }

    // LEFT ROTATION
    AVLNode leftRotate(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left),
                height(x.right)) + 1;

        y.height = Math.max(height(y.left),
                height(y.right)) + 1;

        return y;
    }

    // INSERT OPERATION
    AVLNode insert(AVLNode node, int key) {

        // Normal BST insertion
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);

        else if (key > node.key)
            node.right = insert(node.right, key);

        else
            return node;

        // Update height
        node.height = 1 +
                Math.max(height(node.left),
                        height(node.right));

        // Check balance
        int balance = getBalance(node);

        // LEFT LEFT CASE
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // RIGHT RIGHT CASE
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // LEFT RIGHT CASE
        if (balance > 1 && key > node.left.key) {

            node.left = leftRotate(node.left);

            return rightRotate(node);
        }

        // RIGHT LEFT CASE
        if (balance < -1 && key < node.right.key) {

            node.right = rightRotate(node.right);

            return leftRotate(node);
        }

        return node;
    }

    // PREORDER TRAVERSAL
    void preorder(AVLNode node) {

        if (node != null) {

            System.out.print(node.key + " ");

            preorder(node.left);

            preorder(node.right);
        }
    }
}


// --------------------------------------------------
// MAIN CLASS
// --------------------------------------------------
public class Main {

    public static void main(String[] args) {

        // ==========================================
        // BST IMPLEMENTATION
        // ==========================================
        System.out.println("===== BINARY SEARCH TREE =====");

        BST tree = new BST();

        // Insert shipment records
        tree.root = tree.insert(tree.root,
                new Shipment(101, "Delhi", "Mumbai", 50));

        tree.root = tree.insert(tree.root,
                new Shipment(50, "Chennai", "Bangalore", 30));

        tree.root = tree.insert(tree.root,
                new Shipment(150, "Hyderabad", "Pune", 70));

        tree.root = tree.insert(tree.root,
                new Shipment(25, "Kolkata", "Delhi", 20));

        tree.root = tree.insert(tree.root,
                new Shipment(75, "Jaipur", "Ahmedabad", 40));

        // Display all shipments
        System.out.println("\nAll Shipments:");

        tree.inorder(tree.root);

        // SEARCH
        System.out.println("\nSearching Shipment ID 75");

        BSTNode result = tree.search(tree.root, 75);

        if (result != null)
            result.data.display();

        // DELETE
        System.out.println("\nDeleting Shipment ID 50");

        tree.root = tree.delete(tree.root, 50);

        System.out.println("\nAfter Deletion:");

        tree.inorder(tree.root);


        // ==========================================
        // AVL TREE IMPLEMENTATION
        // ==========================================
        System.out.println("\n\n===== AVL TREE =====");

        AVLTree avl = new AVLTree();

        // Insert values
        avl.root = avl.insert(avl.root, 10);
        avl.root = avl.insert(avl.root, 20);
        avl.root = avl.insert(avl.root, 30);
        avl.root = avl.insert(avl.root, 40);
        avl.root = avl.insert(avl.root, 50);

        // Display preorder traversal
        System.out.println("Preorder Traversal:");

        avl.preorder(avl.root);
    }
}