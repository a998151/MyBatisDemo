package mybatis.dao;

import mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ALemon on 2018/4/5.
 * 接口代理查询
 * 在Mapper 配置文件中工作地区要指定为接口的全类名
 */
public interface EmployeeMapper {

    Employee getEmployeeById(Integer id);

    boolean addEmp(Employee employee);

    boolean updateEmp(Employee employee);

    boolean deleteEmpById(Integer id);

    Employee getEmployeeByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    Employee getEmployeeByMap(Map<String,Object> map);

    List<Employee> getEmpByLastName(String lastName);

    @MapKey("id")
    Map<Integer , Employee> getEmpByLastNameLikeReturnMap(String lastName);
}
