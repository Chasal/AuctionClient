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
	// �������������е������ı���
	private EditText etName;
	private EditText etPass;

	// ��������е�������ť
	private Button bnLogin;
	private Button bnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// ��ȡ�����е������༭��
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		// ��ȡ�����е�������ť
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);

		// ΪbnCancel��ť�ĵ����¼����¼�������
		// bnCancel.setOnClickListener(new Home);

		bnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ִ������У��
				if (validate()) {
					// �����½�ɹ�
					if (loginPro()) {
						//����Main Activity
						Intent intent = new Intent(Login.this, MainActivity.class);
						startActivity(intent);
						
						//������Activity
						finish();
					}else{
						//�����Ի��򡣡���
						Log.i("TAG","�û����ƻ�������������������룡");
					}
				}
			}
		});
	}

	private  boolean loginPro() {
		//��ȡ�û���������
		String username = etName.getText().toString().trim();
		String pwd = etPass.getText().toString().trim();
		
		JSONObject jsonObject;
		try {
			jsonObject = query(username, pwd);
			//���userId����0
			if(jsonObject.getInt("userId") > 0){
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//�����Ի��򡣡�������
			
		} 
		return false;
	}

	/**
	 * ���û�������û������������У��
	 * 
	 * @return
	 */
	private boolean validate() {
		String username = etName.getText().toString().trim();
		if (username.equals("")) {
			// �����Ի���
			// ...
		}

		String pwd = etPass.getText().toString().trim();
		if (pwd.equals("")) {
			// �����Ի���
			// ...
		}

		return true;
	}
	
	/**
	 * ���巢������ķ���
	 * @param username
	 * @param password
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONObject query(String username, String password)
			throws JSONException, InterruptedException, ExecutionException {

		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}
