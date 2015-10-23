public class TreeDriver {
    public static void main(String args[])
    {
        LinkedBinaryTree<String> tree=new LinkedBinaryTree<String>();
        LinkedBinaryTreeNode<String> node;
        node=tree.addRoot("-");
        tree.addLeft(node,"/");
        tree.addRight(node,"+");
        tree.traversePreorder();
        System.out.println();
        tree.traversePostorder();
        System.out.println();
        tree.traverseInorder();
    }
}
