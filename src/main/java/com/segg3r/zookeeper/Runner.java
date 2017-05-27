package com.segg3r.zookeeper;

import com.segg3r.zookeeper.config.CuratorConfig;
import com.segg3r.zookeeper.sample.SharedLock;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				CuratorConfig.class);

		CuratorFramework client = applicationContext.getBean(CuratorFramework.class);
		SharedLock sample = applicationContext.getBean(SharedLock.class);
		sample.accept(client);
	}

}
