package org.cgz.oseye.model;

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

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ose_post_categories")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Post_categories extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -6136648287135105461L;
	private Integer id;
	private Blogs blogs;
	private String name;
	private Integer postNum = 0;
	private Integer sort = 0;
	private Short visible = SystemConstant.POST_CATEGORIES_VISIBLE;
	private Users users;
	private Set<Posts> postses = new HashSet<Posts>(0);
	
	public static final String ID = "id";
	public static final String BLOGS = "blogs";
	public static final String NAME = "name";
	public static final String POSTNUM = "postNum";
	public static final String SORT = "sort";
	public static final String VISIBLE = "visible";
	public static final String USERS = "users";
	
	public Post_categories() {
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

	@Column(name = "name", nullable = false)
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

	@Column(name = "sort", nullable = false)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "visible", nullable = false)
	public Short getVisible() {
		return this.visible;
	}

	public void setVisible(Short visible) {
		this.visible = visible;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post_categories")
	public Set<Posts> getPostses() {
		return this.postses;
	}

	public void setPostses(Set<Posts> postses) {
		this.postses = postses;
	}


	@Override
	public String toString() {
		return "Post_categories [id=" + id + ", name=" + name + ", postNum="
				+ postNum + ", sort=" + sort + ", visible=" + visible + "]";
	}
}