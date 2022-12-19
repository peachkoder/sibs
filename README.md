
# SIBS SIMPLE API

Simple order manager. 
API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it.


## Author

- [@peachkoder](https://www.github.com/peaachkoder)


## Tech Stack

**Server:** Java8, Spring Boot, Spring  JPA, Hibernate, PostgreSQL, MySQL, LogBack, AspectJ, Postman.


## Table of Contents
- Features Demanded
- Documentation
  - Introduction
  - Persistence
  - Logging
  - API Reference
 
## Features Demanded

- create, read, update and delete and list all entities;

- when an order is created, it should try to satisfy it with the current stock.;

- when a stock movement is created, the system should try to attribute it to an order that isn't complete;

- when an order is complete, send a notification by email to the user that created it;

- trace the list of stock movements that were used to complete the order, and vice-versa;

- show current completion of each order;

- Write a log file with: orders completed, stock movements, email sent and errors.
## Documentation

### Introduction 
The system relates orders to inventory movements.
When entering the order, the system looks for stock movements that have the same item as the order.

If the stock movement has a smaller quantity than the order, this stock movement is blocked and becomes part of the order. Blocked stock movements can no longer be part of other orders.

If the stock movement has a quantity greater than the order, then it is divided, generating a new stock movement with the same quantity as the order. This new stock movement is blocked and becomes part of the order. The original movement is free to be part of other orders, but with less quantity available.

When a stock movement is entered, the system looks for incomplete orders. The behavior follows the same way described above.

#### Persistence
The persistence layer is composed of JPA repositories and entities managed by Hibernate.
Some data is pre-entered on system loading to make testing easier.

#### Logging
AspectJ + Logback were used to introduce Aspect Oriented Programming (AOP) and logging capability to the system.
The behavior and errors of methods in controllers, services and repositories are analyzed and recorded in the file.
User can download log file from endpoint **/api/v1/admin/log**

### API Reference
The API has been divided in 4 RestControllers. 

- OrderController 
- StockMovementController 
- ItemController 

- UserController



All Endpoints have **'/api/v1'** as prefix , so take that in consideration.

Functions/Rest verb table:
| Function | REST verb |  
| :--------  | :-------- | 
| get all/get by | GET |
| create | POST |
| update | PUT |
| delete | DELETE |  

#### OrderController
| Function | Endpoint | Parameters | Types     | Note	|
| :--------  | :-------- | :-------- | :------- | :------------------------- |
| `get all orders` | `/order` | `field, sort` | `String, boolean` | Sorted by field. True = asc|
| `get stock movements by order` | ` /order/{id}/stockmovements` | `id` | `String` | |
| `create order` | `/order` | `qunatity, userId, itemId` | `Integer, Long, String` |  |
| `update order` | `/order` | `order` | `Order` |  |
| `delete order` | `order` | `order` | `Order` |  |

#### StockMovementController
| Function | Endpoint | Parameters | Types     | Note	|
| :--------  | :-------- | :-------- | :------- | :------------------------- |
| `get all movements` | `/stockmovement` | `field, sort` | `String, boolean` | Sorted by field. True = asc| 
| `create movements` | `/stockmovement` | `movementDto` | `StockMovementDto` |  |
| `update movements` | `/stockmovement` | `movement` | `StockMovement` |  |
| `delete movements` | `/stockmovement` | `movement` | `StockMovement` |  |

#### ItemController
| Function | Endpoint | Parameters | Types     | Note	|
| :--------  | :-------- | :-------- | :------- | :------------------------- |
| `get all items` | `/items` | `field, sort` | `String, boolean` | Sorted by field. True = asc| 
| `get item by name` | `/items/{name}` | `name` | `String` | | 
| `create item` | `/items` |  `name` | `String` |  |
| `update item` | `/items` | `item` | `Item` |  |
| `delete item` | `/items` | `item` | `Item` |  |

#### UserController
| Function | Endpoint | Parameters | Types     | Note	|
| :--------  | :-------- | :-------- | :------- | :------------------------- |
| `get all users` | `/user` | `field, sort` | `String, boolean` | Sorted by field. True = asc| 
| `get user by name` | `/user/{name}` | `name` | `String` | | 
| `create user` | `/user` |  `name, email` | `String, String` | **Email** must be unique |
| `update user` | `/user` | `user` | `User` |  |
| `delete user` | `/user` | `user` | `User` |  |

#### AdminController
| Function | Endpoint | Parameters | Types     | Note	|
| :--------  | :-------- | :-------- | :------- | :------------------------- |
| `get log file` | `/log` |   |   | Log file download| 

