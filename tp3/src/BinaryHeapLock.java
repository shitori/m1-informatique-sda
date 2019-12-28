import java.util.ArrayList;

/**
 * Cette classe est un proxy pour les ArrayList, c'est à dire les tableaux dynamiques en Java.
 * On utilise cette classe afin d'avoir le contrôle sur la gestion de la mémoire.
 */
public class BinaryHeapLock<T> {

    private ArrayList<T> data;
    private int capacity;

    /**
     * nous fixons ici la capacité max à 800000
     */
    public BinaryHeapLock() {
        this.capacity = 800000;
        this.data = new ArrayList<T>(this.capacity);
    }

    int dad(int i) {
        return (int) (i - 1) / 2;
    }

    int sonLeft(int i) {
        return 2 * i + 1;
    }

    int sonRight(int i) {
        return 2 * i + 2;
    }

    /**
     * Fonction qui permet de retrourner le
     * boolean de arg1>arg2, dans notre cas nous intéressons
     * qu'au entier
     */
    <T> boolean superior(T arg1, T arg2) {
        if (arg1 == null || arg2 == null) {
            return false;
        } else if (arg1 instanceof Integer && arg2 instanceof Integer) {
            int x = (Integer) arg1;
            int y = (Integer) arg2;
            return x > y;
        } else {
            return false;
        }
    }

    /**
     * fonction entasser identique à l'algorithme vu en cours
     * dont le but est de faire de l'arbre de racine i un tas
     */
    void heapUp(int i) {
        int l = sonLeft(i);
        int r = sonRight(i);
        int min;
        if (l < size() && superior(get(i), get(l))) {
            min = l;
        } else {
            min = i;
        }
        if (r < size() && superior(get(i), get(r)) && superior(get(l), get(r))) {
            min = r;
        }
        if (min != i) {
            T tmp = get(i);
            set(i, get(min));
            set(min, tmp);
            heapUp(min);
        }
    }

    T minimum() {
        return get(0);
    }

    /**
     * identique au cours,
     * on retourne null si l'arbre est vide,
     * sinon on recupere le min et on réarrange l'abre
     */
    T extractMin() {
        if (size() == 0) {
            return null;
        }
        T min = get(0);
        set(0, get(size() - 1));
        remove(size() - 1);
        heapUp(0);
        return min;
    }

    /**
     * fonction utilisé pendant l'insertion afin de remonté
     * dans l'arbre la nouvelle valeur
     */
    void reduceKey(int i, T k) {
        if (superior(k, get(i))) {
            System.err.println("erreur: " + k + ">" + get(i));
            return;
        }
        set(i, k);
        while (i > 0 && superior(get(dad(i)), get(i))) {
            T tmp = get(dad(i));
            set(dad(i), get(i));
            set(i, tmp);
            i = dad(i);
        }
    }

    /**
     * la fonction ajoute à l'arraylist
     * la nouvelle valeur et utilise la fonction
     * précédente, si la taille maximal est
     * atteinte, une exception se lance
     */
    void insert(T k) throws Exception {
        if (size()==capacity()){
            throw new Exception("capacité max atteind");
        }
        add(k);
        reduceKey(size() - 1, k);
    }

    void printer() {
        System.out.println("Data :" + data.toString());
    }

    T get(int pos) {
        return data.get(pos);
    }

    void set(int pos, T value) {
        data.set(pos, value);
    }

    void add(T value) {
        data.add(value);
    }

    int size() {
        return data.size();
    }

    void remove(int pos) {
        data.remove(pos);
    }

    int capacity() {
        return capacity;
    }
}
