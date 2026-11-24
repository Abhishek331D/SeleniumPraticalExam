package Exam;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AmazonSortShoes {

	@Test
	public void verifyPriceLowToHigh() {
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.get("https://www.amazon.in");
		
		WebElement search = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
		wait.until(ExpectedConditions.elementToBeClickable(search));
		search.sendKeys("Shoes" + Keys.ENTER);
	
		WebElement sortDropdown = driver.findElement(By.cssSelector("span.a-dropdown-prompt"));
		wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
		sortDropdown.click();
		
		WebElement lowToHigh = driver.findElement(By.id("s-result-sort-select_1"));
		wait.until(ExpectedConditions.elementToBeClickable(lowToHigh));
		lowToHigh.click();
		
		wait.until(ExpectedConditions.urlContains("price-asc-rank"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-component-type='s-search-result']/descendant::span[@class='a-price-whole']")));
		
		List<WebElement> priceElements = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']/descendant::span[@class='a-price-whole']"));

		List<Integer> prices = new ArrayList<>();
		for (WebElement p : priceElements) {
			String text = p.getAttribute("textContent").replaceAll("[^0-9]", "");
			if (!text.isEmpty()) {
				prices.add(Integer.parseInt(text));
			}
		}
		System.out.println("Prices: " + prices);
		
		SoftAssert s1 = new SoftAssert();
		s1.assertTrue(prices.size() > 1, "Not enough prices found to compare");
		for (int i = 0; i < prices.size() - 1; i++) {
			s1.assertTrue(prices.get(i) <= prices.get(i + 1),
					"Not ascending at index " + i + ": " + prices.get(i) + " > " + prices.get(i + 1));
		}

		driver.quit(); 
		s1.assertAll();
	}
}