# Challenge 1 - Compass Uol: Ecommerce


## The project:

This project aims to simulate a product registration and shopping cart system for an e-commerce platform, without using the Spring Framework. Despite not utilizing this tool, we strive to achieve a similar model to Spring MVC, with layers for repositories, services, controllers, and an additional layer for the console interface. The goal is to follow good architecture practices, clean code principles, and adherence to the S.O.L.I.D. principles.

The project aimed to experiment and apply the main concepts of OOP, such as inheritance, polymorphism, encapsulation, abstraction, and dependency injection, in addition to utilizing Java features like Stream, Enum and Generics.

The program connects to a PostgreSQL database using JDBC and has 5 tables: customers, employees, products, order_lines, and orders. The project offers CRUD functionality for products, customers, and employees, as well as the ability to add products to the shopping cart, view the cart, place an order, and calculate the final value.

As a bonus, the program includes authentication for registered customers, ensuring that their shopping cart is always available even when closing the application or switching customer logins. It is also possible to authenticate as an employee, with manager and salesperson permissions. Only the manager can perform CRUD operations with employees.


## Project Structure:


The project has several folders for better code organization. I will briefly describe each one:
- *libs (src/main/java/libs):* Contains the driver for connecting to PostgreSQL.
- *config (src/main/java/config):* Contains the class responsible for configuring and connecting to the database.
- *domain (src/main/java/domain):* Contains the record classes for objects that are not saved in the database. It includes the login class, which assists in authentication for employees and customers.

- *enums (src/main/java/enums):* Contains enums that help classify attributes with predefined values.

- *utils (src/main/java/utils):* Contains helper classes for user input, input validation, and text printing in the console.

- *exceptions (src/main/java/exceptions):* Contains custom exception classes specific to this program.

- *controllers (src/main/java/controllers):* Contains controller interfaces for user input and calls to the service layers. The impl subfolder (src/main/java/controllers/impl) contains the classes that implement the interfaces.

- *services (src/main/java/services):* Contains service interfaces that receive data from the controllers, manipulate object structures, apply business logic and prepare the object to be saved in the repository. The impl subfolder (src/main/java/services/impl) contains the classes that implement the interfaces.

- *repositories (src/main/java/repositories):* Contains repository interfaces responsible for receiving data from the services and executing SQL commands on the database to save objects. The impl subfolder (src/main/java/repositories/impl) contains the classes that implement the interfaces, with dependencies on the configuration class from the config package.

- *view (src/main/java/view):* Contains the interface responsible for the main menu method, presenting the available options to the user. The impl subfolder (src/main/java/view/impl) contains the class that implements the interface.

- *application (src/main/java/view/application):* Contains the class responsible for starting the application. It also performs dependency injection between the layers in the static main method.

## Running the Project:

### Importing the PostgreSQL database server:
- Install PostgreSQL 15 servers and PgAdmin 4 on your machine.
- After installation, open PgAdmin 4 and authenticate.
- Now, you will access the 'DB' folder in the project's root directory to import the server and make a backup.
- Go to 'Tools' -> 'Import/Export Servers'.
- Leave 'Import' selected. In the 'Data input' box, click on the folder icon on the left, browse to the 'DB' folder in the project's root directory, open it, select the 'server_database_ecommerce_json' file, and click 'Open'. Click 'Next'.
- Expand the 'Servers' options and select the checkbox for PostgreSQL 15 (or the available version). Click 'Next'.
- It will show you a summary of the operation. Click 'Confirm'.
- Now, you should see the imported servers on the left side. Click on it, and it will ask for a password to access the server.
- *****``` Enter this password in the input box and press Enter: 3b3G+X8@vd?=G0p. If necessary, enter the this user to continue: postgres ```*****
- Great! You have imported the server responsible for storing application data, including the 'Ecommerce' database. 
- **You can run the application without any saved data, but you will only be able to register new items if an employee with the role *'MANAGER'* is already registered in the database.** 
- Only then can you access the employee menu to add products, customers, employees, and allow these customers to use the shopping menu.
- *****```If you choose not to import the data, you can execute the following query in PostgreSQL to create an employee with the name 'ADMIN', register 'ADMIN', and role 'MANAGER': INSERT INTO employees (name, register, role) VALUES ('ADMIN', 'ADMIN', 'MANAGER'); ```*****

*If you encounter any difficulties, you can watch this video tutorial for assistance -> https://www.youtube.com/watch?v=rHjDW-_Et5g*

#### Importing data into the PostgreSQL database tables:
- Expand 'Databases', and you should now find the 'Ecommerce' database.
- Right-click on it and select 'Restore' from the expanded options.
- In the 'Format' section, select 'Custom or tar'.
- In the 'Filename' part, click on the folder icon on the left, browse to the DB folder in the project's root directory, open it, select the 'backup_database_ecommerce' file, and click 'Open'.
- If you can't see the file, at the bottom right of the file selection box, change the option from "Custom files" to "All files".
- Now click 'Restore' and wait for it to finish.
- After that, you should be able to view some data in the table. This data will be used for the program to function correctly.

