package pageObjects.common.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class Base {
	//all the test cases invocation occurs here
	public static WebDriver driver;
	public static Logger log;
	int pageTimeout;
	
	public Base() {
		log =LogManager.getLogger(Base.class.getName());
	}
	
	public WebDriver initializeWebDriver() throws IOException {
		String browserName = getConfigProperties("browser");
	
		//execute chrome or firefox browsers
		if(browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\java\\resources\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			if(browserName.contains("headless"))
				options.addArguments("headless");
			driver = new ChromeDriver(options); 
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			//Add firefox code
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		return driver;
	}
	
	public void closeBrowser() {
		getDriver().close();
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public WebElement getElement(By by) {
		return getDriver().findElement(by);
	}
	
	public List<WebElement> getElements(By by) {
		return getDriver().findElements(by);
	}
	
	public void launchUrl(String url) {
		try {
			initializeWebDriver();
		} catch (IOException e) {
			log.debug(e.getMessage(), "Failed to Initialize Web Driver");
		}
		log.info("Browser launched successfully");
		
		getDriver().get(url);
		log.info("Navigated ToDo MVC");
	}
	
	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot captureScreenshot = (TakesScreenshot) driver;
		File source = captureScreenshot.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
	
	public void genericWait(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			log.debug(e.getMessage());
		}
	}
	
	public String getConfigProperties(String testData){//fetch data from config properties file
		Properties prop = new Properties();
		try {
			prop.load( new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\config.properties"));
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage(), "Failed to load config properties file");
		} catch (IOException e) {
			log.debug(e.getMessage(), "Failed to get config properties values");
		}
		return prop.getProperty(testData);
	}
	
	public void scrollElementIntoViewViaJse(WebElement elt) {
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", elt);
	}
	
	public void scrollPageToTopViaJse() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0,0);", new Object[0]);
	}
	
	public void scrollPageToBottomViaJse() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0,document.body.scrollHeight);", new Object[0]);
	}
	
	public void scrollElementIntoView(WebElement elt) {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(elt);
		actions.perform();
	}
	
	public void clickElementViaJse(WebElement elt) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].focus();", new Object[] {elt});
		executor.executeScript("arguments[0].click();", new Object[] {elt});
	}
	
	public void setElementViaJse(WebElement elt, String input) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].setAttribute('value', '" + input +"')", elt);
	}
	
	public void refreshPage() {
		getDriver().navigate().refresh();
		log.info("Page successfully refreshed");
	}
	
	public void fluentWaitUntilVisible(By by){
		pageTimeout =  Integer.parseInt(getConfigProperties("pageTimeout"));
		FluentWait<WebDriver> wait = (new FluentWait<WebDriver>(getDriver()))
				.withTimeout(Duration.ofSeconds(pageTimeout))
				.pollingEvery(Duration.ofSeconds(2L))
				.ignoring(NoSuchElementException.class)
				.ignoring(WebDriverException.class)
				.ignoring(NoSuchWindowException.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void fluentWaitUntilVisible(WebElement elt){
		pageTimeout =  Integer.parseInt(getConfigProperties("pageTimeout"));
		FluentWait<WebDriver> wait = (new FluentWait<WebDriver>(getDriver()))
				.withTimeout(Duration.ofSeconds(pageTimeout))
				.pollingEvery(Duration.ofSeconds(2L))
				.ignoring(NoSuchElementException.class)
				.ignoring(WebDriverException.class)
				.ignoring(NoSuchWindowException.class);
		wait.until(ExpectedConditions.visibilityOf(elt));
	}
	
	public void fluentWaitUntilInvisible(By by){
		pageTimeout =  Integer.parseInt(getConfigProperties("pageTimeout"));
		FluentWait<WebDriver> wait = (new FluentWait<WebDriver>(getDriver()))
				.withTimeout(Duration.ofSeconds(pageTimeout))
				.pollingEvery(Duration.ofSeconds(2L))
				.ignoring(NoSuchElementException.class)
				.ignoring(WebDriverException.class)
				.ignoring(NoSuchWindowException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public boolean isElementDisplayed(By by) {
		try {
			List<WebElement> elements = getElements(by);
			if(elements.size() > 0) {
				if(!((WebElement)elements.get(0)).isDisplayed()) {
					log.debug("Element [" + by.toString() + "] exist but properties are not settled to be visible");
				}
				return true;
			}
		}catch(WebDriverException e) {
			log.debug(e.getMessage());
		}
		
		log.debug("Element [" + by.toString() + "] is not found");
		return false;
	}
	
	public boolean isElementDisplayed(By by, boolean waitUntilVisible) {
		if(waitUntilVisible) {
			try {
				this.fluentWaitUntilVisible(by);
			}catch(Exception e) {
				log.debug(e.getMessage());
			}
		}
		return this.isElementDisplayed(by);
	}
	
	public void actionDoubleClick(By by) {
		Actions action = new Actions(getDriver());
		WebElement elt = getElement(by);
		action.doubleClick(elt).perform();
	}
}
