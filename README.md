# Dressy - POS System for Clothing Shop

Dressy is a Point of Sale (POS) system built using Spring, MySQL, Hibernate, and Spring Data JPA. It provides a complete backend solution to manage customer and item information, place orders, and handle transactions efficiently. The system offers CRUD operations for customers and items, along with functionalities to view, select, and manage orders.

## Features
- **Customer Management**: Perform all CRUD operations (Create, Read, Update, Delete) on customer data.
- **Item Management**: Manage items with full CRUD capabilities.
- **Order Management**: Place new orders, view all orders, and select specific orders.
- **Transaction Support**: Ensure consistency and reliability through transaction management.

## Technologies Used
- Spring Framework
- MySQL Database
- Hibernate ORM
- Spring Data JPA
- RESTful API

## API Endpoints

- **Customer API**
    - `GET /customers`: Fetch all customers.
    - `POST /customers`: Create a new customer.
    - `PATCH /customers/{id}`: Update an existing customer.
    - `DELETE /customers/{id}`: Delete a customer.

- **Item API**
    - `GET /items`: Fetch all items.
    - `POST /items`: Create a new item.
    - `PATCH /items/{id}`: Update an existing item.
    - `DELETE /items/{id}`: Delete an item.

- **Order API**
    - `GET /orders`: Fetch all orders.
    - `POST /orders`: Place a new order.
    - `GET /orders/{id}`: View details of a specific order.

## API Documentation

For detailed API documentation, visit: [Postman API Documentation](https://documenter.getpostman.com/view/35386359/2sAXxV59Uv)

## Usage
Use the provided API endpoints to interact with the POS system. You can perform CRUD operations on customers and items, as well as place and manage orders. Use Postman or any API client to test and interact with the endpoints.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
