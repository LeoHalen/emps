package cn.cfxy.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cfxy.ssm.bean.Department;
import cn.cfxy.ssm.bean.Message;
import cn.cfxy.ssm.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 返回所有部门信息
	 * @return
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Message getDepts(){
		List<Department> list = departmentService.getDepts();
		return Message.success().add("depts", list);
	}
}
