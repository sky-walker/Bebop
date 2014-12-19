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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ose_favorites")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Favorites extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -4843242306510915191L;
	/**id**/
	private Integer id;
	/**收藏的标题**/
	private String title;
	/**收藏的URL**/
	private String url;
	/**收藏所属用户**/
	private Users users;
	/**收藏的类型[默认,文章...]**/
	private short type = SystemConstant.FAVORITE_TYPE_DEFAULT;
	/**收藏是内链还是外链**/
	private short isOuterLink;
	/**收藏的文章**/
	private Posts posts; 
	/**收藏的标签**/
	private Set<Favorite_tags> favorite_tags = new HashSet<Favorite_tags>(0);
	
	public Favorites() {
	}
	
	public Favorites(Integer id, String title, String url, short isOuterLink,Set<Favorite_tags> favorite_tags) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.isOuterLink = isOuterLink;
		this.favorite_tags = favorite_tags;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "favorites")
	@OrderBy("id DESC")
	public Set<Favorite_tags> getFavorite_tags() {
		return favorite_tags;
	}
	public void setFavorite_tags(Set<Favorite_tags> favorite_tags) {
		this.favorite_tags = favorite_tags;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "url", nullable = false, length = 300)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "type",nullable=true)
	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
	@Transient
	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	@Column(name = "is_outer_link",nullable=false)
	public short getIsOuterLink() {
		return isOuterLink;
	}

	public void setIsOuterLink(short isOuterLink) {
		this.isOuterLink = isOuterLink;
	}
}