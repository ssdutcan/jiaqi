package com.ant.jiaqi.cache;

public interface PrimaryKeyAnalyser {
	/**
	 * �Ӽ�¼������ȡ����������ֵ���ֱ�ת��Ϊ�ַ�����Ȼ����������ʽ����
	 * @param tableId	������
	 * @param object	��¼����
	 * @return			�ַ�����ʽ����������
	 */
	public String[] analyse(String tableId, Object object);
	
	/**
	 * ��Object��ʽ����������ת��Ϊ�ַ�����ʽ����������
	 * @param tableId
	 * @param primaryKeyValues
	 * @return
	 */
	public String[] analyse(String tableId, Object[] primaryKeyValues);
}
