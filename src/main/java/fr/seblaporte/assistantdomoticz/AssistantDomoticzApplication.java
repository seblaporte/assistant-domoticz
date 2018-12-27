package fr.seblaporte.assistantdomoticz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@IntegrationComponentScan
public class AssistantDomoticzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssistantDomoticzApplication.class, args);
	}
}
