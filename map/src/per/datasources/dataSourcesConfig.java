package per.datasources;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class dataSourcesConfig implements ServletContextListener{
	public static String picURI = "";
	public static String accessURI = "";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("-------CONFIG INIT START -----------");
		getProperties();
		System.out.println("-------CONFIG INIT END -----------");
	}
	
	
	
	/**
	 * 获取配置文件的信息
	 * 
	 * @since 2018.5.8
	 * @author Administrator
	 * 
	 * */
	private void getProperties() {
		Properties pro = new Properties();
		try {
			String str = URLDecoder.decode(getClass().getResource("/").getPath(), "UTF-8")+"/config.properties";
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(str));
			pro.load(in);
			picURI = pro.getProperty("PICURI");
			accessURI = pro.getProperty("ACCESSURI");
			System.out.println(picURI+"," +accessURI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
