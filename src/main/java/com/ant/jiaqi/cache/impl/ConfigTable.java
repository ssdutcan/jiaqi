package com.ant.jiaqi.cache.impl;

public class ConfigTable {
	/**��������*/
	private String id;
	
	/**�������Զ��ŷָ�*/
	private String primaryKey;
	
	/**�������ʵ����ȫ·��*/
	private String objectClazzName;
	
	/**������loaderʵ�����ȫ·��*/
	private String loaderClazzName;
	
	/**�����ʶ*/
	private String location;
	
	/**��������redis�е�����*/
	private String redisCacheName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectClazzName() {
		return objectClazzName;
	}

	public void setObjectClazzName(String objectClazzName) {
		this.objectClazzName = objectClazzName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getLoaderClazzName() {
		return loaderClazzName;
	}

	public void setLoaderClazzName(String loaderClazzName) {
		this.loaderClazzName = loaderClazzName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRedisCacheName() {
		return redisCacheName;
	}

	public void setRedisCacheName(String redisCacheName) {
		this.redisCacheName = redisCacheName;
	}
	
	
}
