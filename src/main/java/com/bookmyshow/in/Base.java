package com.bookmyshow.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;


public class Base {
	static String projectPath = System.getProperty("user.dir");
	static WebDriver driver = null;
	/**
	 *Launching the desired browser
	 */
 public static WebDriver driverSetup(String browser) {
		if (browser.equalsIgnoreCase("chrome"))
		{

			System.setProperty("webdriver.chrome.driver",projectPath+"\\Drivers\\chromedriver.exe");
			ChromeOptions opt=new ChromeOptions();
			opt.addArguments("--disable-notifications");
			driver = new ChromeDriver(opt);
		}

		else if (browser.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", projectPath + "\\Drivers\\geckodriver.exe");
			FirefoxOptions opt1=new FirefoxOptions();
			opt1.addPreference("dom.webnotifications.enabled",false);
			driver = new FirefoxDriver(opt1);

		} else {
			Reporter.log("Browsers Not Found");
			System.out.println("Browsers not found");

		}

		driver.manage().window().maximize();

		return driver;
	}
 
	/**
	 * Inputs from the Properties file
	 */
	public static Properties setProperties() {

		Properties p = null;
		FileInputStream file = null;

		p = new Properties();

		try {
			file = new FileInputStream(projectPath + "\\InputFiles.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			p.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return p;
	}
	
	/**
	 * Reusable method to take screenshots
	 *
	 */
	public void screenShot(String strFileName) throws InterruptedException {

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//String ScreenshotPath = "";

		try {
			FileHandler.copy(screenshot,
					new File(projectPath + "\\src\\test\\resources\\" + strFileName + ".png"));
			//ScreenshotPath = screenshot.toString();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 *Method to write the data into excel sheet
	 */
	public static void writedata(List<WebElement> Lang) throws IOException, InterruptedException  {
		
	    FileInputStream fis = new FileInputStream(projectPath + "\\Writexl\\hackie.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("OUTPUT");
	    XSSFRow row;
		int size=Lang.size();
		for(int i=1;i<=size;i++) {
			row=sheet.createRow(i-1);
			row.createCell(i-1).setCellValue(Lang.get(i-1).getText());
		}
		 fis.close();
		FileOutputStream outputFile=new FileOutputStream(projectPath + "\\Writexl\\hackie.xlsx");
		workbook.write(outputFile);
		outputFile.close();
		
		
		
		
	}
	
	
	
	
	
	/**
	 * method to close the browser
	 *
	 */

	public static void closebrowser() {
		driver.quit();
	}
	
}
