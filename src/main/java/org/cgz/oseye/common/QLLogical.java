package org.cgz.oseye.common;

/** 
 * @author 陈广志 
 * @Description: 逻辑运算符
 */
public enum QLLogical {
	
	NOT(" NOT "),AND(" AND "),OR(" OR ");
	
	private QLLogical(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
