package application;

import java.util.*;


/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set.
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 * @author sapan (sapan@cs.wisc.edu)
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;

    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;


    /**
     * Public constructor
     *
     * @param branchingFactor
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException("Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }


    /*
     *
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override public void insert(K key, V value) {
        root.insert(key, value);
        InternalNode pushNode = (InternalNode) root.split();
        if (pushNode != null) {
            root = pushNode;  // update the root as the internal node
        }
    }


    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && !comparator.contentEquals("==") && !comparator
            .contentEquals("<="))
            return new ArrayList<V>();

        List<V> returnList = new ArrayList<>();

        returnList.addAll(root.rangeSearch(key, comparator));

        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }


    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     *
     * @author sapan
     */
    private abstract class Node {

        // List of keys
        List<K> keys;

        /**
         * Package constructor
         */
        Node() {
            keys = new ArrayList<>();
        }

        /**
         * Inserts key and value in the appropriate leaf node
         * and balances the tree if required by splitting
         *
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         *
         * @return key
         */
        abstract K getFirstLeafKey();

        /**
         * Gets the new sibling created after splitting the node
         *
         * @return Node
         */
        abstract Node split();

        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * @return boolean
         */
        abstract boolean isOverflow();

        public String toString() {
            return keys.toString();
        }

    } // End of abstract class Node


    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     *
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;


        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<>();
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return keys.size() >= branchingFactor;
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            InternalNode promote = null;    // Stores the promote node of subtree
            int i = 0;
            for (i = 0; i < keys.size(); i++) {
                // If we first find a child with a greater key, insert in the
                // left subtree of the child
                if (key.compareTo(keys.get(i)) <= 0) {
                    children.get(i).insert(key, value);
                    promote = (InternalNode) children.get(i).split();
                    break;
                }
            }
            // If the param key is greater than any key in the keys list, then i
            // will eventually be keys.size(), and insert in the right most
            // subtree
            if (i == keys.size()) {
                children.get(children.size() - 1).insert(key, value);
                promote = (InternalNode) children.get(children.size() - 1).split();
            }

            // Check if there is a promoted node
            if (promote != null) {
                // Add all promoted node's children to the children list
                children.remove(children.get(i));
                children.add(i, promote.children.get(0));
                children.add(i + 1, promote.children.get(1));

                //                children.addAll(promote.children);   // TODO

                //                children.sort(new Comparator<Node>() {
                //                    @Override public int compare(BPTree<K, V>.Node o1, BPTree<K, V>.Node o2) {
                //                        return o1.keys.get(0).compareTo(o2.keys.get(0));
                //                    }
                //                });
                // Add the key (max of left subtree) to the keys
                keys.add(promote.keys.get(0));
                keys.sort(new Comparator<K>() {
                    @Override public int compare(K o1, K o2) {
                        return o1.compareTo(o2);
                    }
                });
            } else {
                // If the i is keys.size(), then no need to change the keys list since the
                // value is inserted into the right most subtree.
                if (i != keys.size()) {
                    keys.set(i, children.get(i).keys.get(children.get(i).keys.size() - 1));
                }
            }
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#split()
         */
        Node split() {
            if (isOverflow()) {
                InternalNode promote =
                    new InternalNode();    // The node promoted to the parent level
                InternalNode newRight = new InternalNode();    // The new child node containing the
                // greater half of keys.
                // Update the key list for the new splited node
                for (int i = (keys.size() + 1) / 2; i < this.keys.size(); i++) {
                    newRight.keys.add(keys.get(i));
                }
                // Update key list for the current node
                ArrayList<K> newKeys = new ArrayList<>();
                for (int i = 0; i < (keys.size() + 1) / 2; i++) {
                    newKeys.add(keys.get(i));
                }
                keys = newKeys;
                // Update the key list of the promoted node
                promote.keys.add(newKeys.get(newKeys.size() - 1));
                // Update the children list of the new node
                for (int i = (children.size() + 1) / 2; i < this.children.size(); i++) {
                    newRight.children.add(children.get(i));
                }
                // Update the children list of the current node
                ArrayList<Node> newChildren = new ArrayList<>();
                for (int i = 0; i < (children.size() + 1) / 2; i++) {
                    newChildren.add(children.get(i));
                }
                children = newChildren;
                // Update the children list of the promoted node
                promote.children = new ArrayList<>();
                promote.children.add(this);
                promote.children.add(newRight);
                return promote;
            }
            return null;
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            //TODO should have checked valid values in the BTREE's rangeearch method so that
            //TODO we don't have check for key and comparator in each recursion
            for (int i = 0; i < keys.size(); i++) {
                if (key.compareTo(keys.get(i)) <= 0) {
                    return children.get(i).rangeSearch(key, comparator);
                }
            }
            return children.get(children.size() - 1).rangeSearch(key, comparator);
        }

    } // End of class InternalNode


    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     *
     * @author sapan
     */
    private class LeafNode extends Node {

        // List of values
        List<V> values;

        // Reference to the next leaf node
        LeafNode next;

        // Reference to the previous leaf node
        LeafNode previous;

        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = new ArrayList<>();
            next = null;
            previous = null;
        }


        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            if (previous == null) {
                return keys.get(0);
            }

            LeafNode firstNode = previous;
            while (firstNode.previous != null) {
                firstNode = firstNode.previous;
            }

            return firstNode.keys.get(0);
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if (keys.size() >= branchingFactor) {
                return true;
            }
            return false;
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {

            keys.add(key);
            keys.sort(new Comparator<K>() {
                @Override public int compare(K o1, K o2) {
                    return o1.compareTo(o2);
                }
            });
            values.add(keys.indexOf(key), value);
        }

        /**
         * 1. split the full leaf node into 2 and link them in order
         * 2. create a new Internal Node and set the 1st child and 2nd child
         * 3.
         *
         * @see BPTree.Node#split()
         */
        Node split() {
            // push a null node if isn't overflow
            if (!isOverflow()) {
                return null;
            }
            LeafNode newLeaf = new LeafNode();  // the split leaf node
            InternalNode pushNode = new InternalNode();  // the node to prompt upward

            for (int i = (keys.size() + 1) / 2; i < this.keys.size(); i++) {
                newLeaf.keys.add(keys.get(i));
            }
            // Update key list for the current node
            ArrayList<K> newKeys = new ArrayList<>();
            for (int i = 0; i < (keys.size() + 1) / 2; i++) {
                newKeys.add(keys.get(i));
            }
            keys = newKeys;


            for (int i = (values.size() + 1) / 2; i < values.size(); i++) {
                newLeaf.values.add(values.get(i));
            }
            ArrayList<V> newValues = new ArrayList<>();
            for (int i = 0; i < (values.size() + 1) / 2; i++) {
                newValues.add(values.get(i));
            }

            values = newValues;

            // link the two leafNodes
            if (next == null) {  // if this node is at tail
                newLeaf.previous = this;
                this.next = newLeaf;
            } else {  // if this node is in the middle of linked nodes
                this.next.previous = newLeaf;
                newLeaf.next = this.next;
                this.next = newLeaf;
                newLeaf.previous = this;
            }

            // update the pushNode info
            pushNode.keys.add(keys.get(keys.size() - 1));
            pushNode.children.add(this);
            pushNode.children.add(newLeaf);

            return pushNode;
        }

        /**
         * (non-Javadoc)
         *
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> returnLList = new ArrayList<>();

            if (comparator.contentEquals(">=")) {
                for (int i = 0; i < keys.size(); i++) {
                    if (keys.get(i).compareTo(key) >= 0) {
                        returnLList.add(values.get(i));
                    }
                }
                // add all list of values in following nodes
                LeafNode nextNode = next;
                while (nextNode != null) {
                    returnLList.addAll(nextNode.values);
                    nextNode = nextNode.next;
                }
            } else if (comparator.contentEquals("==")) {

                for (int i = 0; i < keys.size(); i++) {
                    if (keys.get(i).compareTo(key) == 0) {
                        returnLList.add(values.get(i));
                    }
                }
                // searching all following nodes
                LeafNode nextNode = next;
                while (nextNode != null) {
                    for (int i = 0; i < nextNode.keys.size(); i++) {
                        if (nextNode.keys.get(i).compareTo(key) == 0) {
                            returnLList.add(nextNode.values.get(i));
                        }else {
                            break;
                        }
                    }
                    nextNode = nextNode.next;
                }
                // searching all previous nodes
                LeafNode preNode = previous;
                while (preNode != null) {
                    for (int i = 0; i < preNode.keys.size(); i++) {
                        if (preNode.keys.get(i).compareTo(key) == 0) {
                            returnLList.add(preNode.values.get(i));
                        }else {
                            break;
                        }
                    }
                    preNode = preNode.previous;
                }

            } else {  // <=
                for (int i = 0; i < keys.size(); i++) {
                    if (keys.get(i).compareTo(key) <= 0) {
                        returnLList.add(values.get(i));
                    }
                }
                // add all the list of values in previous nodes
                LeafNode preNode = previous;
                while (preNode != null) {
                    returnLList.addAll(preNode.values);
                    preNode = preNode.previous;
                }
                // searching all following nodes
                LeafNode nextNode = next;
                while (nextNode != null) {
                    for (int i = 0; i < nextNode.keys.size(); i++) {
                        if (nextNode.keys.get(i).compareTo(key) <= 0) {
                            returnLList.add(0,nextNode.values.get(i));
                        }else {
                            break;
                        }
                    }
                    nextNode = nextNode.next;
                }

            }
            return returnLList;

        }
    }

    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     *
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(10);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
                        for (int i = 0; i < 40; i++) {
                            Double j = dd[rnd1.nextInt(4)];
                            list.add(j);
                            bpTree.insert(j, j);
                            //                    System.out.println("\n\nTree structure:\n" + bpTree.toString());
                        }

//        for (int i = 0; i < 10; i++) {
//            bpTree.insert((double) 1, (double) i);
//
//        }

        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
