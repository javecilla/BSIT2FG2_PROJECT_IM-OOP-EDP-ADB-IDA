# E-commerce Store Documentation

## Overview
This Java-based E-commerce Store application serves as a comprehensive solution for managing products, categories, and user interactions within a graphical user interface (GUI) implemented with `JOptionPane` dialogs. Designed with both MVC (Model-View-Controller) and OOP (Object-Oriented Programming) principles, the application is structured to ensure a clear separation of concerns, maintainability, and scalability.

## Application Structure
The application is organized into logical packages, each serving a distinct purpose within the MVC and OOP paradigms.

- **Java (GUI - JOptionPane)**  
  The Java code is responsible for server-side logic, database interactions, and presentation through a GUI. It utilizes `JOptionPane` for user interactions, ensuring a straightforward and consistent user experience across the application. The MVC pattern is followed, ensuring clean separation between the user interface, business logic, and data handling.

- **Source Packages**  
  The core of the backend application is divided into the following packages:

  - **`config`**  
    Contains configuration files for the application, including database connection settings. It ensures that all essential configurations are maintained centrally for easier management.

  - **`enums`**  
    Stores predefined constant values, such as specific status codes or categories, which are used throughout the application to maintain consistency and readability.

  - **`interfaces`**  
    Houses all interface definitions, promoting polymorphism and abstraction. Interfaces define a common contract for various classes to implement, enhancing the application’s extensibility.

  - **`exceptions`**  
    Contains custom exception classes that handle specific application errors, allowing for more granular error control and improved error reporting to users.

  - **`helpers`**  
    A collection of utility or helper methods that provide reusable code snippets. These methods simplify common tasks across the application, such as data formatting or input validation.

  - **`views`**  
    This package contains the user interface (UI) logic and presentation elements. It handles displaying information to the user and capturing their inputs, following the MVC pattern’s View component.

  - **`controllers`**  
    Controllers manage the application's workflow by processing user requests, managing application logic, and returning responses. Each controller is responsible for interacting with the appropriate services and models to handle specific requests. This package serves as the “Controller” layer in MVC.

  - **`models`**  
    Models represent the application’s data structure, encapsulating database tables and providing a structure for managing data. Each model corresponds to a table and includes fields and methods to retrieve and manipulate data. This package is the “Model” component in MVC, promoting encapsulation.

  - **`services`**  
    The Service Layer is responsible for handling complex business logic that goes beyond the responsibilities of controllers. It abstracts the business processes, which allows the controllers to focus on managing user input/output. This package supports the principles of abstraction and inheritance, offering reusable logic that various controllers can leverage.

## Development Process
The application was built collaboratively using Git version control, allowing multiple developers to work on different parts of the project simultaneously. Code contributions, issues, and updates can be tracked in the project's GitHub repository: [BSIT2FG2_PROJECT_G1 Repository](https://github.com/javecilla/BSIT2FG2_PROJECT_G1).

## Prerequisites

To run and develop the application, please ensure the following tools are installed:

- [Java JDK](https://www.java.com/download/ie_manual.jsp) - The Java Development Kit is essential for compiling and running the application.
- [NetBeans IDE](https://netbeans.apache.org/) - This IDE facilitates Java development and provides tools for managing projects, dependencies, and GUI design.
- [UCanAccess](https://ucanaccess.sourceforge.net/site.html) - A JDBC driver that allows Java applications to connect to Microsoft Access databases, enabling the application to perform database operations.

## Coding Standards
To ensure consistency, please read the **Standards** in the repository: [README.STANDARDS.md](./README.STANDARDS.md).

## Teams

The application was developed through the collaboration of two main teams: **Development** and **Database**.

### Developer Team
- **Jerson Valdez**  
  - Role: UI/UX Design  
  - GitHub: [Jerson-Valdez](https://github.com/Jerson-Valdez)  

- **Jerome Avecilla**  
  - Role: Business Logic Development  
  - GitHub: [javecilla](https://github.com/javecilla)  

### Database Team
- **Pauline Caballero**  
  - Role: Interview and Documentation  

- **Gillian Bandiola Sioco**  
  - Role: Entity Relationship Diagram (ERD) Design  

- **Raizen Acuña**  
  - Role: Enhanced Entity-Relationship Diagram (EERD) and Relational Data Model (RDM)  

- **Francis Palma**  
  - Role: User Interface Development  

## Summary
The E-commerce Store application is a feature-rich project that integrates essential components of e-commerce functionality within a clean and maintainable structure. By adopting the MVC pattern alongside OOP principles, the application is both flexible and robust, suitable for future scaling and enhancement.
