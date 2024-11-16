# Raq-Track
Final Edition of the wallet expense application
This ReadMe will help me reinforce and refresh my memory in creating this application.


'RaqTrack' was designed for users to manage their Finances, particularly, what they're spending their money on. I've affectionately named this after my son, and got the application idea from he Android mobile app "TrackWallet." 
There are 3 core principles that are keystones for user understanding:



Tracking Expenses - This system prompts the user to select what they would like to report through a dropdown menu, which provides various input options. Every time the user spends money, they can record this expense in the application. Each expense is categorized and defined by the amount spent.
For example, users can categorize their spending under labels such as Groceries, Bills, Rent, Clothing, or Entertainment. It is recommended that users group similar expenses under broader categories for easier tracking and summarization. For instance, instead of creating separate categories for Electric Bill, Phone Bill, and Internet Bill, users can group them all under a single category like "Bills."
This categorization becomes especially useful later in the application when generating reports. By organizing expenses this way, the system enables tracking of dated expenses and provides insights into monthly and yearly spending, giving users a broader perspective on their financial habits.


Setting Budgets - A late feature that was implemented into this application. This allows the application to tell a story on the users finances. Users can set a custom budget for their monthly and yearly spending. This can be simply based on ones salary earned throughout the month, or if they want to track a certain subset of their spending, such as miscellaneous, it is best to do this set at the end of setting your expenses, so you have an idea of what you've spend, and your spending can tell a story.


Report Generation- The simplest form of data saving. The application will push the user input for any of the last few menu options. These are saved in Budgets, Expenses and Categories. 
The review in the bottom will go over the essential classes inside the application that work together to make it functional.

Authenticator class

Validates login credentials (simple users.txt)
The class reads the file containing the usernames and passwords which are separated by a comma, to check if the user trying to log in is legitimate.
Although a very simple login variation, this is imminent as it verifies who can use the application, keeping the data safe. With further idea's I personally plan to implement a more modular file service integrated with the login class to make data more relevant to users, also a registration.

LoginGUI and Start class.

These are both the starting point of my application, as they both have the main method. They can be used interchangably, the LoginGUI shows a graphical Interface for the login, but reverts back to a console app. Before the app can be started, the user must login with their username or password, these are cehcked by the authenticator class. These can shwo a fully console based app, and or a GUI interface, Important as it makes the login process easy and secure for users.

FinanceBot class

This acts as the main control center, it will handle all application operation being the user menu. Once you've logged into the application, you will be show a list of menu options, from adding categories, setting budgets, entering yoru expenses, viewing the expenses and or generating reports. THis is vital as its the application itself, as a central hub which connects all the users to all other features and functions, allowing easy user navigation throughout.

Repository Class

This is our database, this is the storage place for memory, keeping track from categories expenses and budgets. This will temporarily store the data while the app is running while the user is adding category, expense and budgets, it will be held and saved with the repository instance. It will also give a rudimentary save and load data form files using file service, this is the simplest form I can come up with at the time, But it is the best to keep the data organized while the app is running, making setting expenses and budgets tot heir respective categories seamless.

FileService Class

This is what will be saving our user input data to the files (budgets.txt, expenses.txt, categories.txt), which will then be later read back into the app. It writes information from teh repository from their respective files, making sure data is available even after the app is closed. When the app is reopened it will read the data back into the repository. This is vital because its great for data persistence, and without it the users data would be lost each time the app is closed. The one thing I couldnt solve, is that when the app is closed with the data still in the files, It would show up a NULL value, so I managed to get parsing instance pulled and 


Budget Class

This will be the class which will tell a dated story on the users spending. It will manage and the monthly and yearly budget limits. The user can define a limit on how much they want to spend monthly or yearly. The user input value is then stores these values and checks if the user is staying within those limits. Its vital because it allows the users to control their spending by setting boundaries, and it makes it easier to avoid overspending after viewing all expenses, and properly providing an accurate detail on spending.


Category Class

This will populate your user inputed categorized expenses. It can create categories like Groceries, Entertainment to organize its expensives. each Expense will be tagged with a Category. This is important because it help organize data where the money is going, it makes it easier to adjust their spending habits, Categorization defines where the money is going, telling a really strong story.


Expense Class

This will represent individual expenses enetred by the user, and everytime the user inputs a new expense, this class will store the details such as the amount, date, category and the remark, which could be a small subset on what it is. Its vital for the expense application because it will track each and every expense, making it easy for the user to view edit or analyze their spending.


DateUtil Class 

This will be a core feature I've added. This will hadnle date formatting and conversion, it works when users enter dates or view reports, this class helps format dates in a cosistent way
which is a vital feature, as for the RaqTrack its important to understand when you are spending your money, and for obvious reasons it can give you a pattern. If one kept their receipts for a year, Im sure if you continually upload the data on the Application, it will tell a strong story for your day to day, and month to month spending. This makes the date handling across the app easily sortable to display expenses and budgets by date.


ReportService Class


This will calculate the totals and generate the Reports.

The class analyzes all the expenses and generates reports. It will show the total spending per month or per category, so this can help tell teh story of the users expenses, so they're sticking to their budget. This is important because these reports are the main feedback mechanism, helping users make informed financial decisions by reviewing their spending patterns


Here I will show you a simple flow on how these classes will interact.

1. Logging in
  The user will log in, using one of the main method classes, start app for a full console view, or LoginGui, with a starting graphical interface. The authenticator will work to check these user credentials from the users.txt file, If valid, they're directed to the main menu. If invalid, then the it will show a invalid username or password.

2. User navigating the RaqTrack App (FinanceBot)
   User interacts with the menu in FinanceBot choosign what they want to do, such as adding expenses, categories, or setting the budget. This is all indicative on the users choice, but it is reccommended to follow the onscreen order (1-11), as it can provide a strong story from the start of the app.
   
3. Repository (Data store)
  Everytime the user inputs their custom data, the repository instances will be called to hold it, this makes the access easy while the app is running, so the user can call upon, and or customize their data however they need.

4. FileService (Data persistence)
   When user exits the app (0) the fileservice will load all application data left in the repository to files, this will keep all data registered by the user. When the application is next open, the data will retain. Although it will come up as Uncategorized in This Beta version.

5. ReportService (Printing The Story)
  Users can set monthly and yearly budgets/expenses in Budget/Expense Class, and the ReportService class uses this stored data to check if spending is within budget limits, by creating monthly or yearly reports of the prescribed need. Overall this will tell a personal story to the user, and if they need to adjust their spending, or manage how their money works for them, then it will be evident and up to their need.


Summary:

With strong data management resources, using the repository and fileservice, the app effectively stores and saves data, making it easy for users to close and reopen the app without losing their information. It provides a strong foundation from managing your finances, as the Budget allows users to set financial limits, the Expense class allows user to input the actual expenses they would like to manage. All tying together the FinanceBot Class is our menu, it combines with the LoginGUI, and it manages a straightforward user experience. RaqTrack is a well structured, simple expense management tool which organizes spending data, helping users set limits, and gives meaningful feedback through reports. The collaboration of all these classes creates a seamless experience for users to log in, track spending set budgets, and view reprorts within one app. This makes it a practical tool for anyone who wants to manage their finances more effectively. 
   













