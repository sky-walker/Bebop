package org.cgz.oseye.tags;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 对页面中的字符进行url编码和解码 
 * @author Administrator
 */
public class URLCodeTag extends TagSupport{

	private static final long serialVersionUID = -5826654114710481427L;
	
	private static final String ENCODE = "encode";
	private static final String DECODE = "decode";
	
	private String str;
	
	private String deOrEncode;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getDeOrEncode() {
		return deOrEncode;
	}

	public void setDeOrEncode(String deOrEncode) {
		this.deOrEncode = deOrEncode;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut(); 
		if(!"".equals(str.trim()) || null!=str.trim()) {
			if(ENCODE.equals(deOrEncode)) {
				try {
					str = URLEncoder.encode(str,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(DECODE.equals(deOrEncode)) {
				try {
					str = URLDecoder.decode(str,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			try {
				
				out.write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
}
