package com.ant.jiaqi.cache;

import java.util.List;

public interface DBLoader {
	/**
	 * �����ݿ����ָ���汾�ź�����ֵ�ļ�¼
	 * 
	 * @param primaryKeyValues ����ֵ����
	 * @return �ҵ��ļ�¼���󣬷���null
	 */
	public Object load(Object[] primaryKeyValues);
	
	/**
	 * �����ݿ����ָ���汾�ŵĶ�����¼
	 * @param startRow ��ʼ��
	 * @param offset ƫ����
	 * @return
	 */
	public List<?> load(int startRow, int offset);
}
