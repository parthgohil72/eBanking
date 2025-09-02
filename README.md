E-Banking Microservices Backend

A modular, cloud-native backend system simulating core banking operations, built with Spring Boot microservices and designed for scalability, resilience, and real-time data streaming.

Tech Stack
- Java 17, Spring Boot, Spring Security (OAuth2/JWT)
- RESTful APIs, Eureka Server (Service Discovery), Spring Cloud Gateway
- Apache Kafka for event-driven architecture
- Docker for containerization
- Kubernetes (AKS) for orchestration
- Helm for deployment templating and release management
- Resilience4j for fault tolerance (circuit breaker, retry, rate limiter)
- Prometheus + Grafana for observability
- MySQL for persistent storage


 Architecture Overview
- Service Discovery: Eureka Server enables dynamic registration and lookup of microservices.
- API Gateway: Centralized routing and security layer using Spring Cloud Gateway.
- Authentication: OAuth2 with JWT for secure, role-based access control.
- Resilience: Resilience4j ensures fault tolerance with circuit breakers and retries.
- Event Streaming: Kafka handles real-time inter-service communication.
- Observability: Prometheus scrapes metrics; Grafana visualizes service health and performance.
- Containerization: Each microservice is Dockerized for portability and consistency.
- Orchestration: Kubernetes manages scaling, self-healing, and service rollout.
- Helm Advantage: Helm charts simplify deployment, versioning, and rollback across environments.
