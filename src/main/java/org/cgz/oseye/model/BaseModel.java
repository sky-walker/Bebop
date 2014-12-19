package org.cgz.oseye.model;

import java.io.Serializable;

/** 
 * @author 陈广志 
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -4577108212096110315L;

	protected Object id;

	protected String className;
	
    /**
     * 返回对象类名称
     * @return
     */
	public String getClassName() {
		return this.getClass().getSimpleName();
	}

	public Object getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
