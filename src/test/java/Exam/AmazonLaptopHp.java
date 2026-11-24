

package Exam;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AmazonLaptopHp {

	@Test
	public void LaunchAmazonWep() {
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.get("https://www.amazon.in");
		
		WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#twotabsearchtextbox")));
		search.sendKeys("Laptop" + Keys.ENTER);
		
		WebElement selectHPBrand = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='brandsRefinements']/descendant::span[text()='HP']/preceding-sibling::div/label/i")));
		selectHPBrand.click();
		
		WebElement select4stars = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='reviewsRefinements']/descendant::a[contains(@aria-label,'4 Stars')]")));
		select4stars.click();
		
		String parent = driver.getWindowHandle();
		WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@data-component-type='s-search-result']/descendant::img[contains(@class,'s-image')])[1]")));
		product.click();
		
		for (String h : driver.getWindowHandles()) {
			if (!h.equals(parent)) {
				driver.switchTo().window(h);
				break;
			}
		}
		
		WebElement HPLaptop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
		String actual = HPLaptop.getText();
		SoftAssert s1 = new SoftAssert();
		s1.assertTrue(actual.contains("HP"), "Title does not contain HP: " + actual);
		driver.quit(); 
		s1.assertAll();
	}
}