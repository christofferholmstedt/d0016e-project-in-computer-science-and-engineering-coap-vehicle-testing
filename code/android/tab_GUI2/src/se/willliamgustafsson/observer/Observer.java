package se.willliamgustafsson.observer;

public interface Observer<E> {

	public void onNotify(E e);
}
