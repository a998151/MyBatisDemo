package mybatis.dao;

import mybatis.bean.Employee;

/**
 * Created by ALemon on 2018/4/5.
 * 接口代理查询
 * 在Mapper 配置文件中工作地区要指定为接口的全类名
 */
public interface EmployeeMapper {
    Employee getEmployeeById(Integer id);
}
