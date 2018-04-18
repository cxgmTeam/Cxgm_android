package com.cxgm.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.data.entity.User;
import com.deanlib.ootb.utils.DLogUtils;

import java.util.ArrayList;

/**
 * 用户管理
 *
 * Created by dean on 2017/7/8.
 */

public class UserManager {

    private static UserManager um;

    public static User user;

    static Context context;

    static SharedPreferences sp;

    private UserManager(Context context){

        this.context = context;

        sp = context.getSharedPreferences("info", Context.MODE_PRIVATE| Context.MODE_MULTI_PROCESS);

        user = getUser();

    }

    /**
     * 删除本地用户
     */
    public static void deleteUser(){

        user = null;

        SharedPreferences.Editor edit = sp.edit();

        edit.remove("userinfo");

        edit.commit();

    }

    /**
     * 保存登录信息到本地
     * @param l
     */
//    public static void saveLogin(Login l){
//
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("logininfo", JSON.toJSONString(l, false));
//        edit.commit();
//
//    }

    /**
     * 获取本地保存的登录信息
     * @return
     */
//    private static Login getLogin(){
//        String loginJsonString = sp.getString("logininfo", "");
//        if (!TextUtils.isEmpty(loginJsonString)) {
//
//            return JSON.parseObject(loginJsonString, Login.class);
//        }
//        return null;
//    }

    /**
     * 保存用户到本地
     * @param u
     */
    public static void saveUser(User u){

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userinfo", JSON.toJSONString(u,false));
        edit.commit();

        //update
        user = getUser(true);

    }

    private static User getUser(){

        return getUser(false);
    }

    /**
     * 获得用户信息
     * @param isForceUpdate 是否强制更新（因为存在静态user变量,强制更新可以保证读取本地数据）
     * @return
     */
    private static User getUser(boolean isForceUpdate){

        try {

            if (user == null||isForceUpdate) {

                String userJsonString = sp.getString("userinfo", "");

                if (!TextUtils.isEmpty(userJsonString)) {
                    user = JSON.parseObject(userJsonString, User.class);
                }

                //得到登录信息，放到user中
                /*Login login = getLogin();

                if (user!=null&&login!=null){


                    user.token = login.token;

                    user.expiresIn = login.expiresIn;

                    user.currentLastLogin = login.currentLastLogin;


                }*/

            }

            DLogUtils.d("User:"+user);

            //判断登录过期
            /*if (user!=null&&System.currentTimeMillis() > (user.currentLastLogin + Long.parseLong(user.expiresIn))) {

                user = null;

                //Toast.makeText(context, R.string.login_expired, Toast.LENGTH_SHORT).show();

            }*/



        }catch (Exception e){

            e.printStackTrace();

            return null;
        }

//        if (user!=null) {
//            RequestParams params = App.userReqParam.getUserParams();
//            params.addHeader("token",user.token);
//            App.userReqParam.setUserParams(params);
//        }


        return user;
    }

    /**
     * 是否是登录状态
     * @return
     */
    public static boolean isUserLogin(){

        return getUser()!=null;
    }

    /**
     * 是否可更改值守模式
     * 5分钟
     * @return
     */
    public static boolean canChangeDoorStatus(){

        getUser();

        return System.currentTimeMillis()>(user.doorStatusTime+5*60*1000);
    }


    public static UserManager getInstance(Context context){

        if(um==null){

            um = new UserManager(context);

        }

        return um;

    }


    public static ArrayList<OnUserActionListener> mListenerList = new ArrayList<OnUserActionListener>();

    public static void setOnUserActionListener(OnUserActionListener listener){

        if(!mListenerList.contains(listener))

            mListenerList.add(listener);

    }

    public static void removeOnUserActionListener(OnUserActionListener listener){
        mListenerList.remove(listener);
    }

    public interface OnUserActionListener{

        void onLogin(User user);

        void onLogout();

    }

}
