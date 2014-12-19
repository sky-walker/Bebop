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

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_feed")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Feed extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 662347568331765537L;
	private Integer id;
	private String optCode;
	private Users who;
	private short optType;											/**动态的类型**/
	private Users whose;
	
	private Posts posts;
	private Comments comments;
	private Concern concern;
	
	private Date optTime = new Date();
	private short isRemind;											/**动态是否属于通知类型**/
	private short readStatus = SystemConstant.FEED_STATUS_UNREAD;	
	
	
	
	public Feed() {
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
	@JoinColumn(name = "user_who_id", nullable = false)
	public Users getWho() {
		return this.who;
	}

	public void setWho(Users who) {
		this.who = who;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_whose_id", nullable = false)
	public Users getWhose() {
		return this.whose;
	}

	public void setWhose(Users whose) {
		this.whose = whose;
	}

	@Column(name = "opt_type", nullable = false)
	public short getOptType() {
		return this.optType;
	}

	public void setOptType(short optType) {
		this.optType = optType;
	}

	@Column(name = "opt_time", nullable = false, length = 0)
	public Date getOptTime() {
		return this.optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	@Column(name = "opt_code", nullable = false, length = 40)
	public String getOptCode() {
		return this.optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	@Column(name = "read_status")
	public short getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(short readStatus) {
		this.readStatus = readStatus;
	}
	
	@Column(name = "is_remind")
	public short getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(short isRemind) {
		this.isRemind = isRemind;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = true,columnDefinition="INT default 0")
	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id", nullable = true,columnDefinition="INT default 0")
	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concern_id", nullable = true,columnDefinition="INT default 0")
	public Concern getConcern() {
		return concern;
	}

	public void setConcern(Concern concern) {
		this.concern = concern;
	}
}