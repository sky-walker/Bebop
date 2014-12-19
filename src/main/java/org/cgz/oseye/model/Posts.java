package org.cgz.oseye.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ose_posts")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size=20)
public class Posts extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -4438355122764612571L;
	private Integer id;
	private Post_categories post_categories;
	private Blogs blogs;
	private String title;
	private String content;
	private Date createTime = new Date();
	private Integer viewsNum = 0;
	private Integer commentsNum = 0;
	private String postLink;
	private String musicUrl;
	private String tags;
	private short asTop = SystemConstant.POSTS_NOT_AS_TOP;
	private short visible = SystemConstant.POSTS_VISIBLE;
	private short canComment = SystemConstant.POSTS_CAN_COMMENT;
	private short asTrash = SystemConstant.POSTS_NOT_TRASH;
	private short postType = SystemConstant.POSTS_ORIGINAL;
	private String reLink;
	
	private Set<Comments> commentses = new HashSet<Comments>(0);
	private Set<Feed> feeds = new HashSet<Feed>(0);
	
	public static final String ID = "id";
	public static final String POST_CATEGORIES = "post_categories";
	public static final String BLOGS = "blogs";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String CREATETIME = "createTime";
	public static final String VIEWSNUM = "viewsNum";
	public static final String COMMENTSNUM = "commentsNum";
	public static final String POSTLINK = "postLink";
	public static final String MUSICURL = "musicUrl";
	public static final String TAGS = "tags";
	public static final String ASTOP = "asTop";
	public static final String VISIBLE = "visible";
	public static final String CANCOMMENT = "canComment";
	public static final String ASTRASH = "asTrash";
	
	
	public Posts() {
	}
	
	public Posts(Integer id) {
		this.id = id;
	}
	
	public Posts(Post_categories postCategories, Blogs blogs, String title,
				 String content,short visible, short canComment, short postType,String reLink,short asTop) {
		post_categories = postCategories;
		this.blogs = blogs;
		this.title = title;
		this.content = content;
		this.visible = visible;
		this.canComment = canComment;
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
	@JoinColumn(name = "post_category_id", nullable = false)
	public Post_categories getPost_categories() {
		return this.post_categories;
	}

	public void setPost_categories(Post_categories post_categories) {
		this.post_categories = post_categories;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_id", nullable = false)
	public Blogs getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
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

	@Column(name = "views_num", nullable = false)
	public Integer getViewsNum() {
		return this.viewsNum;
	}

	public void setViewsNum(Integer viewsNum) {
		this.viewsNum = viewsNum;
	}

	@Column(name = "comments_num", nullable = false)
	public Integer getCommentsNum() {
		return this.commentsNum;
	}

	public void setCommentsNum(Integer commentsNum) {
		this.commentsNum = commentsNum;
	}

	@Column(name = "post_link", length = 200)
	public String getPostLink() {
		return this.postLink;
	}

	public void setPostLink(String postLink) {
		this.postLink = postLink;
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
	public short getVisible() {
		return this.visible;
	}

	public void setVisible(short visible) {
		this.visible = visible;
	}

	@Column(name = "can_comment",nullable=false)
	public short getCanComment() {
		return this.canComment;
	}

	public void setCanComment(short canComment) {
		this.canComment = canComment;
	}

	@Column(name = "as_trash")
	public short getAsTrash() {
		return this.asTrash;
	}

	public void setAsTrash(short asTrash) {
		this.asTrash = asTrash;
	}
	
	public void setPostType(short postType) {
		this.postType = postType;
	}
	
	@Column(name = "post_type",nullable=false)
	public short getPostType() {
		return postType;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posts",cascade={CascadeType.REMOVE})
	@OrderBy("id DESC")
	public Set<Comments> getCommentses() {
		return this.commentses;
	}

	public void setCommentses(Set<Comments> commentses) {
		this.commentses = commentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posts")
	public Set<Feed> getFeeds() {
		return feeds;
	}
	
	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}
	
	public void setReLink(String reLink) {
		this.reLink = reLink;
	}

	@Column(name = "relink")
	public String getReLink() {
		return reLink;
	}


	@Override
	public String toString() {
		return "Posts [content=" + content + ", createTime=" + createTime
				+ ", id=" + id + ", title=" + title + "]";
	}
}