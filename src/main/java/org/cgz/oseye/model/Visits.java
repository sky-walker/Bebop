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
@Table(name = "ose_visits")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Visits extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 7557686682818868134L;
	private Integer id;
	private Users visitor;
	private Blogs blogs;
	private String visiturl = "/";
	private String ip;
	private Date visittime = new Date();
	/**受访用户收否删除访客的足迹**/
	private short userDel = SystemConstant.VISITS_KEEP_STATUS;
	
	public Visits() {
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
	@JoinColumn(name = "visitor", nullable = false)
	public Users getVisitor() {
		return this.visitor;
	}

	public void setVisitor(Users visitor) {
		this.visitor = visitor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_id", nullable = false)
	public Blogs getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}


	@Column(name = "ip", length = 15)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "visittime", nullable = false, length = 0)
	public Date getVisittime() {
		return this.visittime;
	}

	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	
	@Column(name = "visiturl", nullable = false, length = 80)
	public String getVisiturl() {
		return this.visiturl;
	}

	public void setVisiturl(String visiturl) {
		this.visiturl = visiturl;
	}
	
	@Column(name = "userDel", nullable = false)
	public short getUserDel() {
		return userDel;
	}

	public void setUserDel(short userDel) {
		this.userDel = userDel;
	}
}