package com.ant.jiaqi.cache.impl;

import java.util.List;

public class Config {
	/**��������*/
	private List<ConfigCache> configCaches;
	
	/**����������*/
	private List<ConfigTable> configTables;

	public List<ConfigCache> getConfigCaches() {
		return configCaches;
	}

	public void setConfigCaches(List<ConfigCache> configCaches) {
		this.configCaches = configCaches;
	}

	public List<ConfigTable> getConfigTables() {
		return configTables;
	}

	public void setConfigTables(List<ConfigTable> configTables) {
		this.configTables = configTables;
	}

}