*If you encounter any difficulties, you can watch this video tutorial for assistance -> https://www.youtube.com/watch?v=vdd66leSDa4*

*Please verify if all the steps have been followed correctly. If none of the alternatives solve the issue, try contacting via email: ianpf7@gmail.com*

### Running Java application:
- Install Java 18 or higher on your machine for the application to work.
- Install an IDE capable of running Java code in version 18 or higher, such as IntelliJ or Eclipse.
- Open the project in your preferred IDE and navigate to the src directory. Inside the src directory, go to the main directory, and then open the java directory.
- Here you should be able to see the project structure mentioned above.
- Expand the application package and click on the class inside it named Application.
- Inside this class, there is a main method that can run the entire application in the console.
- Click "Run".
- Now we will run the console application.

### Identifying PostgreSQL driver:
The current project has added Maven to facilitate the database connection. If you are unable to run it with the Maven dependencies, you can either update to the latest version or manually add the PostgreSQL driver to your IDE. The driver is located in *src/main/java/libs* with the name *postgresql-42.6.0.driverJDBC.jar*. After doing so, the application should run normally.

### Console:
- In the console, it will ask you to customize the menu style. Choose a character, type it, and press enter.
- After that, it will welcome you and you should choose between the customer menu, employee menu, or to exit the application.
- In the customer and employee menu options, authentication is required. Consult the database with the provided backups to access the menus.
- *****```To retrieve registered employees, execute the query in pg Admin: SELECT * FROM employees, and to retrieve customers, execute the query in pg Admin SELECT * FROM customers. These commands should provide you with tables of employees and customers, respectively.```*****
- After that, use the retrieved information to authenticate and test the program.

### Employee Menu:
- With employee authentication, you will be able to perform CRUD operations on products, customers, and even other employees if the employee has manager permission.

### Customer Menu:
- With customer authentication, you will be able to perform cart operations such as adding products, listing products, updating products, removing products, searching for products by name, clearing the cart, finalizing the order, searching for order lines by ID, searching for orders by ID, and viewing all orders. If not authenticated, you can register normally to continue to the menu and proceed with purchases.

## Interesting features of the program:

- It can authenticate registered customers and separate products in the cart for each individual customer, even if they don't finalize the order and close the application or switch logins. This is possible due to the OrderLine entity stored in the database, allowing this flexibility in the code.
- It can authenticate employees to perform CRUD operations and differentiate based on the employee's position to allow or disallow the CRUD operation on employees.
- It supports pagination for listed items, and you can customize the number of items displayed per page in the code. By default, it is set to 5 for customers, employees, and products, 10 for order lines, and 2 for orders. The program recognizes the number of pages required, whether it's the first page, middle pages, or the last page.
- It has personalized exception handling specific to the application, providing better reporting for the application manager and information for the user in case of any execution issues.
- Flow of the menu without using loop commands, only using recursion.





[Track the project's progress](https://trello.com/b/DeG0nY6c/projeto-ecommerce-compass-uol)

[Challenge description](https://legend-nightshade-bd0.notion.site/DESAFIO-1-266f087c9cfd4763b8470cfdd250f077)

---
---
---

# DESAFIO 1

A very important client asked us to create an E-commerce system for his company and that an MVP was initially shown so that they could follow the project in other steps.
For this he asked us for a specific structure and that we could act on top of it and bring good results.

Develop a shopping cart system in Java using MongoDB as a database. The system must have the following features:

**Product Management:**

Each product must have a name, price and quantity available in stock.
Products must be stored in the MongoDB database.
It must be possible to add, remove and update products.

- `id`: a unique identifier for the product (it can be a `String` or an `int`).
- `name`: the name of the product (a `String`).
- `price`: the price of the product (a `double`).
- `quantity`: the available quantity of the product (an `int`).

**Shopping cart:**

A customer can add products to the shopping cart.
The shopping cart must store the products selected by the customer, along with the quantity of each product.
The total value of the shopping cart must be calculated based on the selected products and quantities.

**Sales Confirmation:**

Before confirming the sale, the system must request a confirmation from the customer.
The customer must confirm the purchase before the sale is completed.
After confirmation, the inventory of sold products must be updated.

***Technical requirements:***

The project must be developed in Java.
OBS*- Use the official MongoDB API to interact with the database or use an SQL database, such as MySQL, to store the data.
Use the JDBC library to connect and interact with the database.
Organize code into classes and use good programming practices.
Implement methods to add, remove, and update products .
Implement methods to add products to the shopping cart and calculate the total cart value.
Implement a sales confirmation flow before completing the sale.

*START-*

To start the project , create a private repository on github and add your instructors and SM as collaborators .

The project will be evaluated on the following requirements:

1- Does your application meet the basic requirements?

2- Code organization.

3- Structure of the created components.

4- Operation of the MVP (place order, appear in the cart, final value of the cart based on the quantity of products and sales confirmation).
5- Did you document how to configure the environment and run your application?


