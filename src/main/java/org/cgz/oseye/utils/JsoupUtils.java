package org.cgz.oseye.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 * Jsoup工具类
 * @author Administrator
 *
 */
public class JsoupUtils{
	
	/**
	 * html过滤白名单
	 */
	private final static Whitelist user_content_filter = Whitelist.relaxed();
	
	static {
		user_content_filter.addTags("embed","object","param","span","div");
		user_content_filter.addAttributes(":all","codetype","src","style", "class", "id", "name");
		user_content_filter.addAttributes("object", "width", "height","classid","codebase");	
		user_content_filter.addAttributes("param", "name", "value");
		user_content_filter.addAttributes("embed","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");
	}
	
	/**
	 * 处理博客首页文章导读
	 * @param html
	 * @return
	 */
	public static String htmlProcess(String html,String link) {
		Document doc = Jsoup.parse(html);
		Elements imgs = doc.select("img");
		for(int i=0;i<imgs.size();i++){
			Element img = imgs.get(i);
			if(i==1) {
				img.addClass("tempimg");
				img.attr("src", "/images/icons/glyphicons_138_picture.png");
				if(link!=null) {
					img.wrap("<a href='"+link+"'></a>");
				}
			}else {
				img.remove();
			}
		}
		return doc.body().html();
	}
	
	/**
	 * 处理后台博客评论显示(重要是图片显示)
	 * @param html
	 * @return
	 */
	public static String commentHtmlProcess(String html,String imgWidth,String imgHeight) {
		Document doc = Jsoup.parse(html);
		Elements imgs = doc.select("img");
		for(int i=0;i<imgs.size();i++){
			Element img = imgs.get(i);
			String imgsrc = img.attr("src");
			img.addClass("tempimg");
			img.attr("width", imgWidth);
			img.attr("height", imgHeight);
			img.wrap("<a href='"+imgsrc+"' target='blank'></a>");
		}
		return doc.body().html();
	}
	
	/**
	 * 处理博客首页文章导读
	 * @param html
	 * @return
	 */
	public static String commentHtmlProcess(String html) {
		Document doc = Jsoup.parse(html);
		return doc.body().html();
	}
	
	/**
	 * 对用户输入内容进行过滤
	 * @param html
	 * @param domain
	 * @return
	 */
	public static String cleanHtml(String html,String domain) {
		if(org.jsoup.helper.StringUtil.isBlank(html)) return "";
		Document doc = Jsoup.parse(html);
		return Jsoup.clean(doc.body().html(),domain,user_content_filter);
	}
}
