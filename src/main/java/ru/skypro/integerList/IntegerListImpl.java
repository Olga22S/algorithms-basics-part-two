package ru.skypro.integerList;

import ru.skypro.exception.AbsentItemException;
import ru.skypro.exception.IndexOutsideException;
import ru.skypro.exception.NullItemException;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class IntegerListImpl implements IntegerList {

    private Integer[] storage;
    private int count = 0;

    public IntegerListImpl(int capacity) {
        this.storage = new Integer[capacity];
    }

    @Override
    public Integer add(Integer item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (count == storage.length) {
            grow();
        }
        storage[count++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        count++;
        System.arraycopy(storage, index, storage, index + 1, count - index - 1);
        storage[index] = item;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        storage[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        int index = indexOf(item);
        if (index == -1) {
            throw new AbsentItemException("This item is absent!");
        }
        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        Integer item = storage[index];
        count--;
        System.arraycopy(storage, index + 1, storage, index, count - index);
        storage = Arrays.copyOf(storage, count);
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] copiedStorage = Arrays.copyOf(storage, count);
        quickSort(copiedStorage, 0, copiedStorage.length - 1);
        return binarySearch(copiedStorage, item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < count; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = count - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        return storage[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (isNull(otherList)) {
            throw new NullItemException("Item is null!");
        }
        for (int i = 0; i < count; i++) {
            if (!otherList.get(i).equals(storage[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count > 0;
    }

    @Override
    public void clear() {
        count = 0;
        storage = new Integer[0];
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(storage, count);
    }

    private void grow() {
        storage = Arrays.copyOf(storage, (int) (storage.length * 1.5));
    }

    public static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }
        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    public boolean binarySearch(Integer[] arr, int element) {
        int min = 0;
        int max = arr.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}

