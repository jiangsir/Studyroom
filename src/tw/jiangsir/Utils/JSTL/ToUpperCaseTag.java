package tw.jiangsir.Utils.JSTL;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ToUpperCaseTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		this.getJspBody().invoke(writer);
		String upper = writer.toString().toUpperCase();
		this.getJspContext().getOut().print(upper);
	}

}
