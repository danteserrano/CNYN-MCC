package io.github.danteserrano.util;

import org.jetbrains.annotations.NotNull;

public class ComparableWrapper<T> implements Comparable<ComparableWrapper<T>>{
    static @NotNull Integer nextId = 0;
    private final @NotNull Integer mId;
    private final T mInner;

    public ComparableWrapper(T inner){
        mInner = inner;
        mId = nextId;
        nextId++;
    }

    public T getInner() {
        return mInner;
    }

    @Override
    public int compareTo(@NotNull ComparableWrapper<T> o) {
        return mId.compareTo(o.mId);
    }
}
