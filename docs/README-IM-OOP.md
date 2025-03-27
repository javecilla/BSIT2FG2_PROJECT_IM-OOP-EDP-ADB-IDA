# E-commerce Store Documentation

## Overview
This Java-based **E-commerce Store** application provides a comprehensive solution for managing products, categories, and user interactions through a graphical user interface (GUI) using `JOptionPane` dialogs. Built on the **MVC (Model-View-Controller)** architecture and leveraging **OOP (Object-Oriented Programming)** principles, the application ensures maintainability, scalability, and a clear separation of concerns.

## Application Structure

### Java GUI (`JOptionPane`)
The application’s GUI handles user interactions using `JOptionPane` for intuitive and consistent experiences. The **MVC pattern** is implemented to separate user interface, business logic, and data handling, ensuring clean and modular code.

### Source Packages
The application is organized into the following logical packages:

- **`config`**  
  Centralizes configuration settings, including database connections, to streamline management.

- **`enums`**  
  Stores predefined constants like status codes and categories for consistent and readable code.

- **`interfaces`**  
  Defines interface contracts to promote polymorphism and abstraction, enabling extensibility.

- **`exceptions`**  
  Contains custom exception classes for precise error control and user-friendly error reporting.

- **`helpers`**  
  Provides utility methods for common tasks such as data formatting and input validation, enhancing reusability.

- **`views`**  
  Manages UI presentation and user input as part of the **View** layer in MVC.

- **`controllers`**  
  Coordinates application workflows, handles requests, and processes logic as the **Controller** layer in MVC. Controllers interact with services and models to manage specific tasks.

- **`services`**  
  Handles complex business logic beyond the controllers' scope, supporting abstraction, inheritance, and code reuse.

- **`models`**  
  Represents data structures and encapsulates database tables. Models implement the **Model** layer in MVC, ensuring data encapsulation and manipulation consistency.

- **`core`**  
  Contains essential classes and foundational components that define the application's core functionality. These classes are not tied to any specific layer (e.g., MVC) but represent key abstractions, utilities, or domain-specific logic that other packages rely on. The core package ensures reusability and encapsulation of central features.

## Development Process
The application was collaboratively developed using Git for version control, enabling seamless team collaboration. Contributions, updates, and issue tracking are available in the project’s GitHub repository:  
[**BSIT2FG2_PROJECT_G1 Repository**](https://github.com/javecilla/BSIT2FG2_PROJECT_G1).

## Prerequisites
Ensure the following tools are installed to run and develop the application:

- **[Java JDK](https://www.java.com/download/ie_manual.jsp):** Essential for compiling and running Java applications.
- **[NetBeans IDE](https://netbeans.apache.org/):** Simplifies Java development and project management.
- **[UCanAccess](https://ucanaccess.sourceforge.net/site.html):** A JDBC driver for connecting Java applications to Microsoft Access databases.

## Additional Resources

- **System Report:** For a detailed report, refer to the documentation: [OOP-Project-Documentation](https://bulsumain-my.sharepoint.com/:w:/r/personal/2023100300_ms_bulsu_edu_ph/_layouts/15/Doc.aspx?sourcedoc=%7B99CADDA3-A216-4E13-9B3A-59EEA76BE35E%7D&file=Document%201.docx&action=default&mobileredirect=true).  
- **UML Class Diagram:** Explore object relationships through the UML Class Diagram: [UML Diagram](https://lucid.app/lucidchart/52f8ebd5-3998-4227-bdbc-c2304db243d2/edit?viewport_loc=-1636%2C141%2C3371%2C1346%2C0_0&invitationId=inv_c9475dd8-c346-4a94-b439-b54470b0287a).

## Coding Standards
Adherence to consistent coding practices is crucial. Please review the repository’s coding standards: [README.STANDARDS.md](./src/README.STANDARDS.md).

## Teams

### Development Team
- **Jerson Valdez**  
  *Role:* User Interface (UI) & User Experience (UX)  

- **Jerome Avecilla**  
  *Role:* Business Logic & Database Transaction  

### Database Team
- **Gillian Bandiola Sioco**  
  *Role:* Entity Relationship Diagram (ERD)  

- **Pauline Caballero**  
  *Role:* Enhanced Entity-Relationship Diagram (EERD)  and Relational Data Model (RDM)

- **Raizen Acuña**  
  *Role:*  Database Converter (MS Access Integration)  

### Research and Documentation
- **Francis Palma**  
  *Role:* Researcher and Documentation  

## Summary
The **E-commerce Store** application exemplifies robust and scalable development by combining **MVC** and **OOP principles**. Its modular structure promotes maintainability and flexibility, making it an ideal foundation for future enhancements and scalability.
