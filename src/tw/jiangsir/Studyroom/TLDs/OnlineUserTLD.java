package tw.jiangsir.Studyroom.TLDs;

import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;

public class OnlineUserTLD {
	/**
	 * 給 JSTL 呼叫是用在 View 端的
	 * 
	 * @return
	 */
	public static boolean isAdmin(CurrentUser currentUser) {
		return currentUser.getRole() == User.ROLE.ADMIN;
	}

}
