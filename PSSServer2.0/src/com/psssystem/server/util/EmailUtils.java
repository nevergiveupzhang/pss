package com.psssystem.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class EmailUtils {
	public static void sendEmail(final String from, final String to, final String subject,
			final String content) {
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				Properties props = new Properties();
				try {
					InputStream in = Files.newInputStream(Paths
							.get("config/mail.properties"));
					props.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Session mailSession = Session.getDefaultInstance(props);
				// mailSession.setDebug(true);
				MimeMessage message = new MimeMessage(mailSession);
				Transport tr = null;
				try {
					message.setFrom(new InternetAddress(from));
					message.addRecipient(RecipientType.TO, new InternetAddress(to));
					message.setSubject(subject);
					message.setText(content);
					tr = mailSession.getTransport();
					tr.connect(null, "wsymyzqdzgr1991");
					tr.sendMessage(message, message.getAllRecipients());
				} catch (MessagingException e) {
					e.printStackTrace();
				} finally {
					try {
						tr.close();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
	}
	
	public static void main(String []args){
		EmailUtils.sendEmail("zhtao01@163.com", "zhtao01@163.com", "zhtao01@163.com", "zhtao01@163.com");
	}
}
