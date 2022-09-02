package io.github.danteserrano.arena;

public class ComparableWrapper<T> implements Comparable<ComparableWrapper<T>>{
    static Integer nextId = 0;
    private final Integer mId;
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
    public int compareTo(ComparableWrapper<T> o) {
        return mId.compareTo(o.mId);
    }
}
