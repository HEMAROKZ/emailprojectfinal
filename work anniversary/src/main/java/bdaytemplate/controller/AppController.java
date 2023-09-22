package bdaytemplate.controller;

import bdaytemplate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class AppController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @PostMapping("/error")
    public String viewHomePage() {
        return "index";
    }

//    @GetMapping("/welcome")
//    public String welcomePage() {
//        return "welcome";
//    }

    @GetMapping("/login")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("login");
        // modelAndView.addObject("employee", new EmployeeDetails()); // Add the "employee" object
        System.out.println("Inside welcome controller");
        return modelAndView;
    }
    @GetMapping("/emailwithimage")
    public String sendHTMLEmailWithInlineImage1(Model model) throws MessagingException {
        emailService.sendEmailWithInlineImage();
        model.addAttribute("message", "Email Sent Successfully");
        return "result";
    }

    @Scheduled(cron = "0 2 13 * * ?", zone = "Asia/Kolkata")
    public void sendHTMLEmailWithInlineImageDaily() throws MessagingException, IOException {
        emailService.sendEmailWithInlineImage();
    }
}

