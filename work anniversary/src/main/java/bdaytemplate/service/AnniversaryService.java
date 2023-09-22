
package bdaytemplate.service;


import bdaytemplate.customeexception.EmployeeIdNotFoundException;
import bdaytemplate.dao.AnniversaryDAOImp;
import bdaytemplate.dto.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnniversaryService {

	@Autowired
	private JavaMailSender mailSender;

	private AnniversaryDAOImp anniversaryDAOImp;

	public AnniversaryService(AnniversaryDAOImp anniversaryDAOImp) {
		this.anniversaryDAOImp = anniversaryDAOImp;
	}

	@Value("${spring.mail.username}")
	private String senderEmail;

public String getAnniversarySuffix(int years) {
	    String[] suffixes = {"th", "st", "nd", "rd"};
	    int index = (years % 10 > 0 && years % 10 < 4 && (years / 10 % 10 != 1)) ? years % 10 : 0;
	    return suffixes[index];
	}

public void sendEmailWithInlineImage() throws MessagingException {
    List<EmployeeDetails> to = anniversaryDAOImp.findAllWithAnniversary();

    for (EmployeeDetails value : to) {
        LocalDate joiningDate = value.getJoiningdate();
        LocalDate today = LocalDate.now();

        // Calculate the years of work anniversary
       
        int yearsAnniversary = today.getYear() - joiningDate.getYear();
        String anniversarySuffix = getAnniversarySuffix(yearsAnniversary);
        
        // Check if the joining date anniversary is today
        if (joiningDate.getMonth() == today.getMonth() && joiningDate.getDayOfMonth() == today.getDayOfMonth()) {
            try {
                String anniversaryImagePath = "src/main/resources/anniversary.jpg";
                String secondImagePath = "src/main/resources/Msyslogo.jpg"; // Provide the path to your second image
                String reportingManagerEmail = value.getReporting_manager();
                String cc = "rhemanth171297@gmail.com";
                String bcc = value.getReporting_manager(); // Add the BCC recipient's email address

                FileSystemResource anniversaryResource = new FileSystemResource(new File(anniversaryImagePath));
                FileSystemResource secondResource = new FileSystemResource(new File(secondImagePath)); // Load the second image

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String someVariable = value.getName();

               // String anniversarySuffix = getAnniversarySuffix(yearsAnniversary);
                String subject = "Happy " + yearsAnniversary + anniversarySuffix + " Anniversary, " + someVariable + "!";
                helper.setSubject(subject);

                if (value.getEmail() != null) {
                    helper.addTo(value.getEmail()); // Add the original recipient (employee)

                    if (reportingManagerEmail != null) {
                        helper.addCc(cc);
                    }
                    helper.addCc(senderEmail);
                    helper.addCc(bcc);

                    String content = "<b style='font-family: Verdana; color: black;'>Dear " + value.getName() + ",</b><br>"
                            + "<br><span style='font-family: Verdana; color: black;'>Congratulations on your work anniversary at Msys! We appreciate your dedication and hard work.</span>"
                            + "<br><br><br><img src='cid:anniversary' alt='Anniversary Image' width='500px' height='339px' /><br><br>"
                            + "<b style='color: black;'>Best Regards,</b><br><b style='color: black;'>Talent Management</b>"
                            + "<br><img src='cid:Msyslogo' alt='Second Image' title='Msys Logo' width='130px' height='50px' /><br><br>";

                    helper.setText(content, true);

                    // Add both inline images with unique Content-IDs
                    helper.addInline("anniversary", anniversaryResource); // Anniversary image
                    helper.addInline("Msyslogo", secondResource); // Second image (use a unique Content-ID)

                    mailSender.send(message);

                    System.out.println("Sent an anniversary email to " + value.getEmail());
                }
            } catch (Exception e) {
                System.err.println("Error sending anniversary email to " + value.getEmail() + ": " + e.getMessage());
            }
        }
    }
}
public int saveEmployee(EmployeeDetails anniversaryRequest){
	System.out.println("Inside saveEmployee service");

	return anniversaryDAOImp.save(anniversaryRequest);
}

public EmployeeDetails getEmployeeById(int employeeid){
	if(anniversaryDAOImp.findById(employeeid).isEmpty()){
		throw new EmployeeIdNotFoundException("invalid Employee id given ....!!!!");
	}
	else {
		return anniversaryDAOImp.findById(employeeid).get(0);
	}
}

public List<EmployeeDetails> getListOfAllEmployee(){
	return anniversaryDAOImp.allEmployeeDetails();
}

public int update(EmployeeDetails anniversaryRequest){
	return anniversaryDAOImp.update(anniversaryRequest);
}

public int deleteEmployeeById(int employeeid){
	return anniversaryDAOImp.deleteById(employeeid);
}


}

