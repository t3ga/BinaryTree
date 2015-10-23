public class LinkedBinaryTree<E> implements Visitor
{
	protected LinkedBinaryTreeNode<E> root = null;
	private int size=0;
	public LinkedBinaryTree(){}
	public int size()
	{
		return size;
	}
	public LinkedBinaryTreeNode<E> root()
    {
		return root;
	}
	public boolean isEmpty()
    {
		return size()==0;
	}
	protected LinkedBinaryTreeNode<E> createNode(int key,
                                                 E e,
                                                 LinkedBinaryTreeNode<E> parent,
                                                 LinkedBinaryTreeNode<E> left,
                                                 LinkedBinaryTreeNode<E> right)
    {
		return new LinkedBinaryTreeNode<E>(key, e, parent, left, right);
	}
	public LinkedBinaryTreeNode<E> addRoot(E e) throws IllegalStateException
    {
		if(!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root=createNode(0,e,null,null,null);
		size=1;
		return root;
	}
    public void set(LinkedBinaryTreeNode<E> n, E e)
    {
        n.setData(e);
    }
    public void remove(int k) {
        // First we must find the node, after this we can delete it.
        removeAVL(this.root,k);
    }

    /**
     * Finds a node and calls a method to remove the node.
     *
     * @param p The node to start the search.
     * @param q The KEY of node to remove.
     */
    public void removeAVL(LinkedBinaryTreeNode<E> p,int q) {
        if(p==null) {
            // der Wert existiert nicht in diesem Baum, daher ist nichts zu tun
            return;
        } else {
            if(p.key>q)  {
                removeAVL(p.left,q);
            } else if(p.key<q) {
                removeAVL(p.right,q);
            } else if(p.key==q) {
                // we found the node in the tree.. now lets go on!
                removeFoundNode(p);
            }
        }
    }

    /**
     * Removes a node from a AVL-Tree, while balancing will be done if necessary.
     *
     * @param q The node to be removed.
     */
    public void removeFoundNode(LinkedBinaryTreeNode<E> q) {
        LinkedBinaryTreeNode<E> r;
        // at least one child of q, q will be removed directly
        if(q.left==null || q.right==null) {
            // the root is deleted
            if(q.parent==null) {
                this.root=null;
                q=null;
                return;
            }
            r = q;
        } else {
            // q has two children --> will be replaced by successor
            r = successor(q);
            q.key = r.key;
        }

        LinkedBinaryTreeNode<E> p;
        if(r.left!=null) {
            p = r.left;
        } else {
            p = r.right;
        }

        if(p!=null) {
            p.parent = r.parent;
        }

        if(r.parent==null) {
            this.root = p;
        } else {
            if(r==r.parent.left) {
                r.parent.left=p;
            } else {
                r.parent.right = p;
            }
            // balancing must be done until the root is reached.
            recursiveBalance(r.parent);
        }
        r = null;
    }
    public void traversePreorder()throws IllegalStateException
    {
        if (isEmpty()) throw new IllegalStateException("Tree is empty");
        traversePreorder(root);
    }
    private void traversePreorder(LinkedBinaryTreeNode<E> node)
    {
        visit(node);
        if(node.getLeft()!=null)traversePreorder(node.getLeft());
        if(node.getRight()!=null)traversePreorder(node.getRight());
    }
    public void traversePostorder()throws IllegalStateException
    {
        if(isEmpty())throw new IllegalStateException("Tree is empty");
        traversePostorder(root);
    }
    private void traversePostorder(LinkedBinaryTreeNode<E> node)
    {
        if (node.getLeft()!=null)traversePostorder(node.getLeft());
        if(node.getRight()!=null)traversePostorder(node.getRight());
        visit(node);
    }
    public void traverseInorder() throws IllegalStateException
    {
        if(isEmpty())throw new IllegalStateException("Tree is empty");
        traverseInorder(root);
    }
    private void traverseInorder(LinkedBinaryTreeNode<E> node)
    {
        if(node.getLeft()!=null)traverseInorder(node.getLeft());
        visit(node);
        if(node.getRight()!=null)traverseInorder(node.getRight());
    }
    @Override
    public <E> void visit(LinkedBinaryTreeNode<E> Node)
    {
        System.out.print(""+Node.getData()+" ");
    }
    private void setBalance(LinkedBinaryTreeNode<E> n)
    {
        n.balance = height(n.right)-height(n.left);
    }
    private int height(LinkedBinaryTreeNode<E> n)
    {
        if(n==null) {
            return -1;
        }
        if(n.left==null && n.right==null) {
            return 0;
        } else if(n.left==null) {
            return 1+height(n.right);
        } else if(n.right==null) {
            return 1+height(n.left);
        } else {
            return 1+maximum(height(n.left),height(n.right));
        }
    }
    private int maximum(int a, int b)
    {
        if(a>=b) {
            return a;
        } else {
            return b;
        }
    }
    public void insert(int k) {
        // create new node
        LinkedBinaryTreeNode<E> n = new LinkedBinaryTreeNode<E>(k);
        // start recursive procedure for inserting the node
        insertAVL(this.root,n);
    }

    /**
     * Recursive method to insert a node into a tree.
     *
     * @param p The node currently compared, usually you start with the root.
     * @param q The node to be inserted.
     */
    public void insertAVL(LinkedBinaryTreeNode<E> p, LinkedBinaryTreeNode<E> q) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if(p==null) {
            this.root=q;
        } else {

            // If compare node is smaller, continue with the left node
            if(q.key<p.key) {
                if(p.left==null) {
                    p.left = q;
                    q.parent = p;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.left,q);
                }

            } else if(q.key>p.key) {
                if(p.right==null) {
                    p.right = q;
                    q.parent = p;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.right,q);
                }
            } else {
                // do nothing: This node already exists
            }
        }
    }

    /**
     * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
     *
     * @param n : The node to check the balance for, usually you start with the parent of a leaf.
     */
    public void recursiveBalance(LinkedBinaryTreeNode<E> n)
    {

        // we do not use the balance in this class, but the store it anyway
        setBalance(n);
        int balance = n.balance;

        // check the balance
        if(balance==-2) {

            if(height(n.left.left)>=height(n.left.right)) {
                n = rotateRight(n);
            } else {
                n = doubleRotateLeftRight(n);
            }
        } else if(balance==2) {
            if(height(n.right.right)>=height(n.right.left)) {
                n = rotateLeft(n);
            } else {
                n = doubleRotateRightLeft(n);
            }
        }

        // we did not reach the root yet
        if(n.parent!=null) {
            recursiveBalance(n.parent);
        } else {
            this.root = n;
            System.out.println("------------ Balancing finished ----------------");
        }
    }
    public LinkedBinaryTreeNode<E> rotateLeft(LinkedBinaryTreeNode<E> n) 
    {

        LinkedBinaryTreeNode<E> v = n.right;
        v.parent = n.parent;

        n.right = v.left;

        if(n.right!=null) {
            n.right.parent=n;
        }

        v.left = n;
        n.parent = v;

        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     * Right rotation using the given node.
     *
     * @param n
     *            The node for the rotation
     *
     * @return The root of the new rotated tree.
     */
    public LinkedBinaryTreeNode<E> rotateRight(LinkedBinaryTreeNode<E> n) 
    {

        LinkedBinaryTreeNode<E> v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if(n.left!=null) {
            n.left.parent=n;
        }

        v.right = n;
        n.parent = v;


        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }
    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public LinkedBinaryTreeNode<E> doubleRotateLeftRight(LinkedBinaryTreeNode<E> u) 
    {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public LinkedBinaryTreeNode<E> doubleRotateRightLeft(LinkedBinaryTreeNode<E> u) 
    {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }
    public LinkedBinaryTreeNode<E> successor(LinkedBinaryTreeNode<E> q) 
    {
        if(q.right!=null) {
            LinkedBinaryTreeNode<E> r = q.right;
            while(r.left!=null) {
                r = r.left;
            }
            return r;
        } else {
            LinkedBinaryTreeNode<E> p = q.parent;
            while(p!=null && q==p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

}
