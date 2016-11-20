package com.ant.jiaqi.sequence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ant.jiaqi.mybatis.dao.SequenceMapper;
import com.ant.jiaqi.mybatis.model.Sequence;

public class SequenceImpl implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(SequenceImpl.class);
	
	private static final String MIN_NUM = "minNum";
	private static final String MAX_NUM = "maxNum";
	private static final String REGION_SIZE = "regionSize";
	
	@Autowired
	private SequenceMapper sequenceMapper;
	
	private Map<String, Map<String, String>> sequenceMap;
	
	public void setSequenceMap(Map<String, Map<String, String>> sequenceMap) {
		this.sequenceMap = sequenceMap;
	}
	
	private long getMinNum(String key) {
		Map<String, String> result = this.sequenceMap.get(key);
		if(result != null) {
			return Long.valueOf(result.get(MIN_NUM));
		}
		return 0L;
	}
	
	private long getMaxNum(String key) {
		Map<String, String> result = this.sequenceMap.get(key);
		if(result != null) {
			return Long.valueOf(result.get(MAX_NUM));
		}
		return 0L;
	}
	
	private long getRegionSize(String key) {
		Map<String, String> result = this.sequenceMap.get(key);
		if(result != null) {
			return Long.valueOf(result.get(REGION_SIZE));
		}
		return 0L;
	}
	
	private Region nextRegion(String key) {
		long curSeqValue = 0L;
		long newSeqValue = 0L;
		long start = 0L;
		long end = 0l;
		long minNum = getMinNum(key);
		long maxNum = getMaxNum(key);
		long regionSize = getRegionSize(key);
		
		int sqlResult = 0;
		while (sqlResult == 0) {
			Sequence sequence = sequenceMapper.selectByPrimaryKey(key);
			curSeqValue = sequence.getSeqValue();
			logger.debug("��ǰseqKey: " + key + " seqValue: " + curSeqValue);
			
			
			if(curSeqValue < minNum || curSeqValue > maxNum) {
				logger.error("��ǰ���Խ�磬����Ϊ��Сֵ: " + minNum);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("newSeqValue", minNum);
				map.put("seqKey", key);
				map.put("curSeqValue", curSeqValue);
				sequenceMapper.updateByMap(map);
				continue;
			}
			
			start = curSeqValue;
			newSeqValue = curSeqValue + regionSize;
			if(newSeqValue > maxNum) {
				logger.error("����ų������ֵ������Ϊ��Сֵ: " + minNum);
				newSeqValue = minNum;
				end = maxNum;
			} else {
				end = newSeqValue - 1;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newSeqValue", newSeqValue);
			map.put("seqKey", key);
			map.put("curSeqValue", curSeqValue);
			sqlResult = sequenceMapper.updateByMap(map);
		}
		
		return new Region(start, end);
	}
	
	private Distributor distributor;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		distributor = new Distributor("RMT_AR_ID");
	}
	
	public long getSequence(String key) {
		return 0L;
	} 
	
	
	private class Distributor {
		String key;
		Semaphore semaphore;
		ReentrantLock pagedownLock;
		volatile DistributeUnit currentDistributeUnit;
		
		public Distributor(String key) {
			this.key = key;
			this.semaphore = new Semaphore(0);
			this.pagedownLock = new ReentrantLock();
			this.init();
		}
		
		private void init() {
			Region currentRegion = nextRegion(key);
			this.currentDistributeUnit = new DistributeUnit(currentRegion);
			
			Region nextRegion = nextRegion(key);
			this.currentDistributeUnit.nextDistributeUnit = new DistributeUnit(nextRegion);
			
			this.semaphore.release((int)(currentRegion.getSize() + nextRegion.getSize()));
			logger.debug("�ͷ��ź�������ǰ����: " + this.currentDistributeUnit.region + "��һ������: " + this.currentDistributeUnit.nextDistributeUnit.region);
		}
		
		private void pageDown() {
			Region nextRegion = nextRegion(key);
			this.currentDistributeUnit.nextDistributeUnit.nextDistributeUnit = new DistributeUnit(nextRegion);
			this.currentDistributeUnit = this.currentDistributeUnit.nextDistributeUnit;
			this.semaphore.release((int)nextRegion.getSize());
		}
		
		public long nextNumber() {
			boolean isPermitted = false;
			try {
				isPermitted = this.semaphore.tryAcquire(50, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!isPermitted) {
				logger.error("δ�����ɣ��쳣����");
				throw new RuntimeException("��ȡ����쳣");
			}
			
			DistributeUnit holder = this.currentDistributeUnit;
			long number = holder.counter.getAndIncrement();
			
			if(number <= holder.region.getEnd()) {
				return number;
			}
			
			logger.debug("��ǰ��������Ѿ��þ������߳̾���ִ�з�ҳ");
			boolean isLocked = this.pagedownLock.tryLock();
			if(isLocked) {
				if(holder.nextDistributeUnit.nextDistributeUnit == null) {
					logger.debug("��ҳ��δִ�У���ִ�з�ҳ����");
					logger.debug("��ҳǰ������1" + holder.nextDistributeUnit.region);
					
					try{
						this.pageDown();
					} catch(Throwable t) {
						logger.error("��ҳʧ��", t);
						throw new RuntimeException("��ҳʧ��");
					} finally {
						this.pagedownLock.unlock();
						this.semaphore.release();
					}
					
					logger.debug("��ҳ������2" + holder.nextDistributeUnit.nextDistributeUnit.region);
					return this.nextNumber();
				}
			} else {
				logger.debug("δ�����������̣߳���holder.next�������");
				number = holder.nextDistributeUnit.counter.getAndDecrement();
				if(number <= holder.nextDistributeUnit.region.getEnd()) {
					return number;
				}
			}
			
			return 0L;
			
		}
		
	}
	
	private static class DistributeUnit {
		Region region;
		AtomicLong counter;
		volatile DistributeUnit nextDistributeUnit;
		
		public DistributeUnit(Region region) {
			this.region = region;
			this.counter = new AtomicLong(this.region.getStart());
		}
	}

}
