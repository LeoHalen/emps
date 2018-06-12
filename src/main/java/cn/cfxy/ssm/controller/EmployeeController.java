package cn.cfxy.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cfxy.ssm.bean.Employee;
import cn.cfxy.ssm.bean.Message;
import cn.cfxy.ssm.service.EmployeeService;

/**
 * 处理员工CRUD请求
 * @author HALEN
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 单个批量二合一
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Message deleteEmpById(@PathVariable("ids")String ids){
		//批量删除，else单个删除
		if(ids.contains("-")){
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//组装id的集合
			for(String str : str_ids){
				del_ids.add(Integer.parseInt(str));
			}
			employeeService.deleteBatch(del_ids);
		}else{
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Message.success();
	}
	
	/**
	 * 如果直接发送ajax=PUT请求
	 * 封装的数据Employee [empId=2, empName=null, gender=null, email=null, deptId=null]
	 * 问题：请求体中有数据，但是employee对象封装不上，update tb_emp where emp_id = 2
	 * 原因：
	 * Tomcat：1、将请求中的数据，封装一个map。
	 * 		  2、request.getParameter("empName")就会从这个map中取值。
	 * 		  3、SpringMVC封装POJO对象的时候，会把POJO中每个属性的值，request.getParamter("email");
	 * AJAX发送PUT请求引发的血案，
	 * 		PUT请求：请求体中的数据，request.getParameter("empName")拿不到数据
	 * 		Tomcat判断到是PUT请求后不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
	 * 
	 * 要能支持发送PUT类的请求还要封装请求体中的数据
	 * 在web.xml中配置 HttpPutFormContentFilter
	 * 其作用就是将请求体中的数据解析成一个map
	 * request被重新包装，request.getParameter（）被重写，就会从自己封装的map中取出数据
	 * 员工更新方法
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	@ResponseBody
	public Message updateEmp(Employee employee){
		System.out.println("数据"+employee);
		employeeService.updateEmp(employee);
		return Message.success();
	}
	
	/**
	 * 根据id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id")Integer id){
		
		Employee employee = employeeService.getEmp(id);
		
		return Message.success().add("emp", employee);
	}
	
	
	/**
	 * 检验用户名是否可用
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Message checkUser(@RequestParam("empName")String empName){
		//先判断用户名是否是合法的表达式
		String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regex)){
			return Message.fail().add("va_msg", "用户名必须是6-16位英文与数字组合或2-5位中文！");
		}
		boolean bo = employeeService.checkUser(empName);
		if(bo){
			return Message.success();
		}else{
			return Message.fail().add("va_msg", "用户名不可用");
		}
	}
	
	
	
	
	/**
	 * 员工保存
	 * 1、支持spring的JSR303检验
	 * 2、导入hivernate-Validator
	 * 
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Message saveEmp(@Valid Employee employee,BindingResult result){
		if(result.hasErrors()){
			//校验失败，应该返回，在模态框中显示校验失败的错误信息
			Map<String ,Object> map = new HashMap<String,Object>();
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError : errors){
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields", map);
		}else{
			employeeService.saveEmp(employee);
			return Message.success();
		}
	}
	
	/**
	 * 
	 * springMVC的@ResponseBody可以将返回的pageInfo 变为json对象，
	 * @ResponseBody要想工作的话必须导入jackson jar包
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Message getEmpsWithJson(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo){
		System.out.println("测试");
		//引入pageHelper分页插件,在查询之前只需要调用传入页码，以及分页每页的大小
		PageHelper.startPage(pageNo,5);
		//startPage后面紧跟的是这个查询就是一个分页查询
		List<Employee> empsList = employeeService.getAll();
		System.out.println("数据量————————————————————————"+empsList.size());
		//使用pageInfo包装查询后的结果
		//封装了详细的分页信息，包括有我们查询出来的数据,后一个参数表示传入连续显示的页数
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(empsList,5);
		return Message.success().add("pageInfo",pageInfo);
	}
	
	
	/**
	 * 查询员工数据（分页查询）
	 * @return
	 */
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model){
		System.out.println("测试");
		//引入pageHelper分页插件,在查询之前只需要调用传入页码，以及分页每页的大小
		PageHelper.startPage(pageNo,5);
		//startPage后面紧跟的是这个查询就是一个分页查询
		List<Employee> empsList = employeeService.getAll();
		System.out.println("数据量————————————————————————"+empsList.size());
		//使用pageInfo包装查询后的结果
		//封装了详细的分页信息，包括有我们查询出来的数据,后一个参数表示传入连续显示的页数
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(empsList,5);
		model.addAttribute("pageInfo",pageInfo);
		return "list";
	}
}
