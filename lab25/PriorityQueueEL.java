package lab25;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by tkars on 5/25/2016.
 */

public class PriorityQueueEL<Key extends Comparable<Key>>{
    private class Node{
        int N;
        Key data;
        Node parent, left, right;
        public Node(Key data, int N){
            this.data = data; this.N = N;
        }
    }

    public static void main(String[] args) throws IOException {
        long start,end;
        PriorityQueueEL<Long> pq = new PriorityQueueEL<Long>();
        Random random = new Random();


        // Get random int in range
        long LOWER_RANGE = 0; //assign lower range value
        long UPPER_RANGE = 10; //assign upper range value
        long randomValue = LOWER_RANGE +
                (long)(random.nextDouble()*(UPPER_RANGE - LOWER_RANGE));


        // Do several tests on exponentially increasing queue sizes
        for(long x=0; x<7; x++){

            // Insert int into PriorityQueue
            for(long i=0; i<UPPER_RANGE; i++){
                pq.insert(randomValue);
            }


            System.out.println("Benchmark for " + pq.size() + " objects in Explicit Link Priority queue");

            System.out.print("Get Max: ");
            start = System.nanoTime();
            pq.max();
            end = System.nanoTime();
            System.out.println((end - start)/1000 + " microsecond");

            System.out.print("Del Max: ");
            start = System.nanoTime();
            pq.delMax();
            end = System.nanoTime();
            System.out.println((end - start)/1000 + " microsecond");

            System.out.print("Insert: ");
            start = System.nanoTime();
            pq.insert(randomValue);
            end = System.nanoTime();
            System.out.println((end - start)/1000 + " microsecond");
            System.out.println();

            UPPER_RANGE = (UPPER_RANGE) * 10 - pq.size();
        }
    }

    // fields
    private Node root;
    private Node lastInserted;


    // Get size of node
    private int size(Node x){
        if(x == null) return 0;
        return x.N;
    }

    // Exchange node with parent
    private void swim(Node x){
        if(x == null) return;
        if(x.parent == null) return; // we're at root
        int cmp = x.data.compareTo(x.parent.data);
        if(cmp > 0){
            swapNodeData(x, x.parent);
            swim(x.parent);
        }
    }

    // Exchange node with larger child
    private void sink(Node x){
        if(x == null) return;
        Node swapNode;
        if(x.left == null && x.right == null){
            return;
        }
        else if(x.left == null){
            swapNode = x.right;
            int cmp = x.data.compareTo(swapNode.data);
            if(cmp < 0)
                swapNodeData(swapNode, x);
        } else if(x.right == null){
            swapNode = x.left;
            int cmp = x.data.compareTo(swapNode.data);
            if(cmp < 0)
                swapNodeData(swapNode, x);
        } else{
            int cmp = x.left.data.compareTo(x.right.data);
            if(cmp >= 0){
                swapNode = x.left;
            } else{
                swapNode = x.right;
            }
            int cmpParChild = x.data.compareTo(swapNode.data);
            if(cmpParChild < 0) {
                swapNodeData(swapNode, x);
                sink(swapNode);
            }
        }
    }

    // Swap node data
    private void swapNodeData(Node x, Node y){
        Key temp = x.data;
        x.data = y.data;
        y.data = temp;
    }

    // Insert Node
    private Node insert(Node x, Key data){
        if(x == null){
            lastInserted = new Node(data, 1);
            return lastInserted;
        }

        // compare left and right
        int leftSize = size(x.left);
        int rightSize = size(x.right);

        if(leftSize <= rightSize){
            Node inserted = insert(x.left, data);
            x.left = inserted;
            inserted.parent = x;
        } else{
            Node inserted = insert(x.right, data);
            x.right = inserted;
            inserted.parent = x;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    private Node resetLastInserted(Node x){
        if(x == null) return null;
        if(x.left == null && x.right == null) return x;
        if(size(x.right) < size(x.left))return resetLastInserted(x.left);
        else                            return resetLastInserted(x.right);
    }
    
    // public methods
    public void insert(Key data){
        root = insert(root, data);
        swim(lastInserted);
    }
    public Key max(){
        if(root == null) return null;
        return root.data;
    }
    public Key delMax(){
        if(size() == 1){
            Key ret = root.data;
            root = null;
            return ret;
        }
        swapNodeData(root, lastInserted);
        Node lastInsParent = lastInserted.parent;
        Key lastInsData = lastInserted.data;
        if(lastInserted == lastInsParent.left){
            lastInsParent.left = null;
        } else{
            lastInsParent.right = null;
        }

        Node traverser = lastInserted;

        while(traverser != null){
            traverser.N--;
            traverser = traverser.parent;
        }

        lastInserted = resetLastInserted(root);

        sink(root);

        return lastInsData;
    }
    public int size(){
        return size(root);
    }
    public boolean isEmpty(){
        return size() == 0;
    }
}