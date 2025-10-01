# Restock Platform

## Summary

Restock Platform showcases development using Java with the Spring Boot framework and Spring Data MongoDB, working on a MongoDB database. It also demonstrates how to configure and integrate OpenAPI documentation with Swagger UI.

## Features

- RESTful API
- OpenAPI Documentation
- Swagger UI
- Spring Boot Framework
- Spring Data MongoDB
- Validation
- MongoDB Database
- Domain-Driven Design

## Bounded Contexts

Restock Platform is divided into seven bounded contexts:Subscriptions and Payments, Identity and Access Management, Profiles and Preferences, Service Design and Planning, Asset Operation and Monitoring, Service Operation and Monitoring y Analytics.

### Identity and Access Management (IAM) Context

The IAM Context handles the management of platform users, including user registration and authentication processes. It leverages JSON Web Tokens for authorization and applies password encryption. Additionally, it incorporates a request authorization middleware into the Spring Boot processing pipeline to validate tokens included in the request headers for protected endpoints. Its main features include:

- Register a new User (Sign Up).
- Authenticate an existing User (Sign In).
- Retrieve a User by ID.
- List all Users.
- List all Roles.
- Implement an authorization flow using Spring Security and request filtering.
- Generate and verify JSON Web Tokens.
- Secure passwords through hashing.

This version introduces three user roles: **Admin**, **Instructor**, and **Student**. These roles are used to control access to platform functionalities. The **Admin** role has full access, while **Instructor** and **Student** permissions are defined based on specific business logic.

#### Anti-Corruption Layer

The context also includes an anti-corruption layer, which serves to manage interactions between the IAM Context and other bounded contexts. Its responsibilities include:

- Register a new User, returning the User ID upon success. If roles are provided, they are assigned; otherwise, a default role is applied.
- Retrieve a User by ID, returning the corresponding User ID.
- Retrieve a User by Username, returning the corresponding User ID.

In this release, the OpenAPI documentation is configured to support JWT-based authorization.


### Profiles and Preferences Context

- Create a new profile.
- Get a profile by id.
- Get all profiles.

This context includes also an anti-corruption layer to communicate with the Learning Context. The anti-corruption layer is responsible for managing the communication between the Profiles Context and the Learning Context. It offers the following capabilities to other bounded contexts:
- Create a new Profile, returning ID of the created Profile on success.
- Get a Profile by Email, returning the associated Profile ID on success.

### Subscriptions and Payments Context

- Subscribe a user to a service or plan.
- Manage active and past subscriptions.
- Handle payment processing and transaction history.

This context manages the full lifecycle of subscriptions and payments. It ensures that users are properly billed, payments are recorded, and subscription statuses are kept up to date. It also exposes endpoints for verifying active subscriptions and accessing billing data when required by other contexts.

---

### Service Design and Planning Context

- Create and manage service recipes.
- Define ingredients, preparation steps, and required resources.
- Allow restaurants to design or customize service offerings.

This context focuses on the creation and planning of services offered on the platform, particularly recipe-based services. It enables restaurants to define what they offer, how it should be prepared, and what materials or inputs are needed.

---

### Asset and Resource Management Context

- Manage restaurant inventory and track stock levels.
- Maintain a catalog of suppliers and available products.
- Allow administrators to create and manage purchase orders to suppliers.

This context supports efficient management of physical assets and customSupply chain operations. It allows restaurant administrators to keep inventory up to date, connect with suppliers, and manage restocking through automated or manual orders.

---

### Service Operation and Monitoring Context

- Allow providers to manage incoming service orders.
- Track service completion and gather customer feedback.
- Enable restaurants to monitor sales and order statuses in real time.

This context handles the day-to-day execution of services on the platform. Providers can view and complete orders, while customers (restaurants) can see the progress of their requests and submit feedback once a service is completed.

---

### Analytics Context

- Provide summary dashboards for key business metrics.
- Generate insights for both suppliers and restaurants.
- Support decision-making with aggregated data (e.g., sales, order volume, service ratings).

This context delivers relevant analytics to both primary segments of the platform. It transforms raw operational data into meaningful information, enabling users to make informed decisions and evaluate performance over time.


### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.0/maven-plugin/build-image.html)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.5.0/reference/data/nosql.html#data.nosql.mongodb)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.0/reference/using/devtools.html)
* [Validation](https://docs.spring.io/spring-boot/3.5.0/reference/io/validation.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.0/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Because of how Maven is designed, elements from the parent POM are automatically inherited by the project POM. While this inheritance is generally beneficial, it can also bring in unwanted elements such as <license> and <developers>. To avoid this, the project POM may include empty overrides for these elements. However, if you manually switch to a different parent and want to inherit those elements, you'll need to remove the empty overrides.