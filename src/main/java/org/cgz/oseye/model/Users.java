package org.cgz.oseye.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.cgz.oseye.common.SystemConstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "ose_users")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Users extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = -8500387458802933852L;
	private Integer id;
	/**用户名**/
	private String name;
	/**密码**/
	private String pwd;
	/**邮箱**/
	private String email;
	/**头像**/
	private String portraint = "http://bebop.qiniudn.com/images/noportraint.jpg";
	/**性别**/
	private Short gender;
	/**出生日期**/
	private Date birth = new Date();
	/**所在地 省份**/
	private String province;
	/**所在地 城市**/
	private String city;
	/**昵称**/
	private String nickname;
	/**个性签名**/
	private String signature = "这个人很懒,什么都没留下.";
	/**职位**/
	private String occupation;
	/**行业**/
	private String industry;
	/**工作年限**/
	private String workyear;
	/**用户是否被冻结**/
	private Short status = SystemConstant.USERS_FREEZING;
	/**用户在线状态**/
	private Short online = SystemConstant.USERS_OFFLINE;
	/**注册时间**/
	private Date regtime = new Date();
	/**此次登录时间**/
	private Date thisLoginTime = new Date();
	/**此次登录IP**/
	private String thisLoginIp;
	/**校验码**/
	private String activateCode;
	/**密码重置校验码**/
	private String resetPasswordKey;
	/**关注数**/
	private Integer followsNum = 0;
	/**粉丝数**/
	private Integer fansNum = 0;
	/**博客**/
	private Blogs blogs;
	/**用户登录后的SESSIONID**/
	private String sessionKey;
	/**用户的一些设置**/
	private UserSettings userSettings;
	
	private Set<Message_text> message_texts = new HashSet<Message_text>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<Feed> feedsForWhose = new HashSet<Feed>(0);
	private Set<Feed> feedsForWho = new HashSet<Feed>(0);
	private Set<Favorites> favoriteses = new HashSet<Favorites>(0);
	private Set<Visits> visitses = new HashSet<Visits>(0);
	private Set<Comments> commentses = new HashSet<Comments>(0);
    private Set<Favorite_tags> favorite_tags = new HashSet<Favorite_tags>(0);
	private Set<Post_categories> post_categories = new HashSet<Post_categories>();
	
	public static final String NAME = "name";
	
	public Users() {
	}

    public Users(String name, String nickname,String pwd, String email, Short gender, String province, String city) {
        this.name = name;
        this.nickname = nickname;
        this.pwd = pwd;
        this.email = email;
        this.gender = gender;
        this.province = province;
        this.city = city;
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

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "users")
	@PrimaryKeyJoinColumn
	public Blogs getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}
	
	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pwd", nullable = false, length = 40)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "email", nullable = false, length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "portraint", nullable = false, length = 120)
	public String getPortraint() {
		return this.portraint;
	}

	public void setPortraint(String portraint) {
		this.portraint = portraint;
	}

	@Column(name = "gender", nullable = false)
	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birth", length = 0)
	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Column(name = "province", length = 40)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", nullable = false, length = 40)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "nickname", nullable = false, length = 20)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "signature", nullable = false, length = 120)
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column(name = "occupation",length = 25)
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "industry",length = 20)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "workyear", length = 10)
	public String getWorkyear() {
		return workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}
	
	
	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "online", nullable = false)
	public Short getOnline() {
		return this.online;
	}

	public void setOnline(Short online) {
		this.online = online;
	}

	@Column(name = "regtime", nullable = false, length = 0)
	public Date getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	@Column(name = "this_login_time", length = 0)
	public Date getThisLoginTime() {
		return this.thisLoginTime;
	}

	public void setThisLoginTime(Date thisLoginTime) {
		this.thisLoginTime = thisLoginTime;
	}

	@Column(name = "this_login_ip", length = 60)
	public String getThisLoginIp() {
		return this.thisLoginIp;
	}

	public void setThisLoginIp(String thisLoginIp) {
		this.thisLoginIp = thisLoginIp;
	}

	@Column(name = "activate_code", length = 40)
	public String getActivateCode() {
		return this.activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}

	@Column(name = "reset_password_key", length = 40)
	public String getResetPasswordKey() {
		return this.resetPasswordKey;
	}

	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}

	@Column(name = "follows_num", nullable = false)
	public Integer getFollowsNum() {
		return this.followsNum;
	}

	public void setFollowsNum(Integer followsNum) {
		this.followsNum = followsNum;
	}

	@Column(name = "fans_num", nullable = false)
	public Integer getFansNum() {
		return this.fansNum;
	}

	public void setFansNum(Integer fansNum) {
		this.fansNum = fansNum;
	}

    @Column(name = "session_key", nullable = true,length = 60)
	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
	public Set<Message_text> getMessage_texts() {
		return this.message_texts;
	}

	public void setMessage_texts(Set<Message_text> message_texts) {
		this.message_texts = message_texts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Favorite_tags> getFavorite_tagses() {
		return this.favorite_tags;
	}

	public void setFavorite_tagses(Set<Favorite_tags> favorite_tags) {
		this.favorite_tags = favorite_tags;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "whose")
	public Set<Feed> getFeedsForWhose() {
		return this.feedsForWhose;
	}

	public void setFeedsForWhose(Set<Feed> feedsForWhose) {
		this.feedsForWhose = feedsForWhose;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "who")
	public Set<Feed> getFeedsForWho() {
		return this.feedsForWho;
	}

	public void setFeedsForWho(Set<Feed> feedsForWho) {
		this.feedsForWho = feedsForWho;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Favorites> getFavoriteses() {
		return this.favoriteses;
	}

	public void setFavoriteses(Set<Favorites> favoriteses) {
		this.favoriteses = favoriteses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "visitor")
	public Set<Visits> getVisitses() {
		return this.visitses;
	}

	public void setVisitses(Set<Visits> visitses) {
		this.visitses = visitses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Comments> getCommentses() {
		return this.commentses;
	}

	public void setCommentses(Set<Comments> commentses) {
		this.commentses = commentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Post_categories> getPost_categories() {
		return post_categories;
	}

	public void setPost_categories(Set<Post_categories> post_categories) {
		this.post_categories = post_categories;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	@PrimaryKeyJoinColumn
	public UserSettings getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(UserSettings userSettings) {
		this.userSettings = userSettings;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
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
		Users other = (Users) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		return true;
	}
}