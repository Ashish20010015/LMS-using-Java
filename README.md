# LMS-using-Java

In developing my **Library Management System using Java**, I applied **Object-Oriented Programming (OOP) principles** to design a structured and efficient system. The project utilizes **classes, interfaces, and encapsulation** to manage books, users, and administrators effectively.  

I started by designing a **`Book` class**, which encapsulates attributes like `name`, `author`, `publisher`, `ISBNCode`, and `availability`, ensuring proper data organization. To manage books efficiently, I implemented a **`BookList` class**, which uses an **ArrayList** to store and manipulate book records dynamically.  

For user roles, I created an **abstract `User` class**, which is extended by **`NormalUser` and `Admin`** classes. Each role has different operations: **Normal Users can view, borrow, and return books**, while **Admins can add, delete, and modify book records**. These operations are handled using an **interface (`IOOperation`)**, which defines various functionalities like **`AddBook`**, **`DeleteBook`**, and **`BorrowBook`**, promoting a **modular and scalable** structure.  

To enhance user interaction, I implemented a **menu-driven system** using **`LMSUtility`**, which handles **user input validation and navigation**. The **main function (`lms.java`)** integrates all components, managing the login system and directing users to their respective menus.  

This project demonstrates my expertise in **Java OOP, data structures (ArrayList), and interface-driven programming**. By maintaining a **clear separation of concerns and modular design**, I ensured **scalability, maintainability, and user-friendly operation** of the system. 🚀
