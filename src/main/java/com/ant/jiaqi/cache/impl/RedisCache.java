package com.ant.jiaqi.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.cache.Cache;

public class RedisCache extends Cache{
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	
	private static final String DEFAULT_DELI = ":";
	
	//������������	ֵ����������redis�е�ǰ׺��		�磺Py_Rte_Tbl_Redis
	private Map<String, String> tblRedisMap;
	
	//������������	ֵ����������redis�е�hash��	�磺Py_Rte_Tbl_Redis001
	private Map<String, String> tblRedisVersionMap = new ConcurrentHashMap<String, String>();
	
	
	public void setTblRedisMap(Map<String, String> tblRedisMap) {
		this.tblRedisMap = tblRedisMap;
	}

	private String combinePrimaryKeyValue(String tableId, String[] primaryKeyValues) {
		StringBuilder sb = new StringBuilder();
		sb.append(primaryKeyValues[0]);
		for(int i = 1; i < primaryKeyValues.length; i ++) {
			sb.append(DEFAULT_DELI);
			sb.append(primaryKeyValues[i]);
		}
		return sb.toString();
	}

	@Override
	public void put(String tableId, long version, Object object) {
		
	}

	@Override
	public Object get(String tableId, long version, Object[] primaryKeyValues) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
