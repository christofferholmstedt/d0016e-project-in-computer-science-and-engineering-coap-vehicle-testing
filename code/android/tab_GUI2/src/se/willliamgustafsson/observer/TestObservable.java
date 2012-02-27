package se.willliamgustafsson.observer;

public class TestObservable {
	
	private Observable<Integer> observable = new Observable<Integer>();
	
	public Observable<Integer> getObservable() {
		return observable;
	}
	
	public static void main(String[] args) {
		
		TestObservable t = new TestObservable();
		TestObserver o = new TestObserver();
		
		t.getObservable().add(o);
		
		t.getObservable().notifyObserver(new Integer(0));
		
	}

}
