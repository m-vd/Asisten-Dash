public class Identity<T,V> {
	private T firstIdentity;
	private V secondIdentity;

	public T getFirstIdentity() {
		return firstIdentity;
	}

	public V getSecondIdentity() {
		return secondIdentity;
	}

	public Identity(T firstIdentity, V secondIdentity) {
		this.firstIdentity = firstIdentity;
		this.secondIdentity = secondIdentity;
	}


}