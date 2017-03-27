package com.finra.boot;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTask {

	private final JavaMailSender javaMailSender;

	@Autowired
	ScheduleTask(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Scheduled(cron = "${0 0 0/1 * * ?}")
	public void reportCurrentTime() throws IOException {
		
		Path watchFolder = Paths.get("src/main/resources/watch");
		try {
	           WatchService watcher = watchFolder.getFileSystem().newWatchService();
	           watchFolder.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
	           StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

	           WatchKey watckKey = watcher.take();

	           List<WatchEvent<?>> events = watckKey.pollEvents();
	           for (WatchEvent event : events) {
	                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
	                    System.out.println("Created: " + event.context().toString());
	                }
	                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
	                    System.out.println("Delete: " + event.context().toString());
	                }
	                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
	                    System.out.println("Modify: " + event.context().toString());
	                }
	            }

	        } catch (Exception e) {
	            System.out.println("Error: " + e.toString());
	        }
	}


	SimpleMailMessage send(String filename) {       
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("someone@localhost");
		mailMessage.setReplyTo("someone@localhost");
		mailMessage.setFrom("someone@localhost");
		mailMessage.setSubject("file added");
		mailMessage.setText(filename);
		javaMailSender.send(mailMessage);

		return mailMessage;

	}        

}
