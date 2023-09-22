package bdaytemplate.dao;

import bdaytemplate.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImp implements EmployeeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }


    @Override
    public List<EmailRequest> findAllWithBirthday() {
        String    SQL         ="SELECT employeeid,name,birthdate,email,reporting_manager FROM details WHERE  MONTH(birthdate) = MONTH(CURRENT_DATE()) AND DAY(birthdate) = DAY(CURRENT_DATE())";
        return getNamedParameterJdbcTemplate().query(SQL, new BeanPropertyRowMapper<EmailRequest>(EmailRequest.class));
    }

//    @Override
//    public int  save(EmailRequest e) {
//        String sql ="INSERT INTO details (employeeid,name, birthdate, email,reporting_manager) VALUES (:employeeid,:name, :birthdate,:email,:reporting_manager)";
//        MapSqlParameterSource paramSource = new MapSqlParameterSource();
//        paramSource.addValue("employeeid", e.getEmployeeid());
//        paramSource.addValue("name", e.getName());
//        paramSource.addValue("birthdate",e.getBirthdate());
//        paramSource.addValue("email", e.getEmail());
//        paramSource.addValue("reporting_manager", e.getReporting_manager());
//
//        return  namedParameterJdbcTemplate.update(sql, paramSource);
//    }
//
//   
//
//    @Override
//    public List<EmailRequest> allEmployeeDetails() {
//        String SQL ="SELECT * FROM details";
//        return getNamedParameterJdbcTemplate().query(SQL, new BeanPropertyRowMapper<EmailRequest>(EmailRequest.class));
//    }
//
//
//    @Override
//    public int deleteById(int employeeId) {
//        return jdbcTemplate.update("DELETE FROM details WHERE employeeid=?", employeeId);
//    }
//
//
//    @Override
//    public int update(EmailRequest e) {
//        String             sql         ="update details set name= :name, birthdate= :birthdate, email=:email, reporting_manager=:reporting_manager where employeeid= :employeeid ";
//        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("employeeid", e.getEmployeeid()).addValue("name", e.getName()).addValue("birthdate", e.getBirthdate()).addValue("email", e.getEmail()).addValue("reporting_manager", e.getReporting_manager());
//        System.out.println("here in inventorydtoimp ========================");
//        return  namedParameterJdbcTemplate.update(sql, paramSource);
//    }
//
//    @Override
//    public List<EmailRequest> findById(int employeeid) {
//        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("employeeid", employeeid);
//        return getNamedParameterJdbcTemplate().query("SELECT * FROM details WHERE employeeid = :employeeid ", mapSqlParameterSource , new BeanPropertyRowMapper<EmailRequest>(EmailRequest.class));
//    }
}
