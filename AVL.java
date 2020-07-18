import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * Your implementation of an AVL Tree.
 *
 * @author Xiying Huang
 * @userid xhuang309
 * @GTID 903089975
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        for (T t: data) {
            add(t);
        }
    }

    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        root = add(root, data);
    }
    /**
     * description: the real add method
     * @param data mean the data
     * @param tempnode mean the node
     * @return the node
     *
     */

    private AVLNode<T> add(AVLNode<T> tempnode, T data) {
        if (tempnode == null) {
            size++;
            tempnode = new AVLNode<T>(data);
        }


        if (data.compareTo(tempnode.getData()) < 0) {
            tempnode.setLeft(add(tempnode.getLeft(), data));
            tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                    - myGetH(tempnode.getRight()));
            tempnode = balance(tempnode);
        } else if (data.compareTo(tempnode.getData()) > 0) {
            tempnode.setRight(add(tempnode.getRight(), data));
            tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                    - myGetH(tempnode.getRight()));
            tempnode = balance(tempnode);
        }
        tempnode.setHeight(Math.max(myGetH(tempnode.getRight()),
                myGetH(tempnode.getLeft())) + 1);
        tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                - myGetH(tempnode.getRight()));

        return tempnode;


    }
    /**
     * description: banalce method
     * @param node mean the node
     * @return the node
     *
     */

    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null
                    && node.getLeft().getBalanceFactor() >= 0) {

                node = rightR(node);
            } else {
                node = leftrightR(node);
            }
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null
                    && node.getRight().getBalanceFactor() <= 0) {
                node = leftR(node);
            } else {
                node = rightleftR(node);
            }
        }
        return node;


    }






    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * 1: The data is a leaf. In this case, simply remove it.
     * 2: The data has one child. In this case, simply replace the node with
     * the child node.
     * 3: The data has 2 children. For this assignment, use the predecessor to
     * replace the data you are removing, not the sucessor.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data data to remove from the tree
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        AVLNode<T> lul = new AVLNode<T>(null);
        root = remove(root, data, lul);
        return lul.getData();
    }

    /**
     * description: the real remove method
     * @param data mean the data
     * @param tempnode mean the node
     * @param lul the return node
     * @return the node
     *
     */
    private AVLNode<T> remove(AVLNode<T> tempnode, T data, AVLNode<T> lul) {
        AVLNode<T> none = new AVLNode<T>(null);
        if (tempnode == null) {
            throw new java.util.NoSuchElementException("data not found!");
        } else if (data.compareTo(tempnode.getData()) < 0) {
            tempnode.setLeft(remove(tempnode.getLeft(), data, lul));
            tempnode.setHeight(Math.max(myGetH(tempnode.getRight()),
                    myGetH(tempnode.getLeft())) + 1);
            tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                    - myGetH(tempnode.getRight()));
            tempnode = balance(tempnode);

        } else if (data.compareTo(tempnode.getData()) > 0) {
            tempnode.setRight(remove(tempnode.getRight(), data, lul));
            tempnode.setHeight(Math.max(myGetH(tempnode.getRight()),
                    myGetH(tempnode.getLeft())) + 1);
            tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                    - myGetH(tempnode.getRight()));
            tempnode = balance(tempnode);
        } else {

            if (tempnode.getLeft() == null) {
                size--;
                lul.setData(tempnode.getData());
                tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                        - myGetH(tempnode.getRight()));

                return tempnode.getRight();
            } else if (tempnode.getRight() == null) {
                size--;
                lul.setData(tempnode.getData());
                tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                        - myGetH(tempnode.getRight()));

                return tempnode.getLeft();
            } else {
                lul.setData(tempnode.getData());
                tempnode.setData(bignode(tempnode.getLeft()));
                tempnode.setLeft(remove(tempnode.getLeft(),
                        tempnode.getData(), none));
                tempnode.setHeight(Math.max(myGetH(tempnode.getRight()),
                        myGetH(tempnode.getLeft())) + 1);
                tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                        - myGetH(tempnode.getRight()));
                tempnode = balance(tempnode);
            }
        }
        tempnode.setBalanceFactor(myGetH(tempnode.getLeft())
                - myGetH(tempnode.getRight()));

        return tempnode;

    }
    /**
     * description: find the node we want
     * @param onenode means the node to find its successor
     * @return the node
     *
     */
    private T bignode(AVLNode<T> onenode) {
        while (onenode.getRight() != null) {
            onenode = onenode.getRight();
        }
        return onenode.getData();



    }
    /**
     * Right rotation
     * @param thenode is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> rightR(AVLNode<T> thenode) {
        AVLNode<T> temp = thenode.getLeft();
        thenode.setLeft(temp.getRight());
        temp.setRight(thenode);
        thenode.setHeight(Math.max(myGetH(thenode.getRight()),
                myGetH(thenode.getLeft())) + 1);
        temp.setHeight(Math.max(myGetH(temp.getRight()),
                myGetH(temp.getLeft())) + 1);
        thenode.setBalanceFactor(myGetH(thenode.getLeft())
                - myGetH(thenode.getRight()));
        temp.setBalanceFactor(myGetH(temp.getLeft()) - myGetH(temp.getRight()));
        return temp;

    }

    /**
     * left rotation
     * @param thenode is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> leftR(AVLNode<T> thenode) {
        AVLNode<T> temp = thenode.getRight();
        thenode.setRight(temp.getLeft());
        temp.setLeft(thenode);
        thenode.setHeight(Math.max(myGetH(thenode.getRight()),
                myGetH(thenode.getLeft())) + 1);
        temp.setHeight(Math.max(myGetH(temp.getRight()),
                myGetH(temp.getLeft())) + 1);
        thenode.setBalanceFactor(myGetH(thenode.getLeft())
                - myGetH(thenode.getRight()));
        temp.setBalanceFactor(myGetH(temp.getLeft())
                - myGetH(temp.getRight()));

        return temp;
    }


    /**
     * rightleft rotation
     * @param thenode is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> rightleftR(AVLNode<T> thenode) {
        thenode.setRight(rightR(thenode.getRight()));
        return leftR(thenode);
    }


    /**
     * leftright rotation
     * @param thenode is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> leftrightR(AVLNode<T> thenode) {
        thenode.setLeft(leftR(thenode.getLeft()));
        return rightR(thenode);
    }
    /**
     * description: method to get the hight
     * @param tempnode mean the node
     * @return the height
     *
     */

    private int myGetH(AVLNode<T> tempnode) {
        if (tempnode == null) {
            return -1;
        } else {
            return tempnode.getHeight();
        }
    }


    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data data to get in the AVL tree
     * @return the data in the tree equal to the parameter.  Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        return get(root, data);
    }
    /**
     * description: method to get the node from tree
     * @param data mean the data
     * @param tempnode mean the node
     * @return the node
     *
     */

    private T get(AVLNode<T> tempnode, T data) {
        if (tempnode == null) {
            throw new java.util.NoSuchElementException("data is not found!");
        }
        if (data.compareTo(tempnode.getData()) == 0) {
            return tempnode.getData();
        } else if (data.compareTo(tempnode.getData()) < 0) {
            return get(tempnode.getLeft(), data);
        } else {
            return get(tempnode.getRight(), data);
        }

    }

    /**
     * Returns whether or not the parameter is contained within the tree.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data data to find in the AVL tree
     * @return whether or not the parameter is contained within the tree
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        return contains(root, data);
    }
    /**
     * description: method to check the tree has centain node or not
     *
     * @param data of the node
     * @param tempnode node
     * @return true or false of the tree contains the data
     *
     */

    private boolean contains(AVLNode<T> tempnode, T data) {
        if (tempnode == null) {
            return false;
        }
        if (data.compareTo(tempnode.getData()) == 0) {
            return true;
        } else if (data.compareTo(tempnode.getData()) < 0) {
            return contains(tempnode.getLeft(), data);
        } else {
            return contains(tempnode.getRight(), data);
        }


    }


    /**
     * In your BST homework, you worked with the concept of the successor, the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node containing {@code data} whose left child is also
     * an ancestor of {@code data}.
     *
     * For example, in the tree below, the successor of 76 is 81, and the
     * successor of 40 is 76.
     *
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data the data to find the successor of
     * @return the successor of {@code data}. If there is no larger data than
     * the one given, return null.
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
            //nothing null is passed in
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree.");
            //the tree must contain the nonempty data;
        }
        AVLNode<T> dummy = root;
        LinkedList<T> holdingList = new LinkedList<>();
        dummy = find(root, data, holdingList);
        if (dummy.getRight() != null) { //right subtree is not empty
            dummy = dummy.getRight();
            while (dummy.getLeft() != null) {
                dummy = dummy.getLeft();
            }
            return dummy.getData();
        } else { // right subtree is empty

            while (holdingList.size() > 0
                    && holdingList.getFirst().compareTo(data) < 0) {
                holdingList.removeFirst();
            }
            if (holdingList.size() == 0) {
                return null;
            }
            return holdingList.getFirst();
        }
    }

    /**
     * this find method works as a pointer reference to point to the node
     * that we need to find it's successor in the tree
     * @param node last ancestor
     * @param data the data to compare with
     * @param a list to keep track on the ancestors
     * @return the pointer reference to the node that we want to deal with
     */
    private AVLNode<T> find(AVLNode<T> node, T data, LinkedList<T> a) {
        if (node.getData().compareTo(data) == 0) {
            return node;
        } else if (node.getData().compareTo(data) < 0) {
            a.addFirst(node.getData());
            node = node.getRight();
            return find(node, data, a);
        } else if (node.getData().compareTo(data) > 0) {
            a.addFirst(node.getData());
            node = node.getLeft();
            return find(node, data, a);
        }
        return null;
    }

    /**
     * Return the height of the root of the tree.
     * 
     * This method does not need to traverse the tree since this is an AVL.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }
    /**
     * description: function to return tree hight
     * @param tempnode mean the node
     * @return the height
     *
     */
    private int height(AVLNode<T> tempnode) {
        if (tempnode == null) {
            return -1;
        } else {
            return 1 + Math.max(height(tempnode.getLeft()),
                    height(tempnode.getRight()));
        }

    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Get the number of elements in the tree.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the tree. Normally, you wouldn't do this, but it's
     * necessary to grade your code.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
