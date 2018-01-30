package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 材料实体
 * @author Administrator
 *
 */
@Entity
@Table(name="item")
public class ItemEntity extends BaseEntity {

	private String name;
	private long num;
	private String description;
	private String pictureIndex;/*图片唯一标识*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPictureIndex() {
		return pictureIndex;
	}
	public void setPictureIndex(String pictureIndex) {
		this.pictureIndex = pictureIndex;
	}
	
}
