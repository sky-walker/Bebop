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
import javax.persistence.Transient;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_concern")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Concern extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -31090746493148138L;
	private Integer id;
	private Users follow;
	private Users fans;
	private short isFriends = SystemConstant.CONCERN_NOT_FRIEND;
	private Date createTime = new Date();
	private Feed lastFeed;
	
	private Set<Feed> feeds = new HashSet<Feed>(0);
	
	public Concern() {
	}
	
	public Concern(Integer id, Users follow, Users fans, short isFriends,Date createTime) {
		this.id = id;
		this.follow = follow;
		this.fans = fans;
		this.isFriends = isFriends;
		this.createTime = createTime;
	}

	public Concern(Users follow, Users fans) {
		this.follow = follow;
		this.fans = fans;
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
	@JoinColumn(name = "follow_id", nullable = false)
	public Users getFollow() {
		return follow;
	}

	public void setFollow(Users follow) {
		this.follow = follow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fans_id", nullable = false)
	public Users getFans() {
		return this.fans;
	}

	public void setFans(Users fans) {
		this.fans = fans;
	}

	@Column(name = "is_friend",nullable=false)
	public short getIsFriends() {
		return isFriends;
	}

	public void setIsFriends(short isFriends) {
		this.isFriends = isFriends;
	}
	
	@Column(name = "create_time", nullable = false, length = 0)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "concern")
	public Set<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}
	
	@Transient
	public Feed getLastFeed() {
		return lastFeed;
	}

	public void setLastFeed(Feed lastFeed) {
		this.lastFeed = lastFeed;
	}


}