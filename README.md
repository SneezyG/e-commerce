# Microservices eCommerce System

This project is an **event-driven, microservices-based eCommerce system**.

## Services

* **Order Service**: Accepts orders and publishes `order.created` events.
* **Inventory Service**: Consumes `order.created` events and reserves inventory.
* **RabbitMQ**: Event broker (Docker container).
* **PostgreSQL**: Database for orders & inventory (Docker container).

Architecture is modular, clean, and production-ready.

## Architecture Overview

### Event Flow

1. `Order Service` receives an order request.
2. Publishes `order.created` event.
3. `Inventory Service` consumes the event and reserves stock.
4. (Optional) `inventory.reserved` event can be published back.

### Technologies

* Java 17 + Spring Boot
* PostgreSQL
* RabbitMQ
* Docker (for RabbitMQ & PostgreSQL)
* Gradle

## Setup Instructions

### Start Dependencies

```
docker-compose up -d
```

## API Endpoints

### Order Service

* **POST /orders**

  * Create a new order and publish `order.created`.
  * **Example Request:**

```
{
  "id": "100",
  "userId": "user_test",
  "items": [
    {"productId": "A101", "quantity": 1}
  ],
  "totalAmount": 50
}
```

## Event Topics

| Event Name           | Publisher         | Consumers         | Description      |
| -------------------- | ----------------- | ----------------- | ---------------- |
| `order.created`      | Order Service     | Inventory Service | New order placed |
| `inventory.reserved` | Inventory Service | Order Service     | Stock reserved   |

## Testing

### Unit Tests

Run unit tests for both services.

#### Order Service

```
cd order-service
./gradlew test
```

#### Inventory Service

```
cd inventory-service
./gradlew test
```

#### Integration Tests

```
cd order-service
./gradlew test --tests "*Integration*"
```

#### API Test Script

```
./test-api.sh
```

## Development Rules

1. **Backend Architecture**: Clean, modular layers (controller/service/repository/event).
2. **Event-Driven Design**: Async messaging via RabbitMQ events.
3. **Database**: SQL modeling for orders & inventory, consistent with events.
4. **Collaboration**: API contracts documented; README with setup, payloads, tests.
5. **Quality Assurance**: Proper error handling, unit/integration tests, logging, config externalized.

## Notes

* Inventory reservation is **idempotent** — repeated events do not double-reserve stock.
* Services, RabbitMQ, & PostgreSQL run in Docker containers.
* Fully containerized deployment is possible in the future.
