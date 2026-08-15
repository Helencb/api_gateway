# Gateway Server

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Latest-blue)
![Redis](https://img.shields.io/badge/Redis-Rate%20Limit-red)
![Resilience4j](https://img.shields.io/badge/Resilience4j-Circuit%20Breaker-yellow)
![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-orange)
![Swagger](https://img.shields.io/badge/OpenAPI-Documentation-green)
![License](https://img.shields.io/badge/Status-In%20Development-blue)

API Gateway enterprise desenvolvida com Spring Cloud Gateway para atuar como ponto central de entrada em uma arquitetura de microsserviços.

Projetada com foco em:

**segurança • escalabilidade • observabilidade • resiliência • boas práticas**

</div>

---

# Visão geral

Esta gateway centraliza toda a comunicação entre clientes e microsserviços, aplicando:

- autenticação
- autorização
- rate limiting
- service discovery
- load balancing
- observabilidade
- tracing
- fallback
- circuit breaker
- retry automático

---

# Arquitetura

```text
                                CLIENTS
                                   │
                                   ▼
                     ┌────────────────────────────┐
                     │       GATEWAY SERVER       │
                     │────────────────────────────│
                     │ Authentication (JWT)       │
                     │ Authorization              │
                     │ Request Validation         │
                     │ Header Enrichment          │
                     │ Context Propagation        │
                     │ Rate Limiting (Redis)      │
                     │ Retry                      │
                     │ Circuit Breaker            │
                     │ Metrics + Tracing          │
                     └──────────────┬─────────────┘
                                    │
                                    ▼
                         EUREKA + LOAD BALANCER
                                    │
        ┌───────────────────────────┼───────────────────────────┐
        ▼                           ▼                           ▼
 CLIENT SERVICE              ORDER SERVICE              PAYMENT SERVICE
        │                           │                           │
        ▼                           ▼                           ▼
 NOTIFICATION SERVICE       OTHER MICROSERVICES          FUTURE SERVICES
```

---

# Tecnologias utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring WebFlux
- Spring Cloud Eureka
- Spring Cloud LoadBalancer

## Segurança

- JWT Authentication
- Role Based Authorization

## Resiliência

- Resilience4j
- Retry
- Circuit Breaker
- Fallback

## Performance

- Redis
- Distributed Rate Limiting

## Observabilidade

- Spring Actuator
- Micrometer
- Prometheus
- Distributed Tracing

## Documentação

- OpenAPI
- Swagger

---

# Funcionalidades

## Service Discovery

Registro automático via Eureka.

## Dynamic Routing

Roteamento dinâmico para microsserviços.

## Load Balancing

Distribuição automática entre instâncias.

## Authentication

Validação de token JWT.

## Authorization

Controle de acesso baseado em roles.

## Header Enrichment

Adição de headers técnicos automaticamente.

## Context Propagation

Propagação de contexto do usuário.

## Rate Limiting

Controle distribuído com Redis.

## Retry

Retentativas automáticas.

## Circuit Breaker

Proteção contra falhas em cascata.

## Fallback

Resposta controlada em indisponibilidade.

## Metrics

Coleta de métricas em tempo real.

## Tracing

Correlation ID entre serviços.

---

# Estrutura completa do projeto

```text
gateway-service/
│
├── src/main/java/com/helen/gateway/
│
│   ├── GatewayApplication.java
│   │
│   ├── config/
│   │   ├── GatewayRoutesConfig.java
│   │   ├── SecurityConfig.java
│   │   ├── CorsConfig.java
│   │   ├── OpenApiConfig.java
│   │   ├── LoadBalancerConfig.java
│   │   ├── RedisConfig.java
│   │   ├── ResilienceConfig.java
│   │   ├── ObservabilityConfig.java
│   │
│   ├── discovery/
│   │   ├── EurekaRegistrationConfig.java
│   │
│   ├── filters/
│   │   ├── global/
│   │   │   ├── LoggingGlobalFilter.java
│   │   │   ├── CorrelationIdGlobalFilter.java
│   │   │   ├── MetricsGlobalFilter.java
│   │   │   ├── RequestResponseGlobalFilter.java
│   │   │
│   │   ├── route/
│   │   │   ├── AuthenticationFilter.java
│   │   │   ├── AuthorizationFilter.java
│   │   │   ├── RateLimitFilter.java
│   │   │   ├── HeaderEnrichmentFilter.java
│   │   │   ├── UserContextFilter.java
│   │   │   ├── RequestValidationFilter.java
│   │
│   ├── security/
│   │   ├── JwtService.java
│   │   ├── JwtValidator.java
│   │   ├── PublicEndpoints.java
│   │   ├── SecurityContextService.java
│   │
│   ├── routing/
│   │   ├── ClientRoutes.java
│   │   ├── OrderRoutes.java
│   │   ├── PaymentRoutes.java
│   │   ├── NotificationRoutes.java
│   │
│   ├── fallback/
│   │   ├── ClientFallbackController.java
│   │   ├── OrderFallbackController.java
│   │   ├── PaymentFallbackController.java
│   │   ├── NotificationFallbackController.java
│   │
│   ├── rateLimit/
│   │   ├── RedisRateLimitService.java
│   │   ├── RateLimitPolicy.java
│   │
│   ├── observability/
│   │   ├── TracingService.java
│   │   ├── MetricsService.java
│   │
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ErrorResponse.java
│   │
│   ├── util/
│   │   ├── HeaderUtils.java
│   │   ├── IpUtils.java
│   │   ├── JsonUtils.java
│   │
│   └── constants/
│       ├── SecurityConstants.java
│       ├── GatewayConstants.java
│
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prod.yml
│
├── pom.xml
└── README.md
```

---

# Pipeline de request

Toda requisição passa pelo seguinte pipeline:

```text
Request
   │
   ▼
Request Validation
   │
   ▼
Authentication
   │
   ▼
Authorization
   │
   ▼
User Context
   │
   ▼
Header Enrichment
   │
   ▼
Rate Limit
   │
   ▼
Retry
   │
   ▼
Circuit Breaker
   │
   ▼
Eureka Discovery
   │
   ▼
Load Balancer
   │
   ▼
Microservice
```

---

# Rotas disponíveis

## Auth Service

Rotas públicas (sem JWT):

```http
/api/auth/**       -> register, login, refresh
/api/password/**   -> forgot, reset
/api/oauth2/**     -> login social (Google)
/api/mfa/verify    -> valida código TOTP mid-login
```

Rotas autenticadas (exigem `Authorization: Bearer <jwt>` com role `USER`):

```http
/api/mfa/setup
/api/mfa/enable
/api/sessions/**
```

Todas roteadas para `lb://AUTH-SERVICE` com `StripPrefix=1` (o auth-service recebe o path sem o `/api`).

## Client Service

```http
/api/clients/**
```

## Order Service

```http
/api/orders/**
```

## Payment Service

```http
/api/payments/**
```

## Notification Service

```http
/api/notifications/**
```

---

# Segurança

Header obrigatório:

```http
Authorization: Bearer <jwt-token>
```

Headers propagados:

```http
X-User-Id
X-User-Role
X-Correlation-Id
X-Gateway-Version
```

---

# Rate Limiting

Implementado com Redis.

Política atual:

```text
100 requests por minuto por usuário/IP
```

Resposta em excesso:

```http
429 TOO MANY REQUESTS
```

---

# Resiliência

Configuração atual:

```text
Retry: 3 tentativas

Circuit Breaker:
- Sliding Window: 10
- Failure Rate: 50%
- Open State: 10 segundos
```

---

# Observabilidade

## Health Check

```bash
GET http://localhost:8080/actuator/health
```

## Metrics

```bash
GET http://localhost:8080/actuator/prometheus
```

## Swagger

```bash
GET http://localhost:8080/swagger-ui.html
```

## Eureka

```bash
GET http://localhost:8761
```

---

# Como executar localmente

## 1 — Clonar projeto

```bash
git clone <repository-url>
```

## 2 — Subir Eureka Server

```bash
mvn spring-boot:run
```

## 3 — Subir Redis

```bash
redis-server
```

## 4 — Subir Gateway

```bash
mvn spring-boot:run
```

---

# Roadmap

- [x] Service Discovery
- [x] Dynamic Routing
- [x] JWT Authentication
- [x] Authorization
- [x] Rate Limiting
- [x] Retry
- [x] Circuit Breaker
- [x] Fallback
- [x] Metrics
- [x] Prometheus
- [x] Tracing
- [ ] Zipkin
- [ ] Docker
- [ ] Kubernetes
- [ ] Distributed Cache
- [ ] Canary Release

---

# Desenvolvido por:

## Helen Batista

Desenvolvedora Backend focada em:

- Java
- Spring Boot
- Microsserviços
- Mensageria
- Arquitetura distribuída

Se quiser trocar ideias ou compartilhar feedbacks sobre o projeto, fique à vontade para entrar em contato.

---
