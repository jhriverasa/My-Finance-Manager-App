🚀 Project Brief: MyFinanceManager (MFM)
1. Contexto General

MyFinanceManager es una suite integral de gestión financiera personal diseñada bajo estándares de ingeniería de software robustos. El objetivo es centralizar el control de suscripciones, cuentas bancarias, tarjetas de crédito e inversiones (Crypto/CDTs) con un enfoque en la automatización y el análisis predictivo.

    Usuario Objetivo: Profesional técnico que requiere control total y escalabilidad.

    Enfoque de Desarrollo: Mobile-First, Arquitectura Hexagonal y VibeCoding (AI-Assisted).

2. Stack Tecnológico (ADR-001)

Se ha seleccionado un stack desacoplado para evitar el vendor lock-in y facilitar la portabilidad:

    Backend: Java 21 + Spring Boot 3.x.

    Arquitectura: Hexagonal (Ports & Adapters).

    Base de Datos: PostgreSQL 16 (Contenerizada).

    Mobile: React Native con Expo (Expo Router para navegación).

    UI Library: Gluestack UI v2 (Basada en NativeWind/Tailwind CSS).

    Infraestructura: Docker Compose (local), listo para despliegue en servicios como Render, Railway o Supabase.

3. Estructura del Proyecto (Monorepo)
Plaintext

/my-finance-manager
├── /apps
│   ├── /mobile-app        # Expo / React Native
│   └── /api-backend       # Spring Boot (Hexagonal)
├── /docs                  # ADRs, Specs funcionales y técnicas
├── /infrastructure        # Dockerfiles, docker-compose, init.sql
└── README.md

4. Diseño del Backend (Arquitectura Hexagonal)

El núcleo del negocio está aislado de las tecnologías externas:

    Capa de Dominio: Entidades puras (POJOs) y lógica de negocio (ej. cálculo de fechas de corte).

    Capa de Aplicación: Casos de uso (Input Ports) que orquestan el flujo.

    Capa de Infraestructura: Adaptadores (Outbound Ports) para persistencia (JPA), Seguridad (JWT) y controladores REST.

5. Alcance del MVP (Producto Mínimo Viable)

    Seguridad: Sistema de Login/Registro seguro con JWT.

    Cuentas: Gestión manual de saldos en cuentas de ahorro y tarjetas de crédito.

    Suscripciones: El "Core" del MVP. Registro de pagos recurrentes con alertas in-app antes del vencimiento.

    Inversiones Crypto: Tracking manual de saldo en BTC, ETH y NEO.

    Categorización: Clasificación de gastos (Servicios, Ocio, Comida, Arriendo).

6. Modelo de Datos Principal (Resumen)

    User: ID, Email, PasswordHash.

    Account: Name, Type (BANK, CREDIT_CARD, CRYPTO), Balance, Currency.

    Subscription: ProviderName, Amount, Frequency (Monthly/Annual), NextBillingDate, AccountID (FK).

    Transaction: Amount, Category, Date, AccountID (FK).

7. Instrucciones para el LLM (VibeCoding Protocol)

Al trabajar en este proyecto, se deben seguir estas reglas:

    Prioridad de Código: Generar código compatible con Java 21 (Records, Virtual Threads).

    Clean Code: Mantener el desacoplamiento en el backend. El dominio no debe conocer nada de Spring Framework.

    Mobile UI: Utilizar componentes de Gluestack UI v2 y clases de Tailwind (NativeWind).

    TDD: Siempre sugerir o generar los Test Cases antes o junto con la implementación de la lógica de negocio.

Estado Actual: Infraestructura local definida (Docker). Pendiente inicio de la SPEC-001: Autenticación y setup de los proyectos base en el monorepo.