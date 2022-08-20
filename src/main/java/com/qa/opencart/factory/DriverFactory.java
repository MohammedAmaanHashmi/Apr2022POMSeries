package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * this method is used to initialize the driver on the basis of given
 * browserName.
 * 
 * @param browserName
 * @return this returns driver
 *
 */
public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		System.out.println("browser name is: " + browserName);
		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		} else {
			System.out.println("please pass the right browser name " + browserName);
		}

		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this returns properties reference with all config properties
	 * 
	 * @return this returns properties
	 */

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		// command line args---> maven
		// mvn clean install--->Denv="qa" -Dbrowser="chrome"
		// mvn clean install

		String envName = System.getProperty("env");
		System.out.println("Running test cases on environment: " + envName);

		if (envName == null) {
			System.out.println("No env is given.....hence running it on QA env by default......");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		}

		else {
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");

					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");

					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/prod.config.propoerties");

					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.propoerties");

					break;

				default:
					System.out.println("please pass the right environment....+" + envName);
					break;
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}
		try {
			prop.load(ip);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return prop;
	}
	/*
	 * take screenshot
	 */

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir")+"./screenshot/" + methodName + ".png";

		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return path;
	}

}
