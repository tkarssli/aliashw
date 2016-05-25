import java.util.Stack;

public class RedBlackBST <Key extends Comparable<Key>, Value>{

    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;

    public static void main(String[] args){
        RedBlackBST<Integer, Integer> bst = new RedBlackBST<Integer, Integer>();

        // create a tree with keys 1 through 60
        for(int i = 1; i <= 60; i++){
            bst.put(i, i);
        }
        bst.traverse();

        for (int i = 1; i <= 20; i++){
            bst.deleteMin();
        }
        System.out.println("After removing the first 20 keys: --------------------------------------------");
        bst.traverse();
    }

    private class Node{
        Key key;
        Value val;
        Node left, right; // left and right subtrees
        int N ; // # of nodes in subtree
        boolean color;

        Node(Key key, Value val, int N, boolean color){
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    // initialize
    public RedBlackBST() {
    }

    // prints out node, color and children for the root.
    public void traverse(){
        traverse(root);
    }

    // prints out node, color and children for given node.
    private void traverse(Node x){
        if (x == null)
            return;

        Stack<Node> s = new Stack<Node>();
        Node currentNode = x;

        // while the stack is not empty and the current node is not null, add the node
        // to the stack and traverse left.
        while(!s.empty() || currentNode != null){
            if(currentNode != null){
                s.push(currentNode);
                currentNode = currentNode.left;
            }
            // prints out the node, color and children for assistance in drawing the tree.
            else {
                Node n = s.pop();
                System.out.printf("Key: %d", n.key);
                System.out.println("");
                System.out.println("Is it red: " + n.color);
                System.out.print("Child keys: ");
                if(!(n.left == null)){System.out.print(n.left.key + "|");
                }else{
                    System.out.print("x|");
                }
                if(!(n.right == null)){System.out.print(n.right.key);
                }else{
                    System.out.print("x");
                }
                System.out.println("");
                System.out.println("");
                currentNode = n.right;
            }
        }
    }

    public int size(){
        return size(root);
    }

    // return number of nodes rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    // checks to see if the node is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // checks to see if the node is red.
    private boolean isRed(Node x){
        if (x == null)
            return false;
        return x.color == RED;
    }

    // method for rotating to the left
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    // method for rotating to the right
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    // flip the color of the node
    private void flipColors(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // inserts new key at the given value and sets root to black.
    public void put(Key key, Value val){
        root = put(root, key, val);
        root.color = BLACK;
    }

    // creates a new node if empty, otherwise places it in the
    // correct place. uses rotate and flip colors methods to correct
    // the colors after insertion, then recalculates the node size.
    private Node put(Node h, Key key, Value val){
        if (h == null)
            return new Node(key, val, 1, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0)
            h.left = put(h.left, key, val);
        else if (cmp > 0)
            h.right = put(h.right, key, val);
        else
            h.val = val;

        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    // uses flip colors and rotate methods to rotate
    // to the left and flip colors
    private Node moveRedLeft(Node h){
        flipColors(h);
        if (isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    // removes the minimum and sets to black
    public void deleteMin(){
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if(!isEmpty())
            root.color = BLACK;
    }
    private Node deleteMin(Node h){
        if (h.left == null)
            return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    // sets the rules for the BST. if red is on the right, rotate left.
    // if red is on left twice in a row, rotate right. if red is both right and left,
    // flips the colors. then adds up the size.
    private Node balance(Node h) {
        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

}
