package com.segg3r.zookeeper;

import com.segg3r.zookeeper.config.CuratorConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

public class Runner {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				CuratorConfig.class);

		CuratorFramework client = applicationContext.getBean(CuratorFramework.class);

		InterProcessMutex lock = new InterProcessMutex(client, "/zookeeperTest/upgradeLock");
		if (lock.acquire(1000, TimeUnit.MILLISECONDS)) {
			try {
				System.out.println("Acquired shared lock");
				Thread.sleep(60000);
			} finally {
				lock.release();
			}
		} else {
			System.out.println("Could not acquire shared lock");
		}
	}

}
