# OrderService_ECommerce
Project: Building a Microservices-Based E-Commerce Application - Order Service

How to set up the project:
Update the DataBase configuration properties in the application.properties file

How to run the application:
1. Create the DB in MySQL
2. Verify the properties of this microservice in the config-repo
3. Run the config-server and eureka-server
4. Run this microservice project

NOTE: The server port will change according server port value in the config-repo properties

API endpoints and sample requests for testing:

CREATE:
 - POST -> http://localhost:8082/orders
 - BodyRequest:
{
  "productId": 1,
  "quantity": 1,
  "status": "Pending"
}

READ:
- GET ->  http://localhost:8082/orders/1 (get order by ID)
- GET -> http://localhost:8082/orders/ (get all orders)

UPDATE:
- PUT -> http://localhost:8082/orders/updateStatusManually/1 (update the status of the order according with the status value)
 - BodyRequest:
{
  "status": "Shipped"
}

- PUT -> http://localhost:8082/orders/updateStatusAutomatically/1 (update the status of the order according with the status cycle)

- PUT -> http://localhost:8082/orders/payOrder/1 (pay the order and if the payment was successfull update the order status)
