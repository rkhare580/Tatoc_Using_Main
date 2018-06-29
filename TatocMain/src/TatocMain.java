import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TatocMain {

	public static void main(String[] args)
	{
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver","C:\\Users\\rohitkhare\\eclipse-workspace\\TatocMain\\Chrome\\chromedriver.exe");
		driver=new ChromeDriver();
		//Opening Tatoc
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.cssSelector("a[href='/tatoc/basic']")).click();
		
		//1.Grid Gate
		driver.findElement(By.cssSelector("div.greenbox")).click();
		
		//2.FrameDungeon
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#main")));
		String Box1=driver.findElement(By.cssSelector("div#answer")).getAttribute("class");
		driver.switchTo().frame(driver.findElement(By.id("child")));
		String Box2=driver.findElement(By.cssSelector("div#answer")).getAttribute("class");
		while(!(Box1.equals(Box2)))
		{
			driver.switchTo().defaultContent();
			driver.switchTo().frame(driver.findElement(By.id("main")));
			driver.findElement(By.cssSelector("a[onclick='reloadChildFrame();']")).click();
			driver.switchTo().frame(driver.findElement(By.id("child")));
			Box2=driver.findElement(By.cssSelector("div#answer")).getAttribute("class");
		}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("main")));
		driver.findElement(By.cssSelector("a[onclick='gonext();']")).click(); 
		
		//3.DragNDrop
		WebElement Frm=driver.findElement(By.cssSelector("div#dragbox"));
		WebElement To=driver.findElement(By.cssSelector("div#dropbox"));
		Actions DrgNDrp=new Actions(driver);
		DrgNDrp.dragAndDrop(Frm,To).build().perform();
		driver.findElement(By.cssSelector("a[href='#']")).click();
		
		//4.PopUpWindow
		driver.findElement(By.cssSelector("a[onclick='launchwindow();']")).click();
		ArrayList<String> tabs=new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.findElement(By.cssSelector("input#name")).sendKeys("Rohit");
		driver.findElement(By.cssSelector("input#submit")).click();
	    driver.switchTo().window(tabs.get(0));
		driver.findElement(By.cssSelector("a[onclick='gonext();']")).click();
		
		//5.CookieHandling

		driver.findElement(By.cssSelector("a[onclick='generateToken();']")).click();
		String Token=driver.findElement(By.cssSelector("span[id='token']")).getText();
		String Tokenvalue=Token.substring(7);
		Cookie cookie=new Cookie("Token",Tokenvalue);
		driver.manage().addCookie(cookie);
		driver.findElement(By.cssSelector("a[onclick='gonext();']")).click();
	}
}
