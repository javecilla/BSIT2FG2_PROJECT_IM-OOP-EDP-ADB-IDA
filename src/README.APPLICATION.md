##  E-commerce Store

### Application Structure
  - `Java (GUI-jOption)` 
    The java is responsible for handling all server-side logic, database interactions. 
    It follows the Model-View-Controller (MVC) pattern, with a clean separation of concerns 
    thru Object Oriented Programming (OOP)


  - `Source Packages` - The core of the backend application.
    - `config` - Contains all configuration files for the application including database connection.
    - `enums` - Contains predefined constant values for use throughout the application.
    - `interfaces` - Contains overridable methods.
    - `exceptions` - Custom exception classes for handling errors.
    - `helpers` - Utility or helper methods that provide reusable code snippets.
    - `views` - Contains user interface (UI).
    - `controllers` - Contains controllers that handle user requests and return responses.
    - `models` - Entity models representing database tables..
    - `services` - Contains business logic that is more complex than what's in controllers.

# Development
The application is build thru collaboration develpment and deployment using Git version controll.
Can be access source code thru (https://github.com/javecilla/BSIT2FG2_PROJECT_G1).

## Prerequisites

- Install [Java JDK](https://www.java.com/download/ie_manual.jsp)
- Download NetBeans [IDE](https://netbeans.apache.org/) 
- Download UCanAccess plugins [DB Connectors](https://ucanaccess.sourceforge.net/site.html) for allow java connect to ms access database.

## Rules

Please read the repo **Standards** here: [README.STANDARDS.md](./README.STANDARDS.md)


# Teams
    Developer Teams:
    - Jerson Valdez [UI/UX] (https://github.com/Jerson-Valdez)
    - Jerome Avecilla [Business-Logic] (https://github.com/javecilla)

    Database Teams:
    - Pauline Caballero [Interview-Documentation]
    - Gillian Bandiola Sioco [ERD]
    - Raizen Acuña [EERD-RDM]
    - Francis Palma [User-Interface]
