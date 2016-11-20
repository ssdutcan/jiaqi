package com.ant.jiaqi.test.thread;

import java.math.BigDecimal;

public class TestThread {
	public static void main(String args[]) {
		Account account = new Account("yagami", new BigDecimal(100));
		Runnable runnable = new AccountRunnable(account);
		
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		System.out.println(thread1.getName() + "---" + thread1.getState());
		System.out.println(thread2.getName() + "---" + thread2.getState());
		System.out.println(thread3.getName() + "---" + thread3.getState());
		
		thread1.start();
		thread2.start();
		thread3.start();
		System.out.println(thread1.getName() + "---" + thread1.getState());
		
		for(int i = 0; i < 10000; i ++){}
		System.out.println(thread2.getName() + "---" + thread2.getState());
		System.out.println(thread3.getName() + "---" + thread3.getState());
		
		
		System.out.println(account.toString());
		
		
		/*Runnable runnable = new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(5000);//sleep�����Ǿ�̬�ģ�Ŀ����ֻ��sleep��ǰ�̡߳����sleep��ʵ����������ǰ�߳̿���sleep�����̣߳�������ܶ��߳����⣬���磺����
					String str = "";
					str.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread thread = new Thread(runnable);
		System.out.println(thread.getName() + "---" + thread.getState());
		thread.start();
		System.out.println(thread.getName() + "---" + thread.getState());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(thread.getName() + "---" + thread.getState());*/
	}
	
}
