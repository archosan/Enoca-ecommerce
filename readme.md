## Enoca E-commerce API

This is a simple e-commerce API that allows users to create and manage products, orders, and users. The API is built using Spring Boot also java 15, and uses a PostgreSQL database to store data. Spring Boot version 2.7.18 is used in this project.

## Features

The API provides the following features:

### Cart Management

1. Get cart by customer id
2. Empty cart by customer id
3. Add product to cart
4. Remove product from cart
5. Update Cart

### Product Management

1. Get all products
2. Get product by id
3. Create product
4. Update product
5. Delete product

### Order Management

1. Get order by customer id
2. Place order
3. Get order by order id

### Customer Management

1. Get all customers
2. Get customer by id
3. Create customer
4. Delete customer

## Installation

To run this project, you need to have Docker installed on your machine. You can download Docker from [here](https://www.docker.com/products/docker-desktop).

1. Clone the repository to your local machine using the following command:
2. Navigate to the project directory:
3. Run the following command to build the Docker image:

```bash
docker-compose up -d
```

This command will build the Docker image and run the container in detached mode. The API will be accessible at `http://localhost:8080`.

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`. You can use this documentation to test the API endpoints.

To test the API endpoints, you can use the following steps:

1. Open the API documentation in your browser by navigating to `http://localhost:8080/swagger-ui.html`.

2. You need to add user first before you can add product to cart. You can add user by clicking on 'POST /customers' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
3. After adding user, you have a cart for that user. You have to add product for adding product to cart. You can add product by clicking on 'POST /products' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
4. After adding product, you can add product to cart by clicking on 'POST /cart/{customerId}/add-product/{productId}' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
5. You can remove product from cart by clicking on 'POST /cart/{customerId}/remove-product/{productId}' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
6. Also there is a 'PUT /cart/{customerId}' endpoint to update the product in the cart.
7. You can order the product in the cart by clicking on 'POST /orders/{customerId}' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
8. And see the order by clicking on 'GET /orders/{customerId}' and then click on 'Try it out' button. Fill in the required fields and click on 'Execute' button.
