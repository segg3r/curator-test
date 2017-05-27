package com.segg3r.zookeeper.sample;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class SharedLock implements Consumer<CuratorFramework> {

	@Override
	public void accept(CuratorFramework client) {
		try {
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
		} catch (Exception e) {
			throw new RuntimeException("Could not acquire shared lock", e);
		}
	}

}
