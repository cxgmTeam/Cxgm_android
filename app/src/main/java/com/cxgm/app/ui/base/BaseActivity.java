package com.cxgm.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxgm.app.data.entity.User;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.manager.NetworkManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.xutils.x;

/**
 * 
 * @author dean
 * @date 2016年4月26日
 */
public class BaseActivity extends AppCompatActivity implements UserManager.OnUserActionListener, NetworkManager.NetworkListener {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		x.view().inject(this);
		UserManager.setOnUserActionListener(this);

		// 设置网络状态监听器
		NetworkManager.getInstance().addOnNetworkListener(this);

		//推送统计应用启动数据  统计分析sdk中统计日活的方法无关
		PushAgent.getInstance(this).onAppStart();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetworkManager.getInstance().removeNetworkListener(this);
		UserManager.removeOnUserActionListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

//		cancelAllRequestCancelable();

		MobclickAgent.onPause(this);
	}

	@Override
	public void onLogin(User user) {

	}

	@Override
	public void onLogout() {

	}

	@Override
	public void onNetworkDisconnect() {

	}

	@Override
	public void onNetworkConnected(int type) {

	}

	public interface OnClickAppbarListener{

		void onClick(View v);

	}


//	ArrayList<RequestCancelable> mRequestCancelableList;//页面网络请求的句柄
//
//
//	public void addRequestCancelable(RequestCancelable requestCancelable){
//
//		if(mRequestCancelableList==null&&requestCancelable!=null){
//
//			mRequestCancelableList = new ArrayList<RequestCancelable>();
//
//			mRequestCancelableList.add(requestCancelable);
//
//		}
//
//	}
//
//	public boolean removeRequestCancelable(RequestCancelable requestCancelable){
//
//		if(mRequestCancelableList!=null&&requestCancelable!=null){
//
//			requestCancelable.cancelable.cancel();
//
//			return mRequestCancelableList.remove(requestCancelable);
//
//		}
//
//		return false;
//
//	}
//
//	public void cancelAllRequestCancelable(){
//
//		if(mRequestCancelableList!=null){
//
//			for (RequestCancelable requestCancelable : mRequestCancelableList) {
//
//				requestCancelable.cancelable.cancel();
//
//			}
//
//			mRequestCancelableList.clear();
//
//		}
//
//	}
}
