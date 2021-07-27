package com.bookmyshow.in;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/*
 *
 * Test suites with smoke and regression group
 *
 */
public class SportsSearchSuite extends Base {

	@BeforeTest
	public void launchBrowserAndLoadURL() {
		WebDriver driver = null;
		Properties prop = Base.setProperties();
		driver = Base.driverSetup("chrome");
		driver.get(prop.getProperty("search_url"));
		}
	@Test(priority=0,groups= {"Smoke"})
	public void searchSuite() throws InterruptedException, IOException {
		SportsSearch s1=new SportsSearch(driver);
		s1.alertHandle();
		Thread.sleep(3000);
		s1.signIn();
		Thread.sleep(4000);
		s1.sportsPage();
		
	}
	@Test(priority=1,groups= {"Regression"})	
	public void movieSearchSuite() throws InterruptedException, IOException {
		SportsSearch s2= new SportsSearch(driver);
		s2.moviesPage();
		
	}
	
	@AfterTest
	public void closeSuite() {
		driver.quit();
	}
}
