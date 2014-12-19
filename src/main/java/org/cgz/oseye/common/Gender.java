package org.cgz.oseye.common;

/**'
 * 性别
 * @author Administrator
 *
 */
public enum Gender {

	Other("保密",(short)0),Male("男",(short)1),Female("女",(short)2);
	
	private String name;
	
	private short value;
	
	private Gender(String name, short value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public static Gender getGender(short value) {
		if (0 == value){
			return Other;
		}else if (1 == value){
			return Male;
		}else{
			return Female;
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
