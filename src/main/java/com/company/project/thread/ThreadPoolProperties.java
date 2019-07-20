package com.company.project.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liyq
 */
@Data
@Component
public class ThreadPoolProperties {

	/**
	 * 线程池维护线程的最少数量
	 */
	private int corePoolSize = 10;

	/**
	 * 线程池维护线程的最大数量
	 */
	private int maxPoolSize = 20;

	/**
	 * 允许的空闲时间
	 */
	private int keepAliveSeconds = 200;

	/**
	 * 缓存队列
	 */
	private int queueCapacity = 10000;
}
