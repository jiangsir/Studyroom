/**
 * idv.jiangsir.objects - Contest.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package tw.jiangsir.Utils.Config;

import tw.jiangsir.Utils.Annotations.Property;

/**
 * @author jiangsir
 * 
 */
public class AppConfig {

    @Property(key = "ManagerIP")
    private String ManagerIP = "*";

    @Property(key = "Title")
    private String Title = "A Title for Your Site";

    @Property(key = "Author")
    private String Author = "Qixun Jiang";
    @Property(key = "AuthorEmail")
    private String AuthorEmail = "jiangsir@zerojudge.tw";
    @Property(key = "AllowedIP")
    private String AllowedIP = "*";
    @Property(key = "PageSize")
    private int PageSize = 20;

    public String getManagerIP() {
	return ManagerIP;
    }

    public void setManagerIP(String managerIP) {
	ManagerIP = managerIP;
    }

    public String getTitle() {
	return Title;
    }

    public void setTitle(String title) {
	Title = title;
    }

    public String getAuthor() {
	return Author;
    }

    public void setAuthor(String author) {
	Author = author;
    }

    public String getAuthorEmail() {
	return AuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
	AuthorEmail = authorEmail;
    }

    public String getAllowedIP() {
	return AllowedIP;
    }

    public void setAllowedIP(String allowedIP) {
	AllowedIP = allowedIP;
    }

    public int getPageSize() {
	return PageSize;
    }

    public void setPageSize(String pageSize) {
	PageSize = Integer.parseInt(pageSize);
    }

}
