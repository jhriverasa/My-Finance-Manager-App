# ADR-001: Stack Tecnológico

## Estado
**Aceptado**

## Contexto
MyFinanceManager requiere un stack tecnológico que permita:
- Desarrollo multiplataforma (mobile + backend)
- Escalabilidad y mantenibilidad
- Evitar vendor lock-in
- Portabilidad entre entornos

## Decisión
Se ha seleccionado el siguiente stack tecnológico:

### Backend
- **Java 21** - LTS con características modernas (Records, Virtual Threads, Pattern Matching)
- **Spring Boot 3.x** - Framework maduro con amplio ecosistema
- **Gradle** - Build tool flexible y performante

### Mobile
- **React Native** - Desarrollo cross-platform con JavaScript/TypeScript
- **Expo** - Herramientas de desarrollo simplificadas
- **Expo Router** - Navegación basada en file-system routing
- **Gluestack UI v2** - Componentes UI basados en NativeWind/Tailwind CSS

### Base de Datos
- **PostgreSQL 16** - Base de datos relacional robusta y open-source

### Infraestructura
- **Docker Compose** - Contenerización para desarrollo local
- **Render/Railway/Supabase** - Opciones para despliegue en producción

## Consecuencias

### Positivas
- ✅ Stack desacoplado y portable
- ✅ Amplia comunidad y documentación
- ✅ Java 21 ofrece características modernas
- ✅ Expo simplifica el desarrollo mobile
- ✅ PostgreSQL es confiable y escalable

### Negativas
- ⚠️ Curva de aprendizaje para Arquitectura Hexagonal
- ⚠️ Expo tiene limitaciones para módulos nativos personalizados
- ⚠️ Mayor complejidad inicial en configuración

### Riesgos
- 🟡 Actualizaciones frecuentes de Spring Boot 3.x
- 🟡 Dependencia de mantenibilidad de Gluestack UI

## Referencias
- [Spring Boot 3.x Documentation](https://spring.io/projects/spring-boot)
- [Expo Documentation](https://docs.expo.dev/)
- [PostgreSQL 16 Release Notes](https://www.postgresql.org/docs/16/release-16.html)