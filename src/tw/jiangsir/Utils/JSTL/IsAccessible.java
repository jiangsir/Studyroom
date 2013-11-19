package tw.jiangsir.Utils.JSTL;

import tw.jiangsir.Utils.Objects.User;

public class IsAccessible {
	public static boolean isUrlVisible(User user) {
		if (user != null && user.getRole() == User.ROLE.ADMIN) {
			return true;
		}
		return false;
	}
}
