package tw.jiangsir.Studyroom.Objects;

import java.util.Comparator;
import java.util.PriorityQueue;

import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Tools.DateTool;

public class ViolationQueue extends PriorityQueue<Violation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1379321256686109356L;

	// private static PriorityQueue<Violation> queue = new
	// PriorityQueue<Violation>(
	// ApplicationScope.getAppConfig().getPunishingthreshold(),
	// new Comparator<Violation>() {
	// public int compare(Violation x, Violation y) {
	// if (x.getDate().after(y.getDate())) {
	// return 1;
	// } else if (x.getDate().before(y.getDate())) {
	// return -1;
	// } else {
	// return 0;
	// }
	// }
	// });

	private int limit;

	public ViolationQueue(int limit) {
		super(limit, new Comparator<Violation>() {
			public int compare(Violation x, Violation y) {
				if (x.getDate().after(y.getDate())) {
					return 1;
				} else if (x.getDate().before(y.getDate())) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		this.limit = limit;
	}

	public boolean isFull() {
		return size() == limit;
	}

	public Violation getLast() {
		return (Violation) super.toArray()[size() - 1];
	}

}
