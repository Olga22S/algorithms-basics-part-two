package ru.skypro.integerList;

import ru.skypro.exception.AbsentItemException;
import ru.skypro.exception.FullStorageException;
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
            throw new FullStorageException("Storage if full!");
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
        sortInsertion(copiedStorage);
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

    private void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
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

