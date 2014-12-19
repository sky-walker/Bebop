package org.cgz.oseye.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.cgz.oseye.utils.HtmlUtil;

public class HtmlSummaryTag  extends TagSupport{

	private static final long serialVersionUID = -1129219760166737417L;

	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut(); 
		html = HtmlUtil.delAllTag(html.trim());
		try {
			if(html.length()>200) {
				html = html.substring(0, 200) + "...";
			}
			out.write("".equals(html.trim())?"":"&nbsp;&nbsp;"+html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
