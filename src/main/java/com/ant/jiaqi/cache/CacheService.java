package com.ant.jiaqi.cache;

public interface CacheService {
	/**
	 * ������ѯָ��������ļ�¼
	 * @param tableId
	 * @param primaryKeyValues
	 * @return
	 */
	public Object findByPrimaryKey(String tableId, Object... primaryKeyValues);
}
