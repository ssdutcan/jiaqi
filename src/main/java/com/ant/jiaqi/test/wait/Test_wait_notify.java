package com.ant.jiaqi.test.wait;

import java.util.LinkedList;
import java.util.List;

public class Test_wait_notify {
	private List<String> list = new LinkedList<String>();
	
	public String removeElement() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---��ʼ����");
		
		synchronized(list) {
			while(list.isEmpty()) {
				System.out.println(Thread.currentThread().getName() + "---listΪ�գ������̣߳��ͷ���");
				Thread.sleep(1000);
				list.wait();
				System.out.println(Thread.currentThread().getName() + "---���յ���notify������wait");
			}
			String element = (String) list.remove(0);
			System.out.println(Thread.currentThread().getName() + "---������Ԫ��: " + element);
			return element;
		}
	}
	
	public void addElement() {
		System.out.println(Thread.currentThread().getName() + "---��ʼ����");
		
		synchronized(list) {
			System.out.println(Thread.currentThread().getName() + "---��Ԫ��: product1");
			list.add("product1");
			
			/*System.out.println(Thread.currentThread().getName() + "---��Ԫ��: product1��product2");
			list.add("product1");
			list.add("product2");*/
			
			/*System.out.println(Thread.currentThread().getName() + "---֪ͨĳ�������߳�");
			list.notify();*/
			
			System.out.println(Thread.currentThread().getName() + "---֪ͨ���������߳�");
			list.notifyAll();
		}
		
		System.out.println(Thread.currentThread().getName() + "---��������");
	}
	
	public static void main(String args[]) {
		final Test_wait_notify productList = new Test_wait_notify();
		
		Runnable consumerRun = new Runnable(){
			@Override
			public void run() {
				try {
					productList.removeElement();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable producerRun = new Runnable(){
			@Override
			public void run() {
				productList.addElement();
			}
		};
		
		Thread consumer_thread1 = new Thread(consumerRun, "consumer1");
		Thread consumer_thread2 = new Thread(consumerRun, "consumer2");
		consumer_thread1.start();
		consumer_thread2.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("consumer_thread1��ǰ״̬��" + consumer_thread1.getState());
		System.out.println("consumer_thread2��ǰ״̬��" + consumer_thread2.getState());
		
		Thread producer_thread1 = new Thread(producerRun, "producer1");
		producer_thread1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("consumer_thread1��ǰ״̬��" + consumer_thread1.getState());
		System.out.println("consumer_thread2��ǰ״̬��" + consumer_thread2.getState());
	}
}
