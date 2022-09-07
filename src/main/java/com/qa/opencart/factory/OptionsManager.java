package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	public OptionsManager(Properties prop) {
		this.prop = prop;

	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))

		{
			co.setHeadless(true);
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote")))
		{
			co.setCapability("enableVNC", true);
//			co.setCapability("se:screenResolution", "1920x1080");
//			co.setPlatformName("linux");
			co.setBrowserVersion(prop.getProperty("browserversion"));
		}

		return co;

	}

	public FirefoxOptions getFireFoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))

		{
			fo.setHeadless(true);
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote")))
		{
			fo.setCapability("enableVNC", true);
//			fo.setCapability("se:screenResolution", "1920x1080");
//			fo.setPlatformName("linux");
			fo.setBrowserVersion(prop.getProperty("browserversion"));
		}

		return fo;

	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))

		{
			eo.setHeadless(true);
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			eo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote")))
		{
			eo.setCapability("enableVNC", true);
			eo.setCapability("se:screenResolution", "1920x1080");
			eo.setPlatformName("linux");
		}

		return eo;

	}

}
