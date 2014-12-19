package org.cgz.oseye.email;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Email implements Serializable {

	
	private static final long serialVersionUID = 5558012177809382751L;

	/**
	 * 发送人
	 */
	public static final String SENDER = "xxxx@xxx.com";
	
    /**
     * 收件人地址
     */
    private String receiver;

    /**
     * 邮件主题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;
    
    /**
     * 邮件模板数据模型
     */
    private Map<String,Object> dataMap = new HashMap<String,Object>(); 
    
    /**
     * 模板文件名
     */
    private String templateFileName;
    
    
	public Email() {}
	
	
	
	
	/**
	 * @param receiver
	 * @param title
	 * @param templateFileName
	 */
	public Email(String receiver, String title, String templateFileName) {
		super();
		this.receiver = receiver;
		this.title = title;
		this.templateFileName = templateFileName;
	}


	/**
	 * @param receiver
	 * @param title
	 * @param content
	 * @param templateFileName
	 */
	public Email(String receiver, String title, String content,String templateFileName) {
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.templateFileName = templateFileName;
	}

	/**
	 * @param receiver
	 * @param title
	 * @param content
	 * @param dataMap
	 * @param templateFileName
	 */
	public Email(String receiver, String title, String content,Map<String, Object> dataMap, String templateFileName) {
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.dataMap = dataMap;
		this.templateFileName = templateFileName;
	}

	public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public Map<String,Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String,Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
}
