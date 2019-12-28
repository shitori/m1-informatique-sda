import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int index;
        BinomialHeap<Integer> i = new BinomialHeap<Integer>();
        BinomialHeap<Integer> d = new BinomialHeap<Integer>();
        BinomialHeap<Integer> r = new BinomialHeap<Integer>();
        BinomialHeap<Integer> f = new BinomialHeap<Integer>();
        /*BinaryHeap<Integer> i = new BinaryHeap<Integer>();
        BinaryHeap<Integer> d = new BinaryHeap<Integer>();
        BinaryHeap<Integer> r = new BinaryHeap<Integer>();
        BinaryHeap<Integer> f = new BinaryHeap<Integer>();*/
        BinaryHeapLock<Integer> il = new BinaryHeapLock<Integer>();
        BinaryHeapLock<Integer> dl = new BinaryHeapLock<Integer>();
        BinaryHeapLock<Integer> rl = new BinaryHeapLock<Integer>();
        BinaryHeapLock<Integer> fl = new BinaryHeapLock<Integer>();
        Analyzer time_increase = new Analyzer();
        Analyzer time_decrease = new Analyzer();
        Analyzer time_random = new Analyzer();
        Analyzer time_full = new Analyzer();
        Analyzer time_increaseL = new Analyzer();
        Analyzer time_decreaseL = new Analyzer();
        Analyzer time_randomL = new Analyzer();
        Analyzer time_fullL = new Analyzer();
        long beforeI, afterI;
        long beforeD, afterD;
        long beforeR, afterR;
        long beforeF, afterF;
        long beforeIl, afterIl;
        long beforeDl, afterDl;
        long beforeRl, afterRl;
        long beforeFl, afterFl;
        Random rd = new Random(11500697);
        for (index = 0; index < 1000000; index++) {
            beforeI = System.nanoTime();
            i.insert(index);
            afterI = System.nanoTime();
            time_increase.append(afterI - beforeI);

            beforeIl = System.nanoTime();
            try {
                il.insert(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            afterIl = System.nanoTime();
            time_increaseL.append(afterIl - beforeIl);

            beforeD = System.nanoTime();
            d.insert(1000000 - index);
            afterD = System.nanoTime();
            time_decrease.append(afterD - beforeD);

            beforeDl = System.nanoTime();
            try {
                dl.insert(1000000 - index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            afterDl = System.nanoTime();
            time_decreaseL.append(afterDl - beforeDl);

            int value = Math.abs(rd.nextInt());
            beforeR = System.nanoTime();
            r.insert(value);
            afterR = System.nanoTime();
            time_random.append(afterR - beforeR);

            beforeRl = System.nanoTime();
            try {
                rl.insert(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            afterRl = System.nanoTime();
            time_randomL.append(afterRl - beforeRl);

            boolean bool = rd.nextBoolean();
            if (bool) {
                beforeF = System.nanoTime();
                f.insert(value);
                afterF = System.nanoTime();
            } else {
                beforeF = System.nanoTime();
                f.extractMin();
                afterF = System.nanoTime();
            }
            time_full.append(afterF - beforeF);

            if (bool) {
                beforeFl = System.nanoTime();
                try {
                    fl.insert(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                afterFl = System.nanoTime();
            } else {
                beforeFl = System.nanoTime();
                fl.extractMin();
                afterFl = System.nanoTime();
            }
            time_fullL.append(afterFl - beforeFl);
        }
        /*time_increase.save_values("binomial_increase.plot");
        time_decrease.save_values("binomial_decrease.plot");
        time_random.save_values("binomial_random.plot");
        time_full.save_values("binomial_full.plot");*/
        time_increase.save_values("binary_increase.plot");
        time_decrease.save_values("binary_decrease.plot");
        time_random.save_values("binary_random.plot");
        time_full.save_values("binary_full.plot");

        time_increaseL.save_values("binaryL_increase.plot");
        time_decreaseL.save_values("binaryL_decrease.plot");
        time_randomL.save_values("binaryL_random.plot");
        time_fullL.save_values("binaryL_full.plot");
    }

}

