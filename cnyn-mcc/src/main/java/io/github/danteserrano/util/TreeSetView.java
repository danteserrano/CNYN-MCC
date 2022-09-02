package io.github.danteserrano.util;

import java.util.TreeSet;

public class TreeSetView<T> {
    private final TreeSet<T> mTreeSet;

    public TreeSetView(TreeSet<T> treeSet) {
        this.mTreeSet = treeSet;
    }

    public int size() {
        return mTreeSet.size();
    }

    public boolean contains(Object o) {
        return mTreeSet.contains(o);
    }
}
