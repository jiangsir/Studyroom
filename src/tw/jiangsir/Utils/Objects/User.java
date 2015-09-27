package tw.jiangsir.Utils.Objects;

import java.io.Serializable;
import tw.jiangsir.Utils.Annotations.Persistent;
import tw.jiangsir.Utils.Exceptions.DataException;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6818084467792604935L;

	public enum ROLE {
		ADMIN, // 管理權限
		MANAGER, // 一般管理員
		USER, // 一般使用者
		GUEST; // 訪客，或未登入者
	}

	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "account")
	private String account = "";
	@Persistent(name = "passwd")
	private String passwd = "";
	@Persistent(name = "email")
	private String email = "";
	@Persistent(name = "picture")
	private String picture = "";
	@Persistent(name = "name")
	private String name = "";
	@Persistent(name = "role")
	private ROLE role = ROLE.GUEST;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		if (account == null || "".equals(account)) {
			throw new DataException("帳號不可以為空！");
		}
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		if (passwd == null || "".equals(passwd)) {
			throw new DataException("密碼不可為空！");
		}
		this.passwd = passwd;
	}

	public void setPasswd(String passwd1, String passwd2) {
		if (passwd1 == null || passwd2 == null || "".equals(passwd1)
				|| "".equals(passwd2)) {
			throw new DataException("密碼不可為空！");
		}
		if (!passwd1.equals(passwd2)) {
			throw new DataException("兩次密碼不相同！");
		}
		this.setPasswd(passwd2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || "".equals(name)) {
			throw new DataException("姓名不可為空！");
		}
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null) {
			return;
		}
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		if (picture == null) {
			return;
		}
		this.picture = picture;
	}

	public ROLE[] getRoles() {
		return ROLE.values();
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void setRole(String role) {
		try {
			this.setRole(ROLE.valueOf(role));
		} catch (Exception e) {
			throw new DataException("角色不存在！" + e.getLocalizedMessage());
		}
	}

}
