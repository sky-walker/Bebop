package org.cgz.oseye.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_blogs")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Blogs extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 1949570565050680679L;

	private Integer id;
	private Users users;
	private String name;
	private Integer postNum = 0;
	private Integer draftsNum = 0;
	private long viewsNum = (long) 0;
	private Set<Posts> postses = new HashSet<Posts>(0);
	private Set<Post_categories> post_categorieses = new HashSet<Post_categories>(0);
	private Set<Visits> visitses = new HashSet<Visits>(0);
	private Set<Drafts> draftses = new HashSet<Drafts>(0);
	private Set<Comments> commentses = new HashSet<Comments>(0);

	public Blogs() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "name", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "post_num", nullable = false)
	public Integer getPostNum() {
		return this.postNum;
	}

	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}

	@Column(name = "drafts_num", nullable = false)
	public Integer getDraftsNum() {
		return this.draftsNum;
	}

	public void setDraftsNum(Integer draftsNum) {
		this.draftsNum = draftsNum;
	}

	@Column(name = "views_num", nullable = false)
	public long getViewsNum() {
		return viewsNum;
	}

	public void setViewsNum(long viewsNum) {
		this.viewsNum = viewsNum;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogs")
	public Set<Posts> getPostses() {
		return this.postses;
	}

	public void setPostses(Set<Posts> postses) {
		this.postses = postses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogs")
	public Set<Post_categories> getPost_categorieses() {
		return this.post_categorieses;
	}

	public void setPost_categorieses(Set<Post_categories> post_categorieses) {
		this.post_categorieses = post_categorieses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogs")
	public Set<Visits> getVisitses() {
		return this.visitses;
	}

	public void setVisitses(Set<Visits> visitses) {
		this.visitses = visitses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogs")
	public Set<Drafts> getDraftses() {
		return this.draftses;
	}

	public void setDraftses(Set<Drafts> draftses) {
		this.draftses = draftses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogs")
	public Set<Comments> getCommentses() {
		return this.commentses;
	}

	public void setCommentses(Set<Comments> commentses) {
		this.commentses = commentses;
	}

}