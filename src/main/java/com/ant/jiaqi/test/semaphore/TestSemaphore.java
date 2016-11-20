package com.ant.jiaqi.test.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {
	public static void main(String args[]) {
		final Semaphore semaphore = new Semaphore(5, true);//��ʼpermits=5������FIFO�Ĺ�ƽ����
		
		//----------д��1----------
		/*for(int i = 0; i < 10; i++) {
			
			Runnable runnable = new Runnable(){
				@Override
				public void run() {
					try {
						semaphore.acquire();//��ȡһ��permit�����û�п��ã������̣߳�һֱ�ȴ�
						System.out.println("thread---" + Thread.currentThread().getName());
						Thread.sleep((long)(Math.random() * 10000));
						semaphore.release();//�ͷ�һ��permit
						System.out.println("availablePermits---" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread thread = new Thread(runnable);
			thread.start();
		}*/
		
		//----------д��2----------
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++) {
			
			Runnable runnable = new Runnable(){
				@Override
				public void run() {
					try {
						semaphore.acquire();//��ȡһ��permit�����û�п��ã������̣߳�һֱ�ȴ�
						System.out.println("thread---" + Thread.currentThread().getName());
						Thread.sleep((long)(Math.random() * 10000));
						semaphore.release();//�ͷ�һ��permit
						System.out.println("availablePermits---" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			exec.execute(runnable);//��runnable�������̳߳أ�����ִ��
		}
		exec.shutdown();//������ֹ�̵߳����У����ǲ������µ�runnable����
		
	}
}
