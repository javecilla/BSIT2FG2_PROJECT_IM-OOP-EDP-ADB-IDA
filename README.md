# Project Name: Mommy's Variety Store Management System

## Overview
Mommy's Variety Store Management System is a desktop application built with Java Swing for the graphical user interface (GUI) and Microsoft SQL Server as the primary database. 
The application is designed to manage inventory, categories, and other store-related operations. 
It also supports a legacy connection to Microsoft Access for backward compatibility with earlier database versions. 
This project is developed as part of a BSIT (Bachelor of Science in Information Technology) course requirement.

## Features
This project combines foundational features from its initial development with new enhancements. Below is an overview of both:
- [Old features from 2nd Year-1st Semester](./docs/README-IM-OOP.md)
  - Core functionality developed during the Information Management (IM) and Object-Oriented Programming (OOP) phases.
  - Refer to the linked documentation for a detailed list.
- [Old features from 2nd Year-2nd Semester](./docs/README-EDP-ADB-IDA.md)
  - Advanced features introduced in the Enterprise Data Processing (EDP), Advanced Database (ADB), and Integrated Data Analytics (IDA) phases.
  - Includes support for MS SQL Server, improved GUI, and more—see the linked documentation for specifics.

For a complete breakdown of all features: Explore the for the original feature set and Check the for the latest additions.

## Technologies Used
- **Java**: Core programming language (JDK 23).
- **Java Swing**: GUI framework for the desktop interface.
- **MS SQL Server**: Primary database for storing application data.
- **MS Access**: Legacy database support via UCanAccess JDBC driver.
- **JDBC**: Java Database Connectivity for database interactions.
- **NetBeans**: Core programming language (JDK 23).
- **Java**: IDE used for development (version 24 or compatible).

## Prerequisites
Before setting up the project, ensure you have the following installed:
- **Java Development Kit (JDK)**: Version 23 or later. [Download](https://www.oracle.com/ph/java/technologies/downloads/)
- **NetBeans IDE**: Version 24 or compatible (optional, but recommended). [Download](https://netbeans.apache.org/front/main/download/)
- **MS SQL Server**: Express Edition. [Download](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
- **MS SQL Server JDBC Driver**: Download from [Microsoft](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16)
- **UCanAccess JDBC Driver**: For MS Access support, download from [UCanAccess](https://ucanaccess.sourceforge.net/site.html)
- **MS Access**: For MS Database Access (Optional)

## Project Installation and Setup
1. Install MS SQL Developer version
2. Install standalone server
3. Enable Login in SQL authentication
4. Download Microsoft SQL JDBC Driver
5. Move MS SQL auth.dll file to Java SDK
6. Enable TCP IP port for SQL Server
7. Import JAR file to protect library in java compiler
8. Configure Database connection with associated credentials.

## Project Structure
```
BSIT2FG2_PROJECT_IM-OOP-EDP-ADB-IDA/
├── src/
│   ├── config/                           # Configuration settings, including database connections, to streamline management.
│   ├── controllers/                      # Coordinates application workflows, handles requests, and processes logic as the Controller layer in MVC. Controllers interact with services and models to manage specific tasks.
│   ├── core/                             # Contains essential classes and foundational components that define the application's core functionality. These classes are not tied to any specific layer (e.g., MVC) but represent key abstractions, utilities, or domain-specific logic that other packages rely on. The core package ensures reusability and encapsulation of central features.
│   ├── enums/                            # Stores predefined constants like status codes and categories for consistent and readable code.
│   ├── exceptions/                       # Contains custom exception classes for precise error control and user-friendly error reporting.
│   ├── helpers/                          # Provides utility methods for common tasks such as data formatting and input validation, enhancing reusability.
│   ├── interfaces/                       # Defines interface contracts to promote polymorphism and abstraction, enabling extensibility.
│   ├── models/                           # Represents data structures and encapsulates database tables. Models implement the Model layer in MVC, ensuring data encapsulation and manipulation consistency.
│   ├── services/                         # Handles complex business logic beyond the controllers' like database transactions, scope, supporting abstraction, inheritance, and code reuse.
│   └── views/                            # Manages UI presentation and user input as part of the View layer in MVC.
│       └── gui/                          # Java Swing GUI classes       
├── vendors/                              # External libraries (JDBC drivers)
│   ├── mssql-jdbc-x.x.x.jre11.jar        # MS SQL Server JDBC driver
│   └── ucanaccess-x.x.x.jar              # UCanAccess driver (with dependencies)
├── docs/                                 # Constains the application documentations.
├── .gitignore
├── README.md
├── build.xml (or build.gradle)           # Build file (if using Maven/Gradle)
├── desktop.ini
└── manifest.mf
```

## Additional Resources

**System Report:** For a detailed report, refer to the documentation: [OOP-Project-Documentation](https://bulsumain-my.sharepoint.com/:w:/r/personal/2023100300_ms_bulsu_edu_ph/_layouts/15/Doc.aspx?sourcedoc=%7B99CADDA3-A216-4E13-9B3A-59EEA76BE35E%7D&file=Document%201.docx&action=default&mobileredirect=true).  
**UML Class Diagram:** Explore object relationships through the UML Class Diagram: [UML Diagram](https://lucid.app/lucidchart/52f8ebd5-3998-4227-bdbc-c2304db243d2/edit?viewport_loc=-1636%2C141%2C3371%2C1346%2C0_0&invitationId=inv_c9475dd8-c346-4a94-b439-b54470b0287a).

## Rules
Adherence to consistent coding practices is crucial. Please review the repository’s coding standards: [README.STANDARDS.md](./docs/README.STANDARDS.md)

## Teams

#### Development Team
- **Jerson Valdez** User Interface (UI) & User Experience (UX)  
- **Jerome Avecilla** Business Logic & Database Transaction  
#### Database Team
- **Gillian Bandiola Sioco** Entity Relationship Diagram (ERD)  
- **Pauline Caballero** Enhanced Entity-Relationship Diagram (EERD)  and Relational Data Model (RDM)
- **Raizen Acuña** Database Optimization, Converter ERD to MS Access to MS SQL Server 
#### Research and Documentation
- **Francis Palma** Researcher and Documentation 

## License
This project is for educational purposes and does not include a formal license.
