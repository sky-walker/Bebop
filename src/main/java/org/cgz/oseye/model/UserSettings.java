package org.cgz.oseye.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "ose_user_settings")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserSettings extends BaseModel{

	private static final long serialVersionUID = -4358771269683515446L;

	private Integer id;
	
	private Users user;
	
	private short privacy_blog_add = SystemConstant.PRIVACY_BLOG_ADD_SHOW;
	
	private short privacy_comment_add = SystemConstant.PRIVACY_COMMENT_SHOW;
	
	private short privacy_comment_reply = SystemConstant.PRIVACY_COMMENT_REPLY_SHOW;
	
	private short privacy_concern_add = SystemConstant.PRIVACY_CONCERN_ADD_SHOW;
	
	private short privacy_user_status = SystemConstant.PRIVACY_USER_STATUS_SHOW;

	private short privacy_blog_post_favorite = SystemConstant.PRIVACY_BLOG_POST_FAVORITE_SHOW;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Column(name = "privacy_blog_add", nullable = false)
	public short getPrivacy_blog_add() {
		return privacy_blog_add;
	}

	public void setPrivacy_blog_add(short privacy_blog_add) {
		this.privacy_blog_add = privacy_blog_add;
	}

	@Column(name = "privacy_comment_add", nullable = false)
	public short getPrivacy_comment_add() {
		return privacy_comment_add;
	}

	public void setPrivacy_comment_add(short privacy_comment_add) {
		this.privacy_comment_add = privacy_comment_add;
	}

	@Column(name = "privacy_comment_reply", nullable = false)
	public short getPrivacy_comment_reply() {
		return privacy_comment_reply;
	}

	public void setPrivacy_comment_reply(short privacy_comment_reply) {
		this.privacy_comment_reply = privacy_comment_reply;
	}

	@Column(name = "privacy_concern_add", nullable = false)
	public short getPrivacy_concern_add() {
		return privacy_concern_add;
	}

	public void setPrivacy_concern_add(short privacy_concern_add) {
		this.privacy_concern_add = privacy_concern_add;
	}

	@Column(name = "privacy_user_status", nullable = false)
	public short getPrivacy_user_status() {
		return privacy_user_status;
	}

	public void setPrivacy_user_status(short privacy_user_status) {
		this.privacy_user_status = privacy_user_status;
	}

	@Column(name = "privacy_blog_post_favorite", nullable = false)
	public short getPrivacy_blog_post_favorite() {
		return privacy_blog_post_favorite;
	}

	public void setPrivacy_blog_post_favorite(short privacy_blog_post_favorite) {
		this.privacy_blog_post_favorite = privacy_blog_post_favorite;
	}
}
