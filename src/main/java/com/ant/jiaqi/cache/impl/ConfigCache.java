package com.ant.jiaqi.cache.impl;

public class ConfigCache {
	/** �����ʶ��local��redis��db*/
	private String id;
	
	/** ���ͣ�public��private*/
	private String type;
	
	/** ʵ����ȫ·��*/
	private String clazzName;
	
	/** ������Ϣ*/
	private String args;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
	
}
