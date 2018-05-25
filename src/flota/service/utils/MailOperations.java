package flota.service.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailOperations {

	private static final Logger logger = LogManager.getLogger(MailOperations.class);

	public static void sendMail(String mailMessage) {

		String to = "florin.brasoveanu@arabesque.ro";
		String from = "Delegatii";
		String host = "localhost"; // mail.arabesque.ro, 10.1.5.5

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Delegatii");

			message.setText(mailMessage);

			Transport.send(message, message.getAllRecipients());

		} catch (Exception mex) {
			logger.error(mex.toString());
		}

	}

	public static void sendMailName(String mailMessage) {

		String to = "florin.brasoveanu@arabesque.ro";
		String from = "Delegatii";
		String host = "mail.arabesque.ro"; // mail.arabesque.ro, 10.1.5.5

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Delegatii");

			message.setText(mailMessage);

			Transport.send(message, message.getAllRecipients());

		} catch (Exception mex) {
			logger.error(mex.toString());
		}

	}

	public static void sendMail(String subject, String mailMessage) {

		String to = "florin.brasoveanu@arabesque.ro";
		String from = "Delegatii";
		String host = "localhost"; // mail.arabesque.ro, 10.1.5.5

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject(subject);

			message.setText(mailMessage);

			Transport.send(message, message.getAllRecipients());

		} catch (Exception mex) {
			logger.error(mex.toString());
		}

	}

	public static void sendMailNotificare(String mailAddress, String mailMessage) {

		String to = mailAddress;
		String from = "Delegatii";
		String host = "localhost";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Delegatii");

			message.setText(mailMessage);

			Transport.send(message, message.getAllRecipients());

		} catch (Exception mex) {
			logger.error(mex.toString());
		}

	}

}
