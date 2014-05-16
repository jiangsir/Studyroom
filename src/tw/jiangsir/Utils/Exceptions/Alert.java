package tw.jiangsir.Utils.Exceptions;

import java.net.URL;
import java.util.HashMap;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Alert {

	public static enum TYPE {
		INFO, // 處理一些一般訊息的呈現。
		WARNING, // 顯示使用者填報錯誤，或違規行為。
		EXCEPTION, // 系統丟出的例外。
		ERROR // 顯示一些錯誤訊息。
	};

	@Persistent(name = "type")
	private TYPE type = TYPE.INFO;
	@Persistent(name = "title")
	private String title = "";
	@Persistent(name = "subtitle")
	private String subtitle = "";
	@Persistent(name = "content")
	private String content = "";
	@Persistent(name = "stacktrace")
	private StackTraceElement[] stacktrace = new StackTraceElement[] {};
	@Persistent(name = "contentmap")
	private HashMap<String, String> contentmap = new HashMap<String, String>();
	@Persistent(name = "urls")
	private HashMap<String, URL> urls = new HashMap<String, URL>();

	public Alert() {
	}

	public Alert(Throwable throwable) {
		this.setType(TYPE.EXCEPTION);
		this.setTitle(throwable.getLocalizedMessage());
		this.setSubtitle(throwable.getClass().getName());
		this.setStacktrace(throwable.getStackTrace());
	}

	public Alert(Cause cause) {
		this.setType(TYPE.EXCEPTION);
		this.setTitle(cause.getTitle());
		this.setSubtitle(cause.getSubtitle());
		this.setStacktrace(cause.getStackTrace());
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StackTraceElement[] getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(StackTraceElement[] stacktrace) {
		this.stacktrace = stacktrace;
	}

	public HashMap<String, String> getContentmap() {
		return contentmap;
	}

	public void setContentmap(HashMap<String, String> contentmap) {
		this.contentmap = contentmap;
	}

	public HashMap<String, URL> getUrls() {
		return urls;
	}

	public void setUrls(HashMap<String, URL> urls) {
		this.urls = urls;
	}

}
