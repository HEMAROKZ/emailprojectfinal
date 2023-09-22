package bdaytemplate.service;


import bdaytemplate.dao.EmployeeDAOImp;
import bdaytemplate.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	private EmployeeDAOImp employeeDAOImp;

	public EmailService(EmployeeDAOImp employeeDAOImp) {
		this.employeeDAOImp = employeeDAOImp;
	}

	@Value("${spring.mail.username}")
	private String senderEmail;

	public void sendEmailWithInlineImage() throws MessagingException {
		List<EmailRequest> to = employeeDAOImp.findAllWithBirthday();

		for (EmailRequest value : to) {
			String birthdayImagePath = "src/main/resources/brthday.jpg";
			String secondImagePath = "src/main/resources/Msyslogo.jpg"; // Provide the path to your second image
			String reportingManagerEmail = value.getReporting_manager();
			String cc = value.getReporting_manager();
			String bcc= senderEmail;; // Add the BCC recipient's email address// Add the BCC recipient's email address

			FileSystemResource birthdayResource = new FileSystemResource(new File(birthdayImagePath));
			FileSystemResource secondResource = new FileSystemResource(new File(secondImagePath)); // Load the second image

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String someVariable = value.getName();
//			String subject = "Happy Birthday " + someVariable.trim() + "!";
//			helper.setSubject(subject);
			String subject = "Happy Birthday " + someVariable+"!";
            helper.setSubject(subject);
			helper.setFrom("Talent Management <" + senderEmail + ">");

			if (value.getEmail() != null) {
				helper.addTo(value.getEmail()); // Add the original recipient (employee)

				if (reportingManagerEmail != null) {
					helper.addCc(cc);
				}
				
				helper.addCc(bcc);

				String content = "<b style='font-family: Verdana; color: black;'>Dear " + value.getName() + ",</b><br>"
						+ "<br><span style='font-family: Verdana; color: black;'>Msys wishes you a very Happy Birthday! Enjoy your Beautiful day</span>"
						+ "<br><br><br><img src='cid:brthday' alt='Birthday Image' width='500px' height='339px' /><br><br>"
						+ "<b style='color: black;'>Best Regards,</b><br><b style='color: black;'>Talent Management</b>"
						+ "<br><img src='cid:Msyslogo' alt='Second Image' title='Msys Logo' width='130px' height='50px' /><br><br>";


				helper.setText(content, true);

				// Add both inline images with unique Content-IDs
				helper.addInline("brthday", birthdayResource); // Birthday image
				helper.addInline("Msyslogo", secondResource); // Second image (use a unique Content-ID)

				mailSender.send(message);
			}
		}
	}
}
//	public int saveEmployee(EmailRequest emailRequest){
//		System.out.println("Inside saveEmployee service");
//
//		//return employeeDAOImp.save(emailRequest);
//	}
//}
///////////////////////////////////////////////////////////////////
//
//	public EmailRequest getEmployeeById(int employeeid){
//		if(employeeDAOImp.findById(employeeid).isEmpty()){
//			throw new EmployeeIdNotFoundException("invalid Employee id given ....!!!!");
//		}
//		else {
//			return employeeDAOImp.findById(employeeid).get(0);
//		}
//	}
//	///////////////////////////////////////////////////////////
//	public List<EmailRequest> getListOfAllEmployee(){
//		return employeeDAOImp.allEmployeeDetails();
//	}
//
//	////////////////////////////////////////////////////////////////////
//
//	public int updateEmployee(EmailRequest emailRequest){
//		return employeeDAOImp.update(emailRequest);
//	}
//////////////////////////////////////////////////////////
//	public int deleteEmployeeById(int employeeid){
//		return employeeDAOImp.deleteById(employeeid);
//	}
//
//
//}