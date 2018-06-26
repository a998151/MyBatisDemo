package mybatis.dao;

import mybatis.bean.Employee;

/**
 * Created by ALemon on 2018/6/24.
 */
public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);
}
