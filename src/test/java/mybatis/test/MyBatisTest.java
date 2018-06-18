package mybatis.test;

import mybatis.bean.Employee;
import mybatis.dao.EmployeeMapper;
import mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ALemon on 2018/4/5.
 */
public class MyBatisTest {

    //获取日志对象
    private static Logger logger = Logger.getLogger(MyBatisTest.class);

    /**
     * 每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为中心的。
     * SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。
     * 而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。
     *
     * @return SqlSessionFactory
     */
    public SqlSessionFactory getSqlSessionFactory() {
        //加载配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        return sqlSessionFactory;
    }


    /**
     * 从 SqlSessionFactory 中获取 SqlSession
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //获取 sqlSession 实例，这个实例可以直接执行已经映射的 SQL 语句
        //另外，sqlSession 是非线程安全的，每次使用都应该去获取新的对象。
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //需要在 Map 中写好这个唯一标识的 语句才可以正常运行
            Employee employee = sqlSession.selectOne("selectEmp", 1);
            //执行完成之后，需要进行提交
            sqlSession.commit();

            //如果返回的结果字段中没有实体对象中的某一个字段，则实体对象中这个字段默认为 null。
            System.out.println(employee);
        } finally {
            //整个 SQL 过程查询完成后需要关闭对象，释放资源
            sqlSession.close();
        }
    }

    //接口代理方式，接口与 Mapper 文件进行绑定
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
        } finally {
            //关闭资源
            sqlSession.close();
        }
    }


    //基于注解的Mapper
    @Test
    public void test2() {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //Mapper为接口类型，通过在接口上用注解写入需要用到 SQL 语句
            //之后再和全局配置文件绑定，之后也会创建代理对象
            EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmployeeById(2);
            logger.info(employee);
            logger.info(mapper);
        } finally {
            //关闭资源
            sqlSession.close();
        }
    }

    //增删改查测试
    @Test
    public void test3() {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            //多参数，使用 @Param 注解
            Employee a = mapper.getEmployeeByIdAndLastName(7, "阿A");
            System.out.println(a);

            //多参数，使用 map
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id",6);
            map.put("lastName","阿B");
            Employee b = mapper.getEmployeeByMap(map);
            System.out.println(b);


            //添加，id 为自增长 不用管，设置为 null 就行
//            Employee employee = new Employee(null,"阿A", "aniu@email.com", "0");
//            mapper.addEmp(employee);
//            System.out.println(employee);

            //修改
//            Employee employeeUpdate = new Employee(1, "Alon", "Alon@email.com", "1");
//            mapper.updateEmp(employeeUpdate);
//
//            //删除
//            mapper.deleteEmpById(4);

            //提交数据，使之生效
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}