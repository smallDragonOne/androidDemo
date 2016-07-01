package com.example.apple.sometestdemo.ApiManage;
import org.apache.http.message.BasicNameValuePair;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/**
 * API工具类
 * @author WJ
 *
 */
public class ApiUtils {
	final static String Token = "452sfdrtERETre8eHUte312wMr0rAcV";


	/**
	 * 生成验证身份的数据
	 * @param action 无此参数时传入null
	 * @return List<BasicNameValuePair>
	 */
	public static List<BasicNameValuePair> getList(String action)
	{
		String timestamp = String.valueOf(System.currentTimeMillis());

		String ArrTmp = Token + timestamp;
		char[] chars = ArrTmp.toCharArray();	// 转为字符数组
		Arrays.sort(chars);						// ASCII码 升序排列

		StringBuilder tmpStr = new StringBuilder();
		for (char x : chars) {
			tmpStr.append(x);
		}
		// 重新拼接为字符串后MD5编码
		String signature = MD5Util.getSubString(tmpStr.toString(), 4, 24);
		List<BasicNameValuePair> postValue=new LinkedList<>();
		if(action != null){
			postValue.add(new BasicNameValuePair("action",action));
		}
		postValue.add(new BasicNameValuePair("signature", signature));
		postValue.add(new BasicNameValuePair("timestamp", timestamp));
		return postValue;
	}
}