package org.cgz.oseye.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_drafts")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Drafts extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -248967177210104270L;
	private Integer id;
	private Blogs blogs;
	private String title;
	private String content;
	private Date createTime = new Date();
	private String musicUrl;
	private String tags;
	private short asTop = SystemConstant.POSTS_NOT_AS_TOP;
	private short visible = SystemConstant.POSTS_VISIBLE;
	private short canComment = SystemConstant.POSTS_CAN_COMMENT;
	private Post_categories post_categories;
	private short postType = SystemConstant.POSTS_ORIGINAL;
	private String reLink;

	public static final String ID = "id";
	public static final String POST_CATEGORIES = "post_categories";
	public static final String BLOGS = "blogs";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String CREATETIME = "createTime";
	public static final String POSTLINK = "postLink";
	public static final String MUSICURL = "musicUrl";
	public static final String TAGS = "tags";
	public static final String ASTOP = "asTop";
	public static final String VISIBLE = "visible";
	public static final String CANCOMMENT = "canComment";
	public static final String RELINK = "reLink";
	
	
	public Drafts() {
	}
	
	
	public Drafts(Post_categories postCategories, Blogs blogs, String title, String content,short visible, short canComment,short postType,String reLink,short asTop) {
		this.blogs = blogs;
		this.title = title;
		this.content = content;
		this.visible = visible;
		this.canComment = canComment;
		this.post_categories = postCategories;
		this.postType = postType;
		this.reLink = reLink;
		this.asTop = asTop;
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
	@JoinColumn(name = "blog_id",nullable = false)
	public Blogs getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_category_id", nullable = false)
	public Post_categories getPost_categories() {
		return this.post_categories;
	}

	public void setPost_categories(Post_categories post_categories) {
		this.post_categories = post_categories;
	}
	
	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "content",nullable = false)
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

	@Column(name = "music_url", length = 250)
	public String getMusicUrl() {
		return this.musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	@Column(name = "tags", length = 100)
	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Column(name = "as_top",nullable=false)
	public Short getAsTop() {
		return this.asTop;
	}

	public void setAsTop(Short asTop) {
		if(null!=asTop) {
			this.asTop = asTop;
		}
	}

	@Column(name = "visible",nullable=false)
	public Short getVisible() {
		return this.visible;
	}

	public void setVisible(Short visible) {
		this.visible = visible;
	}

	@Column(name = "can_comment",nullable=false)
	public Short getCanComment() {
		return this.canComment;
	}

	public void setCanComment(Short canComment) {
		this.canComment = canComment;
	}
	
	public void setPostType(short postType) {
		this.postType = postType;
	}
	
	@Column(name = "post_type",nullable=false)
	public short getPostType() {
		return postType;
	}

	public void setReLink(String reLink) {
		this.reLink = reLink;
	}

	@Column(name = "relink")
	public String getReLink() {
		return reLink;
	}
}