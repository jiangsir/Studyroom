package tw.jiangsir.Utils.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tw.jiangsir.Utils.Objects.User;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServletRole {
	/**
	 * 指定高於(含)某角色者允許存取。
	 * 
	 * @return
	 */
	User.ROLE allowHigherThen() default User.ROLE.ADMIN;

	/**
	 * 指定低於(含)某個角色者禁止存取。
	 * 
	 * @return
	 */
	User.ROLE denyLowerThen() default User.ROLE.GUEST;

	/**
	 * 指定不允許存取的角色，若角色重疊，則 denyRoles 優先於 allowRoles
	 * 
	 * @return
	 */
	User.ROLE[] denys() default {};

	/**
	 * 指定允許存取的角色，若角色重疊，則 denyRoles 優先於 allowRoles
	 * 
	 * @return
	 */
	User.ROLE[] allows() default {};

}
