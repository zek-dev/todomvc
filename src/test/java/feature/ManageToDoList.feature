Feature: User manage ToDo list

Scenario Outline: Add and mark as completed the ToDo item
Given the user Launch ToDo MVC application
When the user add ToDo item as <toDoItem> and <secondToDoItem>
And the user mark as completed <secondToDoItem> item
Then the user able to add ToDo item and mark as completed
And the user close the browser

Examples:
	| toDoItem  | secondToDoItem |										  
	| Cut Grass | Cut Plants	 |
	
	
Scenario Outline: Filter by All, Active and Completed
Given the user Launch ToDo MVC application
When the user add ToDo item as <toDoItem> and <secondToDoItem>
And the user mark as completed <secondToDoItem> item
And the user filter by All
Then the user able to filter by All
When the user filter by Active
Then the user able to filter by Active
When the user filter by Completed
Then the user able to filter by Completed
And the user close the browser

Examples:
	| toDoItem  | secondToDoItem |										  
	| Cut Grass | Cut Plants	 |

Scenario Outline: Edit ToDo item
Given the user Launch ToDo MVC application
When the user add ToDo item as <toDoItem> and <secondToDoItem>
And the user edit from <toDoItem> to <newToDoItem>
Then the user able to edit to <newToDoItem>
And the user close the browser

Examples:
	| toDoItem  | secondToDoItem | newToDoItem |							  
	| Cut Grass | Cut Plants	 | Cut Tree	   |

Scenario Outline: Delete with/without mark as completed ToDo item individually
Given the user Launch ToDo MVC application
When the user add ToDo item as <toDoItem> and <secondToDoItem>
And the user delete with/without mark as completed <toDoItem> and <secondToDoItem>
Then the user able to delete with/without mark as completed ToDo item
And the user close the browser

Examples:
	| toDoItem  | secondToDoItem |									  
	| Cut Grass | Cut Plants	 |
	
Scenario Outline: Mark and clear all completed ToDo item
Given the user Launch ToDo MVC application
When the user add ToDo item as <toDoItem> and <secondToDoItem>
And the user unmark, mark and clear all completed ToDo item
Then the user able to mark and clear all completed ToDo item
And the user close the browser

Examples:
	| toDoItem  | secondToDoItem |									  
	| Cut Grass | Cut Plants	 |	
	