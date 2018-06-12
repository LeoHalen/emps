package cn.cfxy.ssm.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cfxy.ssm.bean.Department;
import cn.cfxy.ssm.dao.DepartmentMapper;
import cn.cfxy.ssm.dao.EmployeeMapper;

/**
 * 导入springTest 模块
 * @author HALEN
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class EmployeeTest {

	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	DepartmentMapper departmentMapper;
	//批量的sqlSession
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 测试employeeMapper
	 */
	@Test
	public void testEmployee(){
		System.out.println(employeeMapper);
		//1、插入一些数据到employee
//		Employee emp = new Employee(null,"Jerry","M","Jerry@qq.com",1);
//		Employee emp = new Employee(null,"Smith","男","Smith@qq.com",2);
//		employeeMapper.insertSelective(emp);
		//2、批量插入多个员工，批量，使用可以执行批量操作的sqlSession
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i=0;i<1000;i++){
//			String uid = UUID.randomUUID().toString().substring(0,5);
//			mapper.insertSelective(new Employee(null,uid,"女",uid+"@qq.com",1));
//		}
//		System.out.println("批量插入完成-----");
	}
	/**
	 * 测试departmentMapper
	 */
	@Test
	public void testDepartment(){
//		Logger logger = Logger.getLogger(DepartmentMapper.class);
//        logger.debug("开始");
//        logger.debug("结束");
		System.out.println(departmentMapper);
		//1、插入一些数据到department
		Department dept = new Department();
		dept.setDeptName("测试部");
		departmentMapper.insertSelective(dept);
		
		
	}
	
	
	
	/*private SqlSessionFactory sqlSessionFactory;
	@Before
	public void setUp() throws Exception {
		//mybatis配置文件
		String resource = "mbg.xml";
		//得到配置文件流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//创建会话工厂，传入mybatis配置文件信息
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void test() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//创建代理对象
		EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
		//调用mapper的方法
		Integer empId = 1;
		Employee emp = empMapper.selectByPrimaryKey(empId);
		System.out.println(emp);
	}*/

}
