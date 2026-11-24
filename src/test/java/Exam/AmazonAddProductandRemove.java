package Exam;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AmazonAddProductandRemove {

	@Test
	public void productAddRemove() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.get("https://www.amazon.in");

		WebElement search = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
		wait.until(ExpectedConditions.elementToBeClickable(search));
		search.sendKeys("Notebook" + Keys.ENTER);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-component-type='s-search-result']/descendant::a[.//h2]")));
		List<WebElement> links = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']/descendant::a[.//h2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(links));
		String[] productUrls = { links.get(0).getAttribute("href"), links.get(1).getAttribute("href") };

		for (int i = 0; i < productUrls.length; i++) {
			driver.get(productUrls[i]);
			WebElement addtocart = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
			addtocart.click();
			wait.until(ExpectedConditions.textToBe(By.id("nav-cart-count"), String.valueOf(i + 1)));
		}

		WebElement cart = driver.findElement(By.id("nav-cart"));
		wait.until(ExpectedConditions.elementToBeClickable(cart));
		cart.click();

		List<WebElement>	cartItems = driver.findElements(By.cssSelector("fieldset[name='sc-quantity']"));
		wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));

		System.out.println("URL: " + driver.getCurrentUrl());
		int before = cartItems.size();
		System.out.println("Products in cart before remove: " + before);

		WebElement dec =driver.findElement(By.xpath("(//button[@data-a-selector='decrement'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(dec));
		dec.click();

		wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("fieldset[name='sc-quantity']"), 1));
	
		cartItems = driver.findElements(By.cssSelector("fieldset[name='sc-quantity']"));
		int after = cartItems.size();
		System.out.println("Products in cart after remove: " + after);

		SoftAssert s1 = new SoftAssert();
		s1.assertEquals(before, 2, "Cart should have 2 products before removing");
		s1.assertEquals(after, 1, "Cart should have only 1 product after removing");

		driver.quit();
		s1.assertAll();
	}
}