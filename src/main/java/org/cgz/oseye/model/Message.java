package org.cgz.oseye.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ose_message")
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = -5742657710685784791L;
	private Integer id;
	private Message_text message_text;
	private Users receiver;
	private Short readStatus;
	private Date readtime;
	private Short receiverDeleteStatus;

	public Message() {
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
	@JoinColumn(name = "message_id", nullable = false)
	public Message_text getMessage_text() {
		return this.message_text;
	}

	public void setMessage_text(Message_text message_text) {
		this.message_text = message_text;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false)
	public Users getReceiver() {
		return this.receiver;
	}

	public void setReceiver(Users receiver) {
		this.receiver = receiver;
	}

	@Column(name = "read_status", nullable = false)
	public Short getReadStatus() {
		return this.readStatus;
	}

	public void setReadStatus(Short readStatus) {
		this.readStatus = readStatus;
	}

	@Column(name = "readtime", nullable = false, length = 0)
	public Date getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	@Column(name = "receiver_delete_status", nullable = false)
	public Short getReceiverDeleteStatus() {
		return this.receiverDeleteStatus;
	}

	public void setReceiverDeleteStatus(Short receiverDeleteStatus) {
		this.receiverDeleteStatus = receiverDeleteStatus;
	}

}