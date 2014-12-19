package org.cgz.oseye.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 私信
 * @author gz
 *
 */
@Entity
@Table(name = "ose_private_message")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Private_Message extends BaseModel implements Serializable {

	private static final long serialVersionUID = -284742973358539584L;
	
	/**私信编号**/
	private Integer id;
	/**发送者**/
	private Users sender;		
	/**接收者**/
	private Users receiver;	
	/**消息的创建时间**/
	private Date createTime = new Date();
	/**消息内容**/
	private String content;
	/**阅读状态**/
	private Short readStatus = SystemConstant.MESSAGE_NEW;
	/**阅读时间**/
	private Date readtime;
	/**用户**/
	private Users user;
	/**私信对话的另一方**/
	private Users friend;
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false)
	public Users getSender() {
		return sender;
	}
	public void setSender(Users sender) {
		this.sender = sender;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false)
	public Users getReceiver() {
		return receiver;
	}
	public void setReceiver(Users receiver) {
		this.receiver = receiver;
	}
	
	@Column(name = "sent_time", nullable = false, length = 0)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "content", nullable = false, length = 500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "read_status", nullable = false)
	public Short getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Short readStatus) {
		this.readStatus = readStatus;
	}
	
	@Column(name = "read_time",length = 0)
	public Date getReadtime() {
		return readtime;
	}
	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="friend_id",nullable=false)
	public Users getFriend() {
		return friend;
	}
	public void setFriend(Users friend) {
		this.friend = friend;
	}
}
