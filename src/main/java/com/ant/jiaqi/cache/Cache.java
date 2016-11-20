package com.ant.jiaqi.cache;


public abstract class Cache {
	
	/** ���ͣ�public��private*/
	protected String type;
	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * ��ָ����������Ӳ�����¼
	 * @param tableId	�������ʶ
	 * @param version	�汾��
	 * @param object	������¼
	 */
	public abstract void put(String tableId, long version, Object object);
	
	/**
	 * ʹ����������ȡָ��������Ĳ�����¼
	 * @param tableId
	 * @param version
	 * @param primaryKeyValues
	 * @return
	 */
	public abstract Object get(String tableId, long version, Object[] primaryKeyValues);
}
