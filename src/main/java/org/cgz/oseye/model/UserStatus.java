package org.cgz.oseye.model;

import java.io.Serializable;
import java.util.Date;
import org.cgz.oseye.common.SystemConstant;

public class UserStatus implements Serializable {

	private static final long serialVersionUID = 6886987741844279003L;
	private Users users;
	private String visiturl = "/";
	private Date visittime = new Date();
	private String visittip;	
	private short status = SystemConstant.VISITS_STATUS_ONLINE;
	
	
	public UserStatus() {
	}
	
	public UserStatus(Users users) {
		this.users = users;
	}
	
	public Users getUsers() {
		return users;
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public String getVisiturl() {
		return this.visiturl;
	}

	public void setVisiturl(String visiturl) {
		this.visiturl = visiturl;
	}

	public Date getVisittime() {
		return this.visittime;
	}

	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	
	public String getVisittip() {
		return visittip;
	}
	public void setVisittip(String visittip) {
		this.visittip = visittip;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + status;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		result = prime * result
				+ ((visittime == null) ? 0 : visittime.hashCode());
		result = prime * result
				+ ((visittip == null) ? 0 : visittip.hashCode());
		result = prime * result
				+ ((visiturl == null) ? 0 : visiturl.hashCode());
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
		UserStatus other = (UserStatus) obj;
		if (status != other.status)
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		if (visittime == null) {
			if (other.visittime != null)
				return false;
		} else if (!visittime.equals(other.visittime))
			return false;
		if (visittip == null) {
			if (other.visittip != null)
				return false;
		} else if (!visittip.equals(other.visittip))
			return false;
		if (visiturl == null) {
			if (other.visiturl != null)
				return false;
		} else if (!visiturl.equals(other.visiturl))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserStatus [visiturl=" + visiturl + ", visittime=" + visittime
				+ ", visittip=" + visittip + ", status=" + status + "]";
	}
}
