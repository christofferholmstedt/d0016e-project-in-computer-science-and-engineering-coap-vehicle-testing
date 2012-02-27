package se.willliamgustafsson.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<E> {
	
	private List<Observer<E>> observers = new ArrayList<Observer<E>>();
	
	public void add(Observer<E> observer){
		observers.add(observer);
	}
	
	public void notifyObserver(E e){
		for (Observer<E> observer : observers) {
			observer.onNotify(e);
		}
	}
}
