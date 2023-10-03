package bdaytemplate.dao;

import bdaytemplate.dto.EmployeeDetails;

import java.util.List;

public interface AnniversaryDAO {

    public List<EmployeeDetails>findAllWithAnniversary();

    public List<EmployeeDetails> findById(String employeeid); // Changed to String

    public int save(EmployeeDetails anniversaryRequest);

    List<EmployeeDetails> allEmployeeDetails();

    public boolean employeeIdExists(String employeeId) ;

        public int deleteById(String employeeId); // Changed to String

    public int update(EmployeeDetails e);


}
