package tw.jiangsir.Utils.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.TreeMap;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import tw.jiangsir.Utils.Annotations.Property;
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Exceptions.Cause;
import tw.jiangsir.Utils.Exceptions.DataException;

public class ConfigHandler {
	// private static File appRoot;
	// private static File AppConfigFile = new
	// File(ApplicationScope.getAppRoot()
	// + "/WEB-INF/", "AppConfig.xml");
	// private static AppConfig appConfig = null;
	// private static ObjectMapper mapper = new ObjectMapper(); // can reuse,
	// share
	// // globally
	// private static HashMap<String, Field> propertyfields = new
	// HashMap<String, Field>();
	//
	// public static File getAppConfigFile() {
	// return AppConfigFile;
	// }
	//
	// /**
	// * 獲取應用程式參數。
	// *
	// * @return
	// */
	// public static AppConfig getAppConfig(File appConfigFile) {
	// if (appConfig == null) {
	// appConfig = ConfigHandler.readAppConfig(appConfigFile);
	// }
	// return appConfig;
	// }
	//
	// /**
	// * 獲取應用程式參數。
	// *
	// * @return
	// */
	// // 20140915
	// // public static AppConfig getAppConfig() {
	// // if (appConfig == null) {
	// // appConfig = ConfigHandler.readAppConfig(AppConfigFile);
	// // }
	// // return appConfig;
	// // }
	//
	// /**
	// * 取得某個 class 內某個 Persistent name 的 Field
	// *
	// * @param theclass
	// * @return
	// */
	// private static Field getPropertyField(String propertykey) {
	// return getPropertyFields().get(propertykey);
	// }
	//
	// private static HashMap<String, Field> getPropertyFields() {
	// if (propertyfields.size() == 0) {
	// for (Field field : AppConfig.class.getDeclaredFields()) {
	// Property property = field.getAnnotation(Property.class);
	// if (property != null) {
	// propertyfields.put(property.key(), field);
	// }
	// }
	// }
	// return propertyfields;
	// }
	//
	// /**
	// * 從設定檔讀取出來，放入 AppConfig 當中。
	// */
	// private static AppConfig readAppConfig(File appConfigFile) {
	// AppConfig appConfig = new AppConfig();
	// Properties props = new Properties();
	// FileInputStream fis = null;
	// try {
	// fis = new FileInputStream(appConfigFile);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// try {
	// props.loadFromXML(fis);
	// } catch (InvalidPropertiesFormatException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// for (Field field : AppConfig.class.getDeclaredFields()) {
	// Property property = field.getAnnotation(Property.class);
	// if (property == null) {
	// // logger.info("field=" + field.getName() + " 並未設定到設定檔內。");
	// continue;
	// }
	// // for (Object key : props.keySet()) {
	// // Field field = getPropertyField((String) key);
	// // if (field == null) {
	// // logger.info("設定當 property key=" + key + " 找不到相對應的 Field");
	// // continue;
	// // }
	//
	// Method method;
	// try {
	// method = appConfig.getClass().getMethod(
	// "set" + field.getName().toUpperCase().substring(0, 1)
	// + field.getName().substring(1),
	// new Class[] { String.class });
	// method.invoke(appConfig,
	// new Object[] { props.getProperty(property.key()) });
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e));
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e));
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e));
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e));
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e.getTargetException()));
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new DataException(new Cause(e));
	// }
	// }
	// return appConfig;
	// }
	//
	// /**
	// * 將 AppConfig object 當中的設定寫入 AppConfig.xml 檔案當中。
	// *
	// * @param appConfig
	// */
	// public static void writeAppConfig(AppConfig appConfig) {
	// Properties props = new Properties();
	// props.clear();
	// for (Field propertyfield : getPropertyFields().values()) {
	// String propertykey = propertyfield.getAnnotation(Property.class)
	// .key();
	// String name = propertyfield.getName().toUpperCase()
	// .subSequence(0, 1)
	// + propertyfield.getName().substring(1);
	// Method getter;
	// try {
	// if (propertyfield.getType() == boolean.class
	// || propertyfield.getType() == Boolean.class) {
	// getter = appConfig.getClass().getMethod("is" + name);
	// } else {
	// getter = appConfig.getClass().getMethod("get" + name);
	// }
	// Object gettervalue = getter.invoke(appConfig);
	// if (getter.getReturnType() == String.class
	// || getter.getReturnType() == int.class
	// || getter.getReturnType() == double.class
	// || getter.getReturnType() == boolean.class
	// || getter.getReturnType() == Time.class) {
	// System.out.println("key=" + propertykey);
	// System.out.println("value="
	// + (gettervalue == null ? "null" : gettervalue
	// .toString()));
	// props.setProperty(
	// propertykey,
	// (gettervalue == null ? "null" : gettervalue
	// .toString()));
	// } else {
	// props.setProperty(propertykey,
	// mapper.writeValueAsString(gettervalue));
	// }
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (JsonGenerationException e) {
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// FileOutputStream fos;
	// try {
	// fos = new FileOutputStream(ConfigHandler.AppConfigFile);
	// props.storeToXML(fos, "應用程式參數");
	// fos.close();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 取得系統提供的 properties
	// *
	// * @return
	// */
	// public static TreeMap<String, String> getSystemProperties() {
	// TreeMap<String, String> list = new TreeMap<String, String>();
	// Enumeration<?> enumeration = System.getProperties().keys();
	// while (enumeration.hasMoreElements()) {
	// // HashMap map = new HashMap();
	// String name = enumeration.nextElement().toString();
	// // map.put(name, System.getProperty(name));
	// // list.add(map);
	// list.put(name, System.getProperty(name));
	// }
	// return list;
	// }
	//
	public static File getAppRoot() {
		return ApplicationScope.getAppRoot();
	}

	public static File getMETA_INF() {
		return new File(ApplicationScope.getAppRoot() + File.separator
				+ "META-INF");
	}

	public static File getWebxml() {
		return new File(ApplicationScope.getAppRoot() + File.separator
				+ "WEB-INF", "web.xml");
	}

}
