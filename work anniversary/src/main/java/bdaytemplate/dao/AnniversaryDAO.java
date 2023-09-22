
package bdaytemplate.dao;

import bdaytemplate.dto.EmployeeDetails;

import java.util.List;

public interface AnniversaryDAO {
	
public  List<EmployeeDetails> findAllWithAnniversary();

    public List<EmployeeDetails> findById(int employeeid);

    public int save(EmployeeDetails anniversaryRequest);

    public List<EmployeeDetails> allEmployeeDetails();

    public int deleteById(int employeeId);

    public int update(EmployeeDetails e);
}

