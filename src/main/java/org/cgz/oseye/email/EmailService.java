package org.cgz.oseye.email;


public interface EmailService {

    /**
     * 同步发送邮件
     */
    public void sendMailBySynchronizationMode(Email email);
    
    /**
     * 异步发送邮件
     */
    public void sendMailByAsynchronousMode(Email email);

}
