package org.cgz.oseye.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_comments")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Comments extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 7178246739311166689L;
	private Integer id;
	private Blogs blogs;//被评论的博客
	private Users users;//评论人
	private Posts posts;//被评论的文章
	private String content;
	private Comments parent;
	private Set<Comments> childs = new HashSet<Comments>(0);
	private Date createTime = new Date();
	private String ip;
	private short visible = SystemConstant.COMMENTS_VISIBLE;
	private short delStatus = SystemConstant.COMMENTS_NOT_DEL;
	
	private Set<Feed> feeds = new HashSet<Feed>(0);
	
	public Comments() {
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
	@JoinColumn(name = "blog_id", nullable = false)
	public Blogs getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	public Posts getPosts() {
		return this.posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	@Lob
	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_time", nullable = false, length = 0)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ip", length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)	
	@JoinColumn(name="parent_id",nullable=true)
	public Comments getParent() {
		return parent;
	}

	public void setParent(Comments parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent")
	//@OneToMany(cascade={CascadeType.REFRESH},mappedBy="parent")
	public Set<Comments> getChilds() {
		return childs;
	}

	public void setChilds(Set<Comments> childs) {
		this.childs = childs;
	}

	public void setVisible(short visible) {
		this.visible = visible;
	}
	
	@Column(name = "visible",nullable=false)
	public short getVisible() {
		return visible;
	}

	@Column(name = "del_status",nullable=false)
	public short getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(short delStatus) {
		this.delStatus = delStatus;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comments")
	public Set<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}
}