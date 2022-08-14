package stepDefinitions;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.common.base.Base;

public class HomeSteps{
	
	HomePage homePage = new HomePage();
	Base base = new Base();
	
	@Given("^the user Launch ToDo MVC application$")
	public void userLaunchToDoMvc() {
		homePage.launchUrl(homePage.getConfigProperties("toDoMvcUrl"));
		homePage.isDisplayed();
	}
	
	@When("^the user add ToDo item as (.+) and (.+)$")
	public void userAddToDoItem(String toDoitem, String secondToDoItem) {
		homePage.setWhatNeedsToBeDone(toDoitem);
		homePage.setWhatNeedsToBeDone(secondToDoItem);
	}
	
	@Then("^the user able to add ToDo item and mark as completed$")
	public void userAbleTOAddToDoItemAndMarkAsCompleted() {
		assertTrue(homePage.getItemLeft() > 0, "ToDo item is not added");
		assertTrue(homePage.getItemLeft() == 1, "ToDo item is not mark as completed");
	}
	
	@And("^the user mark as completed (.+) item$")
	public void userClickToDoItem(String toDoitem) { homePage.clickToDoItem(toDoitem); }
	
	@And("^the user close the browser$")
	public void userCloseBrowser() { base.closeBrowser(); }
	
	@And("^the user filter by All$")
	public void userFilterByAll() { homePage.clickAll(); }
	
	@When("^the user filter by Active$")
	public void userFilterByActive() { homePage.clickActive(); }
	
	@When("^the user filter by Completed$")
	public void userFilterByCompleted() { homePage.clickCompleted(); }
	
	@Then("^the user able to filter by All$")
	public void userAbleToFilterByAll() { assertTrue(homePage.getFilterItemCount() == 2, "All ToDo items not filtered"); }
	
	@Then("^the user able to filter by Active$")
	public void userAbleToFilterByActive() { assertTrue(homePage.getFilterItemCount() == 1, "Active ToDo items not filtered"); }
	
	@Then("^the user able to filter by Completed$")
	public void userAbleToFilterByCompleted() { assertTrue(homePage.getFilterItemCount() == 1, "Completed ToDo items not filtered"); }
	
	@And("^the user edit from (.+) to (.+)$")
	public void userEditWithoutMarkAsCompletedToDoItem(String toDoItem, String newToDoItem) {
		homePage.doubleClickAndClearToDoItem(toDoItem);
		homePage.setToDoItemOnEditField(newToDoItem);
	}
	
	@Then("^the user able to edit to (.+)$")
	public void userAbleToEditToDoItem(String newToDoItem) { assertTrue(homePage.getToDoItems().contains(newToDoItem), "Unable to edit the ToDo item"); }
	
	@And("^the user delete with/without mark as completed (.+) and (.+)$")
	public void userDeleteWithAndWithoutMarkAsCompletedToDoItem(String toDoItem, String secondToDoItem) {
		homePage.doubleClickAndDeleteWithAndWithoutMarkAsCompletedToDoItem(toDoItem, secondToDoItem);
	}
	
	@Then("^the user able to delete with/without mark as completed ToDo item$")
	public void userAbleToDeleteWithAndWithoutMarkAsCompletedToDoItem() { assertTrue(homePage.getItemLeft() == 0, "ToDo item has not been deleted"); }
	
	@And("^the user unmark, mark and clear all completed ToDo item$")
	public void userMarkAndClearAllCompletedToDoItem() {
		homePage.clickOrUnclickAllToDoItem();
		homePage.clickOrUnclickAllToDoItem();
		homePage.clickOrUnclickAllToDoItem();
		assertTrue(homePage.getItemLeft() == 0, "All ToDo items is not marked");
		homePage.clickClearCompleted();
	}
	
	@Then("^the user able to mark and clear all completed ToDo item$")
	public void userAbleToMarkAndDeleteAllCompletedToDoItem() { assertTrue(homePage.getItemLeft() == 0, "All ToDo items are not cleared"); }
}
