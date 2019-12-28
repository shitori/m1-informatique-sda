import java.lang.Comparable;
import java.util.ArrayList;
import java.util.List;

public class BinomialHeap<T extends Comparable<T>> {

    private Node<T> head;

    public BinomialHeap() {
        head = null;
    }

    public BinomialHeap(Node<T> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }

    /**
     * identique à l'algorithme vu en cours,
     * on créer un noeud à partir de la valeur key
     * avec lequel on créer un nouvel arbre que l'on
     * va unir avec la fonction union
     */
    public void insert(T key) {
        Node<T> node = new Node<T>(key);
        BinomialHeap<T> tempHeap = new BinomialHeap<T>(node);
        head = union(tempHeap);
    }

    /**
     * la fonction cherche parmis tout les freres
     * de la racine et elle-meme qui est le plus petit
     * et qui sera forcément la valeur minimal du tas
     */
    public T findMinimum() {
        if (head == null) {
            return null;
        } else {
            Node<T> min = head;
            Node<T> next = min.sibling;

            while (next != null) {
                if (next.compareTo(min) < 0) {
                    min = next;
                }
                next = next.sibling;
            }

            return min.key;
        }
    }

    public T extractMin() {
        if (head == null) {
            return null;
        }

        Node<T> min = head;
        Node<T> minPrev = null;
        Node<T> next = min.sibling;
        Node<T> nextPrev = min;
        // boncle permettant de localiser le minimum et son frere de gauche
        while (next != null) {
            if (next.compareTo(min) < 0) {
                min = next;
                minPrev = nextPrev;
            }
            nextPrev = next;
            next = next.sibling;
        }

        removeTreeRoot(min, minPrev);
        return min.key;
    }

    private void removeTreeRoot(Node<T> root, Node<T> prev) {
        // si le min etait à la tete de l'arbre binomial
        if (root == head) {
            head = root.sibling; // alors la tete de l'arbre de l'arbre devient le frere de la tete actuel
        } else {
            prev.sibling = root.sibling; // sinon on relie le frere de gauche de min au frere de droite de min
        }

        // fonction qui inverse l'ordre chainé des enfants du min
        Node<T> newHead = null;
        Node<T> child = root.child;
        while (child != null) {
            Node<T> next = child.sibling; // permet de sauvegardé les freres de droite
            child.sibling = newHead; // supprime les frères par les nouveaux
            child.parent = null; // supprime le parent
            newHead = child; // déplace la tete vers le frere le plus à gauche
            child = next; // passe au frere de droite suivant
        }
        // on crée un nouvel arbre à partir de cette nouvelle tete
        BinomialHeap<T> newHeap = new BinomialHeap<T>(newHead);

        // puis on unis notre arbre avec l'arbre précedent
        head = union(newHeap);
    }

    /**
     * identique à l'algorithme vu en cours,
     * permet de lié deux arbres entre eux,
     * où minNodeTree de vient le pere de other
     */
    private void linkTree(Node<T> minNodeTree, Node<T> other) {
        other.parent = minNodeTree;
        other.sibling = minNodeTree.child;
        minNodeTree.child = other;
        minNodeTree.degree++;
    }

    public Node<T> union(BinomialHeap<T> heap) {
        // on commence par fusionne notre arbre avec l'arbre heap
        Node<T> newHead = merge(this, heap);
        // on libere les deux arbre
        head = null;
        heap.head = null;

        if (newHead == null) {
            return null;
        }

        Node<T> prev = null;
        Node<T> curr = newHead;
        Node<T> next = newHead.sibling;
        // boucle qui lie les arbre lie 2 freres de meme dégré en fonction de qui à la racine la plus grande
        while (next != null) {
            if (curr.degree != next.degree || (next.sibling != null &&
                    next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } else {
                if (curr.compareTo(next) < 0) {
                    curr.sibling = next.sibling;
                    linkTree(curr, next);
                } else {
                    if (prev == null) {
                        newHead = next;
                    } else {
                        prev.sibling = next;
                    }

                    linkTree(next, curr);
                    curr = next;
                }
            }

            next = curr.sibling;
        }

        return newHead;
    }

    /**
     * cette fonction retourne un noeud avec ses freres triés
     * par rapport au dégré croissant
     */
    private static <T extends Comparable<T>> Node<T> merge(
            BinomialHeap<T> heap1, BinomialHeap<T> heap2) {
        if (heap1.head == null) {
            return heap2.head;
        } else if (heap2.head == null) {
            return heap1.head;
        } else {
            Node<T> head;
            Node<T> heap1Next = heap1.head;
            Node<T> heap2Next = heap2.head;
            // condition pour savoir par quelle arbre son commence
            if (heap1.head.degree <= heap2.head.degree) {
                head = heap1.head;
                heap1Next = heap1Next.sibling;
            } else {
                head = heap2.head;
                heap2Next = heap2Next.sibling;
            }

            Node<T> tail = head;
            // boucle qui rajoute les freres de heap1 et heap2 en fonction du degréé
            while (heap1Next != null && heap2Next != null) {
                if (heap1Next.degree <= heap2Next.degree) {
                    tail.sibling = heap1Next;
                    heap1Next = heap1Next.sibling;
                } else {
                    tail.sibling = heap2Next;
                    heap2Next = heap2Next.sibling;
                }

                tail = tail.sibling;
            }
            // fini par ajouté le reste de heap1 ou heap2
            if (heap1Next != null) {
                tail.sibling = heap1Next;
            } else {
                tail.sibling = heap2Next;
            }

            return head;
        }
    }

    public void print() {
        System.out.println("Binomial heap:");
        if (head != null) {
            head.print(0);
        }
    }

    public static class Node<T extends Comparable<T>>
            implements Comparable<Node<T>> {
        public T key;
        public int degree;
        public Node<T> parent;
        public Node<T> child;
        public Node<T> sibling;

        public Node() {
            key = null;
        }

        public Node(T key) {
            this.key = key;
        }

        /**
         * fonction de comparaison entre 2 node
         */
        public int compareTo(Node<T> other) {
            return this.key.compareTo(other.key);
        }


        public void print(int level) {
            Node<T> curr = this;
            while (curr != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < level; i++) {
                    sb.append(" ");
                }
                sb.append(curr.key.toString());
                System.out.println(sb.toString());
                if (curr.child != null) {
                    curr.child.print(level + 1);
                }
                curr = curr.sibling;
            }
        }
    }

}
