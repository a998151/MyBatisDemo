package mybatis.test;

import mybatis.bean.Employee;
import mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by ALemon on 2018/4/5.
 */
public class MyBatisTestTest {

    public SqlSessionFactory getSqlSessionFactory(){
        //加载配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        }catch (IOException e){
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        return sqlSessionFactory;
    }


    @Test
    public void test() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //获取 sqlSession 实例，这个实例可以直接执行已经映射的 SQL 语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("selectEmp", 1);
            sqlSession.commit();
            System.out.println(employee.toString());
        }finally {
            sqlSession.close();
        }
    }

    //接口代理方式
    @Test
    public void test1() {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口实现类对象
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.getEmployeeById(1);
        System.out.println(employee);
    }

}