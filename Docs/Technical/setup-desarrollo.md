# Guía de Setup de Desarrollo

Esta guía te ayudará a configurar el entorno de desarrollo para MyFinanceManager.

## Prerrequisitos

### Software Requerido

| Herramienta | Versión | Propósito |
|-------------|---------|-----------|
| Java | 21+ | Backend |
| Node.js | 18+ | Mobile |
| Docker Desktop | Latest | Infraestructura |
| Git | 2.x+ | Control de versiones |
| IDE | VS Code / IntelliJ | Desarrollo |

### Verificar Instalación

```bash
# Java
java -version

# Node.js
node --version
npm --version

# Docker
docker --version
docker-compose --version

# Git
git --version
```

## Paso 1: Clonar Repositorio

```bash
git clone <repository-url>
cd MyFinanceManagerApp
```

## Paso 2: Configurar Infraestructura

### Iniciar PostgreSQL y pgAdmin

```bash
cd infrastructure
cp .env.example .env
docker-compose up -d
```

### Verificar Servicios

```bash
docker-compose ps
```

Deberías ver:
- `mfm-postgres` - PostgreSQL corriendo en puerto 5432
- `mfm-pgadmin` - pgAdmin corriendo en puerto 5050

### Acceder a pgAdmin

1. Abre `http://localhost:5050`
2. Login:
   - Email: `admin@mfm.local`
   - Password: `admin`

### Conexión a PostgreSQL

- Host: `localhost`
- Puerto: `5432`
- Database: `mfm_db`
- User: `mfm_user`
- Password: `mfm_password`

## Paso 3: Configurar Backend

### Navegar al directorio

```bash
cd apps/api-backend
```

### Configurar Variables de Entorno

```bash
cp .env.example .env
```

Edita `.env` si necesitas cambiar valores por defecto.

### Ejecutar Backend

```bash
# En Windows
gradlew.bat bootRun

# En Mac/Linux
./gradlew bootRun
```

### Verificar Backend

Abre `http://localhost:8080/actuator/health`

Deberías ver:
```json
{
  "status": "UP"
}
```

## Paso 4: Configurar Mobile

### Navegar al directorio

```bash
cd apps/mobile-app
```

### Instalar Dependencias

```bash
npm install
```

### Configurar Variables de Entorno

```bash
cp .env.example .env
```

### Iniciar Servidor de Desarrollo

```bash
npm start
```

### Ejecutar en Plataforma

Después de iniciar el servidor:

- **iOS Simulator**: Presiona `i`
- **Android Emulator**: Presiona `a`
- **Web Browser**: Presiona `w`
- **Dispositivo Físico**: Escanea el QR code con Expo Go

## Paso 5: Configurar IDE

### VS Code Extensiones Recomendadas

- **Java Extension Pack** - Desarrollo Java
- **Spring Boot Extension Pack** - Spring Boot
- **ESLint** - Linting JavaScript/TypeScript
- **Prettier** - Formateo de código
- **React Native Tools** - React Native
- **Docker** - Contenedores
- **GitLens** - Git integration

### IntelliJ IDEA

1. Abre `apps/api-backend` como proyecto
2. Espera a que Gradle sincronice
3. Configura JDK 21 en Project Structure

## Comandos Útiles

### Backend

```bash
# Compilar
./gradlew build

# Ejecutar tests
./gradlew test

# Ver cobertura
./gradlew jacocoTestReport

# Formatear código
./gradlew spotlessApply

# Verificar calidad
./gradlew check
```

### Mobile

```bash
# Iniciar servidor
npm start

# Ejecutar tests
npm test

# Ver cobertura
npm run test:coverage

# Formatear código
npm run format

# Verificar linting
npm run lint
```

### Docker

```bash
# Detener servicios
docker-compose down

# Reiniciar servicios
docker-compose restart

# Ver logs
docker-compose logs -f postgres

# Eliminar volúmenes (¡cuidado!)
docker-compose down -v
```

## Solución de Problemas

### Puerto ya en uso

Si un puerto está en uso, cambia el puerto en el archivo `.env` correspondiente.

### Error de Java

Asegúrate de tener Java 21 instalado:
```bash
java -version
```

### Error de Node.js

Asegúrate de tener Node.js 18+:
```bash
node --version
```

### Docker no responde

Reinicia Docker Desktop o ejecuta:
```bash
docker-compose down
docker-compose up -d
```

## Próximos Pasos

1. Revisa la [SPEC-001: Autenticación](../Specs/SPEC-001-autenticacion.md)
2. Explora los [ADRs](../ADR/)
3. Comienza a desarrollar features

## Recursos Adicionales

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Expo Documentation](https://docs.expo.dev/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)