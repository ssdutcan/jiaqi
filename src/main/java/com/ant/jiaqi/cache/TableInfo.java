package com.ant.jiaqi.cache;

public class TableInfo {
	/**����*/
	private String[] primaryKeys;
	
	/**�������ʵ����ȫ·��*/
	private String objectClazz;
	
	/**�༶����*/
	private String[] caches;

	/**�汾��*/
	private volatile long version;
	
	public String[] getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getObjectClazz() {
		return objectClazz;
	}

	public void setObjectClazz(String objectClazz) {
		this.objectClazz = objectClazz;
	}

	public String[] getCaches() {
		return caches;
	}

	public void setCaches(String[] caches) {
		this.caches = caches;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
