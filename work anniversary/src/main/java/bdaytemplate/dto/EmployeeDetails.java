package bdaytemplate.dto;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "details")
public class EmployeeDetails {

    @Id
    private int employeeid;
    private String name;
    private LocalDate birthdate;
    private String email;
    private LocalDate joiningDate;
    private String reporting_manager;

    public EmployeeDetails() {
    }

    public EmployeeDetails(int employeeid, String name,LocalDate joiningdate, LocalDate birthdate, String email, String reporting_manager) {
        this.employeeid = employeeid;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.joiningDate = joiningdate;
        this.reporting_manager = reporting_manager;
    }


    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getJoiningdate() {
        return joiningDate;
    }

    public void setJoiningdate(LocalDate joiningdate) {
        this.joiningDate = joiningdate;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReporting_manager() {
        return reporting_manager;
    }

    public void setReporting_manager(String reporting_manager) {
        this.reporting_manager = reporting_manager;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "employeeid=" + employeeid +
                ", name='" + name + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", joiningdate=" + joiningDate +
                ", reporting_manager='" + reporting_manager + '\'' +
                '}';
    }
}

