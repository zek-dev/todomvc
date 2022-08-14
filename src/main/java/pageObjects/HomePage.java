package pageObjects;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.google.common.primitives.Ints;

import pageObjects.common.base.Base;

public class HomePage extends Base{
	
	private By edtWhatNeedsToBeDone = By.className("new-todo");
	private By txtItemLeft = By.className("todo-count");
	private By txtToDoItems = By.xpath("//div[@class='view']//label");
	private By cbToDoItems = By.xpath("//input[@type='checkbox' and @class='toggle']");
	private By lnkAll = By.linkText("All");
	private By lnkActive = By.linkText("Active");
	private By lnkCompleted = By.linkText("Completed");
	private By eltToDoEditing = By.xpath("//li[@class='todo editing']");
	private By eltMarkAllToDoItem = By.xpath("//label[@for='toggle-all']");
	private By eltClearCompleted = By.className("clear-completed");
	
	public boolean isDisplayed() { return getElement(edtWhatNeedsToBeDone).isDisplayed(); }
	
	public void setWhatNeedsToBeDone(String toDo) {
		getElement(edtWhatNeedsToBeDone).click();
		getElement(edtWhatNeedsToBeDone).sendKeys(toDo);
		getElement(edtWhatNeedsToBeDone).sendKeys(Keys.ENTER);
	}
	
	public List<String> getToDoItems() {
		List<WebElement> elts = getElements(txtToDoItems);
		return elts.stream().map(WebElement::getText).collect(Collectors.toList());
	}
	
	public int getItemLeft() {
		fluentWaitUntilVisible(txtItemLeft);
		String itemLeft = getElement(txtItemLeft).getText().replaceAll(" item left", "");
		return Optional.ofNullable(itemLeft).map(Ints::tryParse).orElse(0);
	}
	
	public void clickToDoItem(String toDoItem) {
		List<WebElement> eltsTxt = getElements(txtToDoItems);
		List<WebElement> eltsCb = getElements(cbToDoItems);
		
		for (int i = 0; i < eltsTxt.size(); i++) {
			if(eltsTxt.get(i).getText().equalsIgnoreCase(toDoItem)) {
				eltsCb.get(i).click();
				eltsCb.get(i).isSelected();
			}
		}
	}
	
	public void clickAll() { getElement(lnkAll).click(); }
	
	public void clickActive() { getElement(lnkActive).click(); }
	
	public void clickCompleted() { getElement(lnkCompleted).click(); }
	
	public int getFilterItemCount() {
		List<WebElement> eltsTxt = getElements(txtToDoItems);
		return eltsTxt.size();
	}
	
	public void doubleClickAndClearToDoItem(String toDoItem) {
		List<WebElement> eltsTxt = getElements(txtToDoItems);
		for (int i = 0; i < eltsTxt.size(); i++) {
			if(eltsTxt.get(i).getText().equalsIgnoreCase(toDoItem)) {
				actionDoubleClick(txtToDoItems);
				fluentWaitUntilVisible(eltToDoEditing);
				Actions action = new Actions(getDriver());
				action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
				break;
			}
		}
	}
	
	public void setToDoItemOnEditField(String toDoItem) {
		List<WebElement> eltsTxt = getElements(txtToDoItems);
		for (int i = 0; i < eltsTxt.size(); i++) {
			if(eltsTxt.get(i).getText().isEmpty()) {
				Actions action = new Actions(getDriver());
				fluentWaitUntilVisible(eltToDoEditing);
				action.sendKeys(toDoItem).build().perform(); 
				action.sendKeys(Keys.ENTER).build().perform(); 
			}
		}
	}
	
	public void doubleClickAndDeleteWithAndWithoutMarkAsCompletedToDoItem(String toDoItem, String secondToDoItem) {
		List<WebElement> eltsTxt = getElements(txtToDoItems);
		for (int i = 0; i < eltsTxt.size(); i++) {
			String eltText = eltsTxt.get(i).getText();
			if(eltText.equalsIgnoreCase(toDoItem) || eltText.equalsIgnoreCase(secondToDoItem)) {
				actionDoubleClick(txtToDoItems);
				fluentWaitUntilVisible(eltToDoEditing);
				Actions action = new Actions(getDriver());
				action.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
				action.sendKeys(Keys.BACK_SPACE).sendKeys(Keys.ENTER).build().perform();
			}
		}
	}
	
	public void clickOrUnclickAllToDoItem() { getElement(eltMarkAllToDoItem).click(); }
	
	public void clickClearCompleted() { getElement(eltClearCompleted).click(); }
}
