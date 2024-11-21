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

- **`models`**  
  Represents data structures and encapsulates database tables. Models implement the **Model** layer in MVC, ensuring data encapsulation and manipulation consistency.

- **`services`**  
  Handles complex business logic beyond the controllers' scope, supporting abstraction, inheritance, and code reuse.

## Development Process
The application was collaboratively developed using Git for version control, enabling seamless team collaboration. Contributions, updates, and issue tracking are available in the project’s GitHub repository:  
[**BSIT2FG2_PROJECT_G1 Repository**](https://github.com/javecilla/BSIT2FG2_PROJECT_G1).

## Prerequisites
Ensure the following tools are installed to run and develop the application:

- **[Java JDK](https://www.java.com/download/ie_manual.jsp):** Essential for compiling and running Java applications.
- **[NetBeans IDE](https://netbeans.apache.org/):** Simplifies Java development and project management.
- **[UCanAccess](https://ucanaccess.sourceforge.net/site.html):** A JDBC driver for connecting Java applications to Microsoft Access databases.

## Additional Resources

- **System Report:** For a detailed report, refer to the documentation: [OOP-Project-Documentation.docx](https://docs.google.com/document/d/12_uOVckdKLDgM0cxOCKlfHoWVn6w99mhC83IONZR1K0/edit?usp=sharing).  
- **UML Class Diagram:** Explore object relationships through the UML Class Diagram: [UML Diagram](https://lucid.app/lucidchart/52f8ebd5-3998-4227-bdbc-c2304db243d2/edit?viewport_loc=-1636%2C141%2C3371%2C1346%2C0_0&invitationId=inv_c9475dd8-c346-4a94-b439-b54470b0287a).

## Coding Standards
Adherence to consistent coding practices is crucial. Please review the repository’s coding standards: [README.STANDARDS.md](./src/README.STANDARDS.md).

## Teams

### Development Team
- **Jerson Valdez**  
  *Role:* User Interface (UI) & User Experience (UI)  

- **Jerome Avecilla**  
  *Role:* Business Logic & Database Transaction  

### Database Team
- **Gillian Bandiola Sioco**  
  *Role:* Entity Relationship Diagram (ERD) Designer  

- **Pauline Caballero**  
  *Role:* Enhanced Entity-Relationship Diagram (EERD) Specialist  

- **Raizen Acuña**  
  *Role:* Relational Data Model (RDM) Specialist and Database Converter (MS Access Integration)  

### Research and Documentation
- **Francis Palma**  
  *Role:* Researcher and Documentation  

## Summary
The **E-commerce Store** application exemplifies robust and scalable development by combining **MVC** and **OOP principles**. Its modular structure promotes maintainability and flexibility, making it an ideal foundation for future enhancements and scalability.
