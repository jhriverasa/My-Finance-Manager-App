# ADR-003: Herramientas de Calidad de Código

## Estado
**Aceptado**

## Contexto
MyFinanceManager requiere herramientas que aseguren:
- Consistencia en el estilo de código
- Detección temprana de errores
- Mantenibilidad del código
- Buenas prácticas de desarrollo

## Decisión

### Backend (Java/Spring Boot)

| Herramienta | Propósito | Configuración |
|-------------|-----------|---------------|
| **Checkstyle** | Verificación de estilo de código | Google Java Style Guide |
| **Spotless** | Formateo automático | Pre-commit hook |
| **SpotBugs** | Detección de bugs potenciales | Nivel medio de severidad |
| **JaCoCo** | Cobertura de tests | Reportes en CI |

### Mobile (React Native/Expo)

| Herramienta | Propósito | Configuración |
|-------------|-----------|---------------|
| **ESLint** | Linting de JavaScript/TypeScript | Airbnb + React Native |
| **Prettier** | Formateo de código | Configuración estándar |
| **Husky** | Git hooks | pre-commit con lint-staged |
| **lint-staged** | Ejecutar linters en archivos staged | ESLint + Prettier |

### Convención de Commits

Se adopta **Conventional Commits**:

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

**Tipos**:
- `feat:` - Nueva funcionalidad
- `fix:` - Corrección de bug
- `docs:` - Cambios en documentación
- `style:` - Cambios de formato (no afectan lógica)
- `refactor:` - Refactorización
- `test:` - Agregar/modificar tests
- `chore:` - Tareas de mantenimiento

**Ejemplos**:
```
feat(auth): add JWT token refresh endpoint
fix(subscriptions): correct billing date calculation
docs: update API documentation
refactor(domain): extract payment validation logic
```

### Testing

| Capa | Framework | Propósito |
|------|-----------|-----------|
| Backend Unit | JUnit 5 + Mockito | Tests de dominio y casos de uso |
| Backend Integration | Testcontainers + Spring Boot Test | Tests de integración con BD |
| Mobile Unit | Jest + React Native Testing Library | Tests de componentes y hooks |
| Mobile E2E | Detox (futuro) | Tests end-to-end |

## Consecuencias

### Positivas
- ✅ Código consistente y legible
- ✅ Detección temprana de errores
- ✅ Tests automatizados desde el inicio
- ✅ Commits semánticos para changelog automático

### Negativas
- ⚠️ Tiempo adicional en CI/CD
- ⚠️ Curva de aprendizaje inicial
- ⚠️ Posible resistencia al cambio de estilo

### Riesgos
- 🟡 Configuración compleja inicial
- 🟡 Falsos positivos en herramientas de análisis

## Implementación

### Gradle (Backend)
```groovy
plugins {
    id 'checkstyle'
    id 'com.diffplug.spotless'
    id 'com.github.spotbugs'
    id 'jacoco'
}
```

### Package.json (Mobile)
```json
{
  "devDependencies": {
    "eslint": "^8.0.0",
    "prettier": "^3.0.0",
    "husky": "^8.0.0",
    "lint-staged": "^14.0.0"
  }
}
```

## Referencias
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Checkstyle](https://checkstyle.org/)
- [Spotless Gradle Plugin](https://github.com/diffplug/spotless)
- [ESLint React Native Config](https://github.com/Intellicode/eslint-config-react-native-airbnb)