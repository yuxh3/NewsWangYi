package com.example.admin.newswangyi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceTools {
	
	private static final String SP_NAME = "config";
	private static SharedPreferences sp;
	//保存布尔值
	public static void saveBoolean (Context context,String key,boolean value){
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	
	public static boolean getBoolean(Context context,String key,boolean defValue){
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		boolean result = sp.getBoolean(key, defValue);
		return result;
	}
	
	//保存字符串
	public static void saveString (Context context,String key,String value){
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	
	public static String getString(Context context,String key,String defValue){
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		String result = sp.getString(key, defValue);
		return result;
	}
}
