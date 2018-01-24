package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户实体
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name="customer")
public class CustomerEntity extends BaseEntity {
	
	private String name;
	private String tel;
	private String pictureIndex;
	public String getPictureIndex() {
		return pictureIndex;
	}
	public void setPictureIndex(String pictureIndex) {
		this.pictureIndex = pictureIndex;
	}
	private String msg; /*备注*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	

}
