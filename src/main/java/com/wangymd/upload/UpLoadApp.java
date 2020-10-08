package com.wangymd.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传
 * @author win7
 *
 */
@SpringBootApplication
@RestController
public class UpLoadApp {
	
	@RequestMapping("hello")
	public String hello() {
		return "hello upload!";
	}
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(UpLoadApp.class);
		springApplication.run(args);
	}
}
