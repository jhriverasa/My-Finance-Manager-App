# ADR-002: Arquitectura Hexagonal (Ports & Adapters)

## Estado
**Aceptado**

## Contexto
El backend de MyFinanceManager necesita una arquitectura que:
- Aísle la lógica de negocio de las tecnologías externas
- Facilite el testing unitario
- Permita cambiar infraestructura sin afectar el dominio
- Sea mantenible a largo plazo

## Decisión
Se implementará **Arquitectura Hexagonal (Ports & Adapters)** en el backend Spring Boot.

### Estructura de Capas

```
com.myfinancemanager.api
├── domain/                    # Capa de Dominio (núcleo)
│   ├── model/                 # Entidades puras (POJOs)
│   ├── port/                  # Puertos (interfaces)
│   │   ├── in/                # Input Ports (casos de uso)
│   │   └── out/               # Output Ports (infraestructura)
│   └── service/               # Lógica de negocio
├── application/               # Capa de Aplicación
│   ├── usecase/               # Casos de uso (implementan Input Ports)
│   ├── dto/                   # Data Transfer Objects
│   └── exception/             # Excepciones de aplicación
└── infrastructure/            # Capa de Infraestructura
    ├── adapter/               # Adaptadores
    │   ├── in/                # Adaptadores de entrada (REST controllers)
    │   └── out/               # Adaptadores de salida (JPA, external APIs)
    ├── config/                # Configuración Spring
    ├── persistence/           # Entidades JPA, Repositories
    └── security/              # Seguridad (JWT, Spring Security)
```

### Principios Clave

1. **Dominio Independiente**: La capa `domain` NO tiene dependencias de Spring o frameworks
2. **Inversión de Dependencias**: Los adapters dependen de los ports, no al revés
3. **Testing**: El dominio se puede testear sin infraestructura
4. **Regla de Oro**: Las dependencias siempre apuntan hacia adentro (hacia el dominio)

### Ejemplo de Flujo

```
REST Controller (infrastructure) 
    → Use Case (application) 
        → Domain Service (domain) 
            → Repository Port (domain) 
                → JPA Repository (infrastructure)
```

## Consecuencias

### Positivas
- ✅ Alto desacoplamiento
- ✅ Testabilidad del dominio sin mocks complejos
- ✅ Fácil evolución de la infraestructura
- ✅ Código más mantenible y organizado

### Negativas
- ⚠️ Mayor cantidad de archivos y clases
- ⚠️ Curva de aprendizaje para el equipo
- ⚠️ Posible sobre-ingeniería para proyectos pequeños

### Riesgos
- 🟡 Tentación de violar capas por conveniencia
- 🟡 Dificultad para mantener límites claros

## Referencias
- [Hexagonal Architecture - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Best Practices](https://spring.io/guides)