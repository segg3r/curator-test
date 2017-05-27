package com.segg3r.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configuration/zookeeper.properties")
public class CuratorConfig {

	@Value("${zookeeper.connectionString}")
	private String zookeeperConnectionString;

	@Bean
	public RetryPolicy retryPolicy() {
		return new ExponentialBackoffRetry(1000, 3);
	}

	@Bean
	public CuratorFramework curatorFramework() {
		System.out.println("Creating curator client for zookeeper " + zookeeperConnectionString);

		CuratorFramework curatorFramework = CuratorFrameworkFactory
				.newClient(zookeeperConnectionString, retryPolicy());
		curatorFramework.start();
		return curatorFramework;
	}

}
