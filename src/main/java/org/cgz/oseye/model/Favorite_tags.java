package org.cgz.oseye.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "ose_favorite_tags")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Favorite_tags extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 6521817847041976268L;
	/**uuid生成主键**/
	private String id;
	/**标签名**/
	private String name;
	/**标签所属用户**/
	private Users users;
	/**标签所属收藏**/
	private Favorites favorites;
	/**标签下收藏的个数(Transient)**/
	private Integer favorite_num;
	
	public Favorite_tags() {
	}
	
	public Favorite_tags(String name, Users users, Favorites favorites) {
		this.name = name;
		this.users = users;
		this.favorites = favorites;
	}
	
	public Favorite_tags(Integer favorite_num,String name) {
		this.name = name;
		this.favorite_num = favorite_num;
	}

	public Favorite_tags(String name, Users users) {
		this.name = name;
		this.users = users;
	}

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(name = "id", unique = true, nullable = false,length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "name", nullable = false, length = 15)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "favorites_id", nullable = false)
	public Favorites getFavorites() {
		return favorites;
	}

	public void setFavorites(Favorites favorites) {
		this.favorites = favorites;
	}

	@Transient
	public Integer getFavorite_num() {
		return favorite_num;
	}

	public void setFavorite_num(Integer favorite_num) {
		this.favorite_num = favorite_num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((favorites == null) ? 0 : favorites.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Favorite_tags other = (Favorite_tags) obj;
		if (favorites == null) {
			if (other.favorites != null)
				return false;
		} else if (!favorites.equals(other.favorites))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
}