package com.chasal.auction.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.chasal.auction.client.util.HttpUtil;
import com.chasal.auction.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	// 定义两个界面中的两个文本框
	private EditText etName;
	private EditText etPass;

	// 定义界面中的两个按钮
	private Button bnLogin;
	private Button bnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// 获取界面中的两个编辑框
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		// 获取界面中的两个按钮
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);

		// 为bnCancel按钮的单击事件绑定事件监听器
		// bnCancel.setOnClickListener(new Home);

		bnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 执行输入校验
				if (validate()) {
					// 如果登陆成功
					if (loginPro()) {
						//启动Main Activity
						Intent intent = new Intent(Login.this, MainActivity.class);
						startActivity(intent);
						
						//结束该Activity
						finish();
					}else{
						//弹出对话框。。。
						Log.i("TAG","用户名称或者密码错误，请重新输入！");
					}
				}
			}
		});
	}

	private  boolean loginPro() {
		//获取用户名和密码
		String username = etName.getText().toString().trim();
		String pwd = etPass.getText().toString().trim();
		
		JSONObject jsonObject;
		try {
			jsonObject = query(username, pwd);
			//如果userId大于0
			if(jsonObject.getInt("userId") > 0){
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//弹出对话框。。。。。
			
		} 
		return false;
	}

	/**
	 * 对用户输入的用户名、密码进行校验
	 * 
	 * @return
	 */
	private boolean validate() {
		String username = etName.getText().toString().trim();
		if (username.equals("")) {
			// 弹出对话框
			// ...
		}

		String pwd = etPass.getText().toString().trim();
		if (pwd.equals("")) {
			// 弹出对话框
			// ...
		}

		return true;
	}
	
	/**
	 * 定义发送请求的方法
	 * @param username
	 * @param password
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONObject query(String username, String password)
			throws JSONException, InterruptedException, ExecutionException {

		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}
