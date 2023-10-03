package bdaytemplate.dao;

import bdaytemplate.dto.EmailRequest;

import java.util.List;

public interface EmployeeDAO {
public  List<EmailRequest> findAllWithBirthday();
//    public List<EmailRequest> findById(int employeeid);
//
//    public int save(EmailRequest emailRequest);
//
//    public List<EmailRequest> allEmployeeDetails();
//
//    public int deleteById(int employeeId);
//
//    public int update(EmailRequest e);
}
