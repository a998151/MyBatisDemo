package mybatis.test;

import mybatis.bean.Employee;
import mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ALemon on 2018/4/5.
 */
public class MyBatisTest {

    //获取日志对象
    private static Logger logger = Logger.getLogger(MyBatisTest.class);

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
        //另外，sqlSession 是非线程安全的，每次使用都应该去获取新的对象。
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
        try {
            //获取接口实现类对象
            //只要接口和 Mapper 配置文件绑定，这一步会为它创建一个代理对象，由代理对象实现增删改查
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
            logger.info(mapper);                //查看对象是否为代理对象，带有 "Proxy@xxx" 字样
        }finally {
            //关闭资源
            sqlSession.close();
        }
    }

}