# MyFinanceManager

MyFinanceManager es una suite integral de gestión financiera personal diseñada bajo estándares de ingeniería de software robustos. Centraliza el control de suscripciones, cuentas bancarias, tarjetas de crédito e inversiones (Crypto/CDTs) con un enfoque en la automatización y el análisis predictivo.

## 🚀 Características

- **Gestión de Suscripciones** - Registro y seguimiento de pagos recurrentes con alertas
- **Cuentas Bancarias** - Gestión manual de saldos en cuentas de ahorro
- **Tarjetas de Crédito** - Seguimiento de gastos y fechas de corte
- **Inversiones Crypto** - Tracking de BTC, ETH, NEO y stablecoins
- **Categorización** - Clasificación automática de gastos

## 🏗️ Arquitectura

El proyecto sigue una arquitectura de **Monorepo** con separación clara entre frontend y backend:

```
my-finance-manager/
├── /apps
│   ├── /mobile-app        # React Native + Expo
│   └── /api-backend       # Spring Boot (Hexagonal)
├── /docs                  # ADRs, Specs, Technical docs
├── /infrastructure        # Docker, PostgreSQL
└── README.md
```

## 🛠️ Stack Tecnológico

### Backend
- **Java 21** + **Spring Boot 3.x**
- **Arquitectura Hexagonal** (Ports & Adapters)
- **PostgreSQL 16** (contenerizada)
- **Spring Security** + **JWT**
- **Gradle** (build tool)

### Mobile
- **React Native** + **Expo**
- **TypeScript**
- **NativeWind** (Tailwind CSS)
- **Gluestack UI** (componentes)
- **Zustand** (estado)

### Infraestructura
- **Docker Compose** (desarrollo local)
- **pgAdmin** (gestión de BD)

## 📚 Documentación

### Architecture Decision Records (ADRs)
- [ADR-001: Stack Tecnológico](docs/ADR/ADR-001-stack-tecnologico.md)
- [ADR-002: Arquitectura Hexagonal](docs/ADR/ADR-002-arquitectura-hexagonal.md)
- [ADR-003: Herramientas de Calidad](docs/ADR/ADR-003-herramientas-calidad.md)

### Especificaciones
- [SPEC-001: Autenticación](docs/Specs/SPEC-001-autenticacion.md)

## 🚀 Inicio Rápido

### Prerrequisitos
- Java 21+
- Node.js 18+
- Docker Desktop
- Git

### 1. Clonar Repositorio
```bash
git clone <repo-url>
cd my-finance-manager
```

### 2. Iniciar Infraestructura
```bash
cd infrastructure
cp .env.example .env
docker-compose up -d
```

### 3. Configurar Backend
```bash
cd apps/api-backend
cp .env.example .env
./gradlew bootRun
```

El backend estará disponible en `http://localhost:8080`

### 4. Configurar Mobile
```bash
cd apps/mobile-app
cp .env.example .env
npm install
npm start
```

## 📖 Uso

### API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Registro de usuario |
| POST | `/api/v1/auth/login` | Inicio de sesión |
| POST | `/api/v1/auth/refresh` | Refrescar token |
| GET | `/actuator/health` | Health check |

### pgAdmin
Accede a `http://localhost:5050` para gestionar la base de datos.
- Email: `admin@mfm.local`
- Password: `admin`

## 🧪 Testing

### Backend
```bash
cd apps/api-backend
./gradlew test
./gradlew jacocoTestReport
```

### Mobile
```bash
cd apps/mobile-app
npm test
npm run test:coverage
```

## 🔧 Calidad de Código

### Backend
```bash
./gradlew checkstyleMain
./gradlew spotlessApply
./gradlew spotbugsMain
```

### Mobile
```bash
npm run lint
npm run format
```

## 📱 Mobile App

La aplicación mobile está construida con Expo y React Native. Soporta iOS, Android y web.

```bash
cd apps/mobile-app
npm start

# Luego presiona:
# - i para iOS
# - a para Android
# - w para Web
```

## 🔐 Seguridad

- Contraseñas hasheadas con BCrypt
- Autenticación JWT con refresh tokens
- Validación de inputs en frontend y backend
- HTTPS requerido en producción

## 🤝 Contribuir

1. Crear rama feature (`git checkout -b feature/amazing-feature`)
2. Commit siguiendo Conventional Commits (`git commit -m 'feat: add amazing feature'`)
3. Push a la rama (`git push origin feature/amazing-feature`)
4. Crear Pull Request

## 📄 Licencia

Privado - Todos los derechos reservados

## 📞 Contacto

Para preguntas o soporte, contacta al equipo de desarrollo.

---

**Versión**: 0.1.0  
**Última Actualización**: Marzo 2026