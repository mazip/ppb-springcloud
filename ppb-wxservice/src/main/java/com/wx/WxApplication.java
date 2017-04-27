package com.wx;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author daizy
 * 
 * 微信微服务入口
 *
 */
@SpringBootApplication
public class WxApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(WxApplication.class).web(true).run(args);
	}
}
	