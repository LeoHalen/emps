package cn.cfxy.ssm.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回类
 * @author HALEN
 *
 */
public class Message {

	//状态码，比如可以100-成功，200-失败，自定义
	private int code;
	//提示信息
	private String msg;
	//用户要返回给浏览器的数据
	
	/**
	 * 成功方法
	 * @return
	 */
	public static Message success(){
		Message result = new Message();
		result.setCode(100);
		result.setMsg("处理成功");
		return result;
	}
	/**
	 * 失败方法
	 * @return
	 */
	public static Message fail(){
		Message result = new Message();
		result.setCode(200);
		result.setMsg("处理失败");
		return result;
	}
	/**
	 * 自定义的快捷添加键值的方法
	 * @param key
	 * @param value
	 * @return
	 */
	public Message add(String key,Object value){
		this.getExtend().put(key, value);
		return this;
		
	}
	
	private Map<String, Object> extend = new HashMap<String, Object>();
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
	
}
