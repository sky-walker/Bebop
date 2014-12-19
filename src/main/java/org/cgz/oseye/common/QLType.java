package org.cgz.oseye.common;

/** 
 * @author 陈广志 
 * @Description: 
 */
public enum QLType {
	DEL("DELETE "),
	SELECT("SELECT "),
	SELECTALL("SELECT o "),
	COUNT("SELECT COUNT(o) "),
	UPDATE("UPDATE "),
	UPDATEALL("UPDATE o ");
	
	private QLType(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
}
