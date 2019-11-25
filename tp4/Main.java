
public class Main{
  public static void main(String[] args) {
    BTree<Integer> arbre1 = new BTree<Integer>(4);
    SelfBalancingBinarySearchTree arbre2 = new SelfBalancingBinarySearchTree();

    arbre1.add(5);
    arbre2.insert(5);

    System.out.println("B-Tree\n");
    System.out.println(arbre1);
    System.out.print("AVL: ");
    arbre2.inorder();
    System.out.println();
  }
}
