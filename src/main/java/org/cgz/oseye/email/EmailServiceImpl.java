package org.cgz.oseye.email;


import freemarker.template.Template;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import java.util.Map;

@Service("emailService")
public class EmailServiceImpl implements EmailService{

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TaskExecutor taskExecutor;
    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 发送带模板的单个html格式邮件
     */
    public void sendMailBySynchronizationMode(final Email email) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
        	// 设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
            helper.setTo(email.getReceiver()); // 邮件接收地址
            helper.setFrom(Email.SENDER); // 邮件发送地址,这里必须和xml里的邮件地址相同一致
            helper.setSubject(email.getTitle()); // 主题
            String htmlText;
            htmlText = getMailContent(email.getDataMap(),email.getTemplateFileName());
            helper.setText(htmlText, true); // 邮件内容，注意加参数true，表示启用html格式
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(msg); // 发送邮件
    }


    // 通过模板构造邮件内容
    public String getMailContent(Map<String, Object> dataMap,String templateFileName) throws Exception {
        String htmlText = "";
        // 通过指定模板名获取FreeMarker模板实例
        Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateFileName);
        // 解析模板并替换动态数据
        htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, dataMap);
        return htmlText;
    }
    
	/**
	 * 异步发送
	 * @param email
	 */
	public void sendMailByAsynchronousMode(final Email email) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
