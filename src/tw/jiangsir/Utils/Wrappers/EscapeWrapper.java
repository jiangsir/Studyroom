package tw.jiangsir.Utils.Wrappers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

public class EscapeWrapper extends HttpServletRequestWrapper {

	public EscapeWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		System.out.print("parameter name=" + name);
		String value = this.getRequest().getParameter(name);
		System.out.print(", value=" + value);

		// value = StringEscapeUtils.escapeHtml(value);
		// value = StringEscapeUtils.escapeXml(value);
		value = StringEscapeUtils.escapeSql(value);
		System.out.println(", value=" + value);
		return value;
	}
}
