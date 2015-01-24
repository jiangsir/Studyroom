package tw.jiangsir.Utils.Tools;

import java.util.LinkedList;

public class FixedSizeQueue<E> extends LinkedList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4843529419105461336L;
	private int limit;

	public FixedSizeQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public boolean add(E o) {
		super.add(o);
		while (size() > limit) {
			super.remove();
		}
		return true;
	}

	public boolean isFull() {
		return size() == limit;
	}

}
