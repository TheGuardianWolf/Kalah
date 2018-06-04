package kalah.util;

import java.util.*;

public class Circular2DArrayList<E> extends ArrayList<List<E>> {
    public int circularSize() {
        int count = 0;
        for (List<E> ls : this) {
            count += ls.size();
        }
        return count;
    }

    public int toCircularIndex(int i, int j) {
        int circularIndex = 0;

        for (int k = 0; k < i; k++) {
            circularIndex += this.get(k).size();
        }

        return circularIndex + j;
    }

    public int toFlatIndex(int circularIndex) {
        int index = circularIndex % circularSize();

        int retVal = 0;
        for (List<E> list : this) {
            index -= list.size();

            if (index < 0) {
                return retVal;
            }

            retVal++;
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(Object item) {
        int i = 0;

        for (List<E> ls : this) {
            int itemIndex = ls.indexOf(item);

            if (itemIndex > -1) {
                return itemIndex + i;
            }

            i += ls.size();
        }

        return -1;
    }

    public E circularGet(int i) {
        int index = i % circularSize();
        while (index >= 0) {
            for (List<E> ls : this) {
                if (index < ls.size()) {
                    return ls.get(index);
                }
                index -= ls.size();
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public E circularGetOpposite(int i) {
        int cSize = circularSize();
        int opposite = cSize - i % cSize - 1;
        return circularGet(opposite);
    }

}
