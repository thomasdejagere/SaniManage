package domain;

import java.util.Observable;

public abstract class ModelObservable<E> extends Observable {

    public abstract int aantal();

    public abstract E getAtIndex(int index);
}
