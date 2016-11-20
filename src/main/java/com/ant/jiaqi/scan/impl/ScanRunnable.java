package com.ant.jiaqi.scan.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.scan.ScanDos;
import com.ant.jiaqi.scan.ScanHandler;

public class ScanRunnable implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(ScanRunnable.class);
	
	//���ݿ����Dos
	private ScanDos scanDos;
	//ɨ�账��
	private ScanHandler scanHandler;
	//һ����¼	
	private Object record;
	
	public ScanRunnable(ScanDos scanDos, ScanHandler scanHandler, Object record) {
		this.scanDos = scanDos;
		this.scanHandler = scanHandler;
		this.record = record;
	}
	
	@Override
	public void run() {
		boolean result = scanDos.lockRecord(record); 
		
		if(!result) {
			logger.debug("����ʧ��");
			return;
		}
		
		logger.debug("�����ɹ�");
		scanHandler.doHandler(record);
		
		scanDos.unlockRecord(record);
	}
	
}
