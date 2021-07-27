package com.bookmyshow.in;

import java.io.IOException;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

/**
 * 
 * Test methods created in page object model,performs the actions like sign in
 * alert handle ,searching , storing in excel(POI), reporting the output
 */

public class SportsSearch extends Base {

	WebDriver driver;
	Properties prop = Base.setProperties();
	
	
	By mailid = By.name(prop.getProperty("mailid"));
	
	By error = By.className(prop.getProperty("error"));
	
	
	public By xpath(String element) {
		
		By xpath=By.xpath(prop.getProperty(element));
		return xpath;
	}

	public SportsSearch(WebDriver driver) {
		this.driver = driver;
	}

	public void alertHandle() throws InterruptedException {
		Thread.sleep(1000);
		
		driver.findElement(xpath("city")).click();
	}

	/**
	 * This method is used to check the signin functionality with wrong mailid,
	 * prints the error message Window handling is used
	 *
	 *
	 */
	public void signIn() throws InterruptedException {
		
		driver.findElement(xpath("signin")).click();
		Thread.sleep(10000);
		
		driver.findElement(xpath("google")).click();
		Thread.sleep(5000);
		
		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();

		while (i1.hasNext()) {
			String ChildWindow = i1.next();

			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {

				driver.switchTo().window(ChildWindow);
				String email = prop.getProperty("invalid_email");
				driver.findElement(mailid).sendKeys(email);
				Thread.sleep(3000);
				driver.findElement(xpath("next")).click();
				Thread.sleep(5000);
				screenShot("invalidMail");
				String msg = driver.findElement(error).getText();
				
				System.out.println("error message for invalid email:" +msg);
				Reporter.log("Error message for invalid email is:");
				Reporter.log(msg);
				driver.close();

			}
		}
		Thread.sleep(5000);
		driver.switchTo().window(MainWindow);
		// driver.findElement(closewindow).click();

	}

	/*
	 * This method is used to search the sports activities for coming weekend with
	 * lowest prices on top
	 *
	 */
	public void sportsPage() throws InterruptedException
	{
		driver.navigate().to(prop.getProperty("sports_url"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(xpath("sportsdate")).click();
		Thread.sleep(2000);
		driver.findElement(xpath("price")).click();
		driver.findElement(xpath("lowprice")).click();
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(5000);
		Reporter.log("Sports available for this weekend:");
		System.out.println("*********sports avalaible for coming weekend*********");
		List<WebElement> sports_title = driver.findElements(By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div[2]//a/div/div[3]")); 
	    
		HashMap<String, Integer> weekendGames = new HashMap<String, Integer>();
		for (WebElement sport : sports_title) {

			
			String name = sport.getText().substring(0, sport.getText().indexOf("\n")); // game name
			System.out.println("Game_Name:" + name);

			String price = sport.getText().substring(name.length()).replaceAll("[^0-9]", ""); // price
			System.out.println("price:" + price);

			weekendGames.put(name,Integer.parseInt(price)); // reading values into
			

		}
	

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		ArrayList<Integer> list = new ArrayList<>();

		for (Map.Entry<String,Integer> entry : weekendGames.entrySet()) {
			list.add(entry.getValue());
		}
		Collections.sort(list, new Comparator<Integer>() {
			public int compare(Integer str, Integer str1) {
				return (str).compareTo(str1);
			}
		});
		for (Integer str : list) {
			for (java.util.Map.Entry<String, Integer> entry : weekendGames.entrySet()) {
				if (entry.getValue().equals(str)) {
					sortedMap.put(entry.getKey(), str);
				}
			}
		}
		
		//System.out.println(sortedMap);
		
		System.out.println("Sorted games based on price(low to high)");
		 Set<String> keys = sortedMap.keySet();
	 int k =1;       
	        // printing the elements of LinkedHashMap
	        for (String key : keys) {
	            System.out.println("GAME:"+(k++)+":"+key +"   "+ "Price:"
	                               + sortedMap.get(key));
	        }
		
	}

	/**
	 * This method is used to verify all the filters in movies page, prints all the
	 * languages available
	 *
	 */
	public void moviesPage() throws InterruptedException, IOException {
		driver.navigate().to(prop.getProperty("movies_url"));
		driver.findElement(By.xpath("//*[@id=\"super-container\"]/div[2]/div[4]/div[2]/div[3]/div/div/div[2]/a/div/div[2]/div/img")).click();
	    Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,400)");
		screenShot("mLanguages");
		Reporter.log("All available languages are:");
		System.out.println("********Movie languages are*********");
		
		List<WebElement> Languages = driver.findElements(xpath("langlist"));
		for (WebElement Language : Languages) {
			String movieslanguage= Language.getText();
			System.out.println(movieslanguage);
		}
		Base.writedata(Languages);
		Thread.sleep(3000);
		driver.findElement(xpath("genre")).click();
		driver.findElement(xpath("thriller")).click();
		Thread.sleep(3000);
		js.executeScript("window.scrollBy(0,400)");
		Thread.sleep(3000);
		

	}

	
}