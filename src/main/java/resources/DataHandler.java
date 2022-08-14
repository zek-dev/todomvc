package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import pageObjects.common.base.Base;

public class DataHandler extends Base{

	public String getTestProperties(String testData){//fetch data from test properties file
		Properties prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\test.properties");
		
		try {
			FileInputStream fis = new FileInputStream(file);
			prop.load(fis);
		} catch (Exception e) {
			log.debug(e.getMessage(), "Failed to fetch data from test properties file");
			
		}
		
		return prop.getProperty(testData);
	}
	
	public void setTestProperties(String key, String value) {//set key and value data to test properties file
		Properties prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\test.properties");
		
		try {
			prop.load(new FileInputStream(file));
		} catch (Exception e) {
			log.debug(e.getMessage(), "Failed to fetch data from test properties file");
		} 
		
		if(prop.containsKey(key)) {
			prop.remove(key);
		}
		prop.setProperty(key, value);
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			prop.store(fos, "");
		} catch (Exception e) {
			log.debug(e.getMessage(), "Failed to set data in test properties file");
		}
	}
}
