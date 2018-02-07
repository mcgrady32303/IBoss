package com.app.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	/**状态有效*/
	public static final int STATUS_VALID = 1;
	/**状态无效*/
	public static final int STATUS_NO_VALID = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	//0:无效 1:有效
	protected int status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
