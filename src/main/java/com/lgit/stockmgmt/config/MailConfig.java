package com.lgit.stockmgmt.config;

import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("${email.host}")
	private String host;

	@Value("${email.port}")
	private Integer port;

	@Value("${email.username}")
	private String username;

	@Value("${email.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		if (host.equals("smtp.gmail.com")) {
			properties.setProperty("mail.transport.protocol", "smtp");// gmail
			properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");// gmail
			properties.setProperty("mail.smtp.auth", "true");// gmail
			properties.setProperty("mail.smtp.starttls.enable", "true");// gmail
		} else if (host.contains("cafe24.com")) {
			properties.setProperty("mail.smtp.auth", "true");// cafe24
		}

		return properties;
	}
}