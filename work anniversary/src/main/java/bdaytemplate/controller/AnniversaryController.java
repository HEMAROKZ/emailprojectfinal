
package bdaytemplate.controller;

import bdaytemplate.dto.EmployeeDetails;
import bdaytemplate.service.AnniversaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class AnniversaryController {

	@Autowired(required=true)
	private JavaMailSender mailSender;

	@Autowired
	private AnniversaryService anniversaryService;


	@PostMapping("/errors")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/images")
	public String sendHTMLEmailWithInlineImage1(Model model) throws MessagingException {
		anniversaryService.sendEmailWithInlineImage();
	    model.addAttribute("message", "An anniversary mail sent");
	    return "anniversaryresult";
	}

	@Scheduled(cron = "0 02 13 * * ?", zone = "Asia/Kolkata")
	// Daily at 10:10 AM IST
	public void sendHTMLEmailWithInlineImageDaily() throws MessagingException, IOException {
		anniversaryService.sendEmailWithInlineImage();
	}
/////////////////////////////////////////////add employee /////////////////////////////
	
	@GetMapping("/adding")
	public ModelAndView addEmployeeDetail() {
	    ModelAndView modelAndView = new ModelAndView("add");
	   // modelAndView.addObject("employee", new EmployeeDetails()); // Add the "employee" object
	    System.out.println("Inside add employee");
	    return modelAndView;
	}

	@PostMapping("/add-Inventryitems")
	public ModelAndView saveEmployee(
	    @RequestParam("employeeid") int employeeid,
	    @RequestParam("name") String name,
	    @RequestParam("joiningdate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate joiningdate,
	    @RequestParam("birthdate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate birthdate,
	    @RequestParam("email") String email,
	    @RequestParam("reporting_manager") String reporting_manager) {

	    EmployeeDetails employee = new EmployeeDetails(employeeid, name, joiningdate, birthdate, email, reporting_manager);
	    anniversaryService.saveEmployee(employee);

	    ModelAndView model = new ModelAndView();
	    model.setViewName("add");
	    return model;
	}

/////////////////////////////////////////////get employee /////////////////////////////

   @GetMapping("/employees/{id}")
	public EmployeeDetails getEmployeeById(@PathVariable int id) {
		return anniversaryService.getEmployeeById(id);
	}


	/////////////////////////////////////////////get all employee /////////////////////////////

	@GetMapping("/getAllEmployees")
	public ModelAndView getListOfAllEmployee() {
		List<EmployeeDetails> list  = anniversaryService.getListOfAllEmployee();
		System.out.println(list);
		ModelAndView  model =new ModelAndView();
		model.addObject("list",list);
		model.setViewName("get");
		return model;
	}

	/////////////////////////////////////////////delete employee /////////////////////////////
	@GetMapping("/deleteing")
	public ModelAndView getListOfAllEmployeeDelete() {
		List<EmployeeDetails> list  = anniversaryService.getListOfAllEmployee();
		System.out.println(list);
		ModelAndView       model =new ModelAndView();
		model.addObject("list",list);
		model.setViewName("Deleting_page");
		return model;
	}

	@GetMapping("/del/{id}")
	public ModelAndView deleteProductById(@PathVariable int id) {
	 anniversaryService.deleteEmployeeById(id);
		List<EmployeeDetails> list= anniversaryService.getListOfAllEmployee();

		ModelAndView model=new ModelAndView();
		model.addObject("list",list);

		model.setViewName("get");
		return model;
	}

/////////////////////////////////////////////update employee /////////////////////////////

	@GetMapping("/updating")
	public ModelAndView getListOfAllEmployeeUpdate() {
		List<EmployeeDetails> list  = anniversaryService.getListOfAllEmployee();
		System.out.println(list);
		ModelAndView       model =new ModelAndView();
		model.addObject("list",list);
		model.setViewName("updating_page");
		return model;
	}
	@RequestMapping(value = "updating/employees/{employeeid}", method = RequestMethod.GET)
	public ModelAndView updatePage(@PathVariable("employeeid") int employeeid) {
    ModelAndView model=new ModelAndView("update");

		model.addObject("employeeid",employeeid);
		model.addObject("employee", anniversaryService.getEmployeeById(employeeid));
		return model;

	}

	@RequestMapping(value = "updates/users", method = RequestMethod.POST)
	public ModelAndView updateUser(
	    @RequestParam int employeeid,
	    @RequestParam(value = "name", required = true) String name,
	    @RequestParam(value = "birthdate", required = true) String birthdateStr, // Receive as String
	    @RequestParam(value = "joiningdate", required = true) String joiningdateStr, // Receive as String
	    @RequestParam(value = "email", required = true) String email,
	    @RequestParam(value = "reporting_manager", required = true) String reporting_manager) {

	    try {
	        // Parse the 'dd-MM-yyyy' formatted dates
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
	        LocalDate joiningdate = LocalDate.parse(joiningdateStr, formatter);

	        EmployeeDetails userDetail = new EmployeeDetails();
	        userDetail.setEmployeeid(employeeid);
	        userDetail.setName(name);
	        userDetail.setBirthdate(birthdate);
	        userDetail.setJoiningdate(joiningdate); // Save as LocalDate
	        userDetail.setEmail(email);
	        userDetail.setReporting_manager(reporting_manager);

	        int resp = anniversaryService.update(userDetail);
	        ModelAndView model = new ModelAndView();

	        if (resp > 0) {
	            model.addObject("msg", "User with id : " + employeeid + " updated successfully.");
	            model.addObject("list", anniversaryService.getListOfAllEmployee());
	            model.setViewName("get");
	            return model;
	        } else {
	            model.addObject("msg", "User with id : " + employeeid + " updation failed.");
	            model.addObject("userDetail", anniversaryService.getEmployeeById(employeeid));
	            model.setViewName("update");
	            return model;
	        }
	    } catch (DateTimeParseException e) {
	        ModelAndView model = new ModelAndView();
	        model.addObject("employeeid", employeeid);
	        model.addObject("msg", "Invalid date format. Please use dd-MM-yyyy.");
	        model.addObject("userDetail", anniversaryService.getEmployeeById(employeeid));
	        model.setViewName("update");
	        return model;
	    }
	}



	///////////////////////////////updating page redirect///////////////////////////////////////



}