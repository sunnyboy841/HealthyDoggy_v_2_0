//SP：SharedPreferences  这是 Android 系统提供的一种轻量级数据存储方式，主要用于保存应用的配置信息、用户偏好设置等小型数据（以键值对形式存储在 XML 文件中）。
//Utils：Utilities 工具：表明是工具类
 //以上是本文件名解释

package com.example.healthydoggy.utils;  //包的生声明
import android.content.Context;
/*
包用法解析：

获取存储相关
getSharedPreferences(String name, int mode)：获取指定名称的SharedPreferences实例（用于轻量级存储），mode指定访问模式（如MODE_PRIVATE表示私有模式，仅当前应用可访问）。
getFilesDir()：获取应用私有文件目录的路径。
openFileOutput(String name, int mode)：打开应用私有文件输出流（用于写入文件）。

访问资源
getResources()：获取应用资源管理器（Resources对象），用于访问字符串、图片、布局等资源。
getString(int resId)：直接获取字符串资源（通过资源 ID）。
getDrawable(int resId)：获取图片资源（通过资源 ID）。

启动组件
startActivity(Intent intent)：启动新的Activity（界面）。
startService(Intent intent)：启动Service（后台服务）。

获取系统服务
getSystemService(String name)：获取系统服务（如通知服务NotificationManager、活动管理服务ActivityManager等）。

应用信息相关
getPackageName()：获取当前应用的包名。
getApplicationInfo()：获取应用的详细信息（如应用名称、图标等）。

*/

import android.content.SharedPreferences;
/*
SharedPreferences接口的常见函数
 SharedPreferences是 Android 提供的轻量级键值对存储接口，用于保存小量数据（如配置、状态），常见函数包括：

 获取编辑器（用于修改数据）
 edit()：返回SharedPreferences.Editor编辑器对象，通过它可以添加 / 修改 / 删除数据。

 读取数据
 getBoolean(String key, boolean defValue)：读取 boolean 类型数据，defValue是默认值（当键不存在时返回）。
 getString(String key, String defValue)：读取 String 类型数据。
 getInt(String key, int defValue)：读取 int 类型数据。
 类似的还有getLong()、getFloat()等，对应不同数据类型。

 其他辅助方法
 contains(String key)：判断是否包含某个键。
 registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)：注册监听器，监听数据变化。
 */


public class SPUtils {
    private static final String SP_NAME = "login_status";   //储存文件名称
    public static final String KEY_IS_LOGIN = "is_login";  //登陆状态：布尔值
    public static final String KEY_USERNAME = "username";   //用户名


    //封装并统一提供操作指定 SharedPreferences 文件的实例
    private static SharedPreferences getSP(Context context) {
        //SharedPreferences是用于操作键值对数据
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        //Context.MODE_PRIVATE：是Context类的系统常量，表示 “私有模式”—— 该文件仅当前应用可访问，其他应用无法读写。
    }


    //方法名：“保存登录状态”
    //持久化记录用户的登录信息，以便应用后续可以快速判断用户是否已登录、获取当前登录用户名。
    //在用户登录成功后，将 “登录状态” 和 “用户名” 保存到SharedPreferences中（即存储到login_status.xml文件中）
    public static void saveLoginStatus(Context context, String username) {

        SharedPreferences.Editor editor = getSP(context).edit();
        //// edit()是SharedPreferences接口提供的方法，用于获取编辑器对象
        editor.putBoolean(KEY_IS_LOGIN, true);
        // putBoolean()是Editor接口提供的方法，用于存储boolean类型数据
        editor.putString(KEY_USERNAME, username);
        // putString()是Editor接口提供的方法，用于存储String类型数据
        editor.apply();
        // apply()是Editor接口提供的方法，用于提交保存的数据（异步操作）

        /*
        除开上述方法外，还有 putLong()、putFloat() 等，对应不同基本数据类型。
        此外，它还提供 remove(String key)（删除指定键）、clear()（清空所有数据）等方法。
        */
    }


    //清除登陆状态（就是退出登录）
    public static void clearLoginStatus(Context context) {
        // 获取编辑器对象，edit()是SharedPreferences接口提供的方法
        SharedPreferences.Editor editor = getSP(context).edit();

        // clear()是Editor接口提供的方法，用于清除所有存储的数据
        editor.clear();

        // apply()是Editor接口提供的方法，用于提交操作（异步）
        editor.apply();
    }


   //获取用户名
    public static String getUsername(Context context) {
        // getString()是SharedPreferences接口提供的方法
        // 用于获取指定键对应的String值，第二个参数为默认值
        return getSP(context).getString(KEY_USERNAME, "匿名用户");
    }


    //判断是否已经登陆
    public static boolean isLogin(Context context) {
        // getBoolean()是SharedPreferences接口提供的方法
        // 用于获取指定键对应的boolean值，第二个参数为默认值
        return getSP(context).getBoolean(KEY_IS_LOGIN, false);
    }
}