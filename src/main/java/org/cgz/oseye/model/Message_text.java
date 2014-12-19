package org.cgz.oseye.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ose_message_text")
public class Message_text implements java.io.Serializable {

	private static final long serialVersionUID = 1837590511819033155L;

	private Integer id;
	private Users sender;
	private String content;
	private Date sentTime;
	private Short senderDeleteStatus;
	private Short type;
	private Set<Message> messages = new HashSet<Message>(0);

	public Message_text() {
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false)
	public Users getSender() {
		return this.sender;
	}

	public void setSender(Users sender) {
		this.sender = sender;
	}

	@Column(name = "content", nullable = false, length = 300)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "sent_time", nullable = false, length = 0)
	public Date getSentTime() {
		return this.sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	@Column(name = "sender_delete_status", nullable = false)
	public Short getSenderDeleteStatus() {
		return this.senderDeleteStatus;
	}

	public void setSenderDeleteStatus(Short senderDeleteStatus) {
		this.senderDeleteStatus = senderDeleteStatus;
	}

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message_text")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}