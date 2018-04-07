package mybatis.dao;

import mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ALemon on 2018/4/7.
 */
public interface EmployeeMapperAnnotation {

    @Select("select * from  tbl_employee where id = #{id}")
    Employee getEmployeeById(Integer id);
}
