package cn.sysu.sse.recruitment.job_platform_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.sysu.sse.recruitment.job_platform_api.server.mapper")
public class JobPlatformApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPlatformApiApplication.class, args);
	}

}
