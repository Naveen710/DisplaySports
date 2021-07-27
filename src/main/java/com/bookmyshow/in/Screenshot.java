package com.bookmyshow.in;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

public class Screenshot extends Base{
	/**
	 * Reusable method to take screenshots
	 *
	 */
	public void screenShot(String strFileName) throws InterruptedException {
		 
		   //Create new object for Date
		   Date d=new Date();
		   String date= d.toString().replace(" ", "-").replace(":", "-");

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//String ScreenshotPath = "";

		try
		{
			FileHandler.copy(screenshot,new File(projectPath + "\\src\\test\\resources\\" +strFileName+ " "+date+".png"));
			//ScreenshotPath = screenshot.toString();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	

}
