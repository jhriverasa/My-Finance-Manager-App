# SPEC-001: Autenticación y Autorización

## Descripción

Este documento especifica los requisitos funcionales y técnicos para el sistema de autenticación y autorización de MyFinanceManager.

## Alcance

- Registro de nuevos usuarios
- Login/autenticación
- Gestión de tokens JWT
- Protección de rutas
- Cierre de sesión

## User Stories

### US-001: Registro de Usuario

**Como** usuario nuevo,  
**quiero** poder registrarme con mi correo electrónico y contraseña,  
**para** acceder a las funcionalidades de la aplicación.

**Criterios de Aceptación:**
- [ ] El usuario debe proporcionar email, contraseña, nombre y apellido
- [ ] El email debe ser único en el sistema
- [ ] La contraseña debe tener al menos 8 caracteres
- [ ] Después del registro exitoso, el usuario recibe tokens de acceso
- [ ] El sistema valida el formato del email
- [ ] El sistema muestra mensajes de error claros

**Flujo Principal:**
1. Usuario ingresa a la pantalla de registro
2. Usuario completa el formulario (email, contraseña, nombre, apellido)
3. Sistema valida los datos
4. Sistema crea la cuenta y genera tokens JWT
5. Usuario es redirigido a la pantalla principal

**Flujos Alternos:**
- Email ya registrado → Mostrar error "El email ya está registrado"
- Contraseña muy corta → Mostrar error "La contraseña debe tener al menos 8 caracteres"
- Email inválido → Mostrar error "Formato de email inválido"

---

### US-002: Inicio de Sesión

**Como** usuario registrado,  
**quiero** poder iniciar sesión con mis credenciales,  
**para** acceder a mi información financiera.

**Criterios de Aceptación:**
- [ ] El usuario debe proporcionar email y contraseña
- [ ] Las credenciales deben ser validadas contra la base de datos
- [ ] Después del login exitoso, el usuario recibe tokens JWT
- [ ] El token de acceso se almacena de forma segura
- [ ] El sistema muestra mensajes de error genéricos por seguridad

**Flujo Principal:**
1. Usuario ingresa a la pantalla de login
2. Usuario proporciona email y contraseña
3. Sistema valida las credenciales
4. Sistema genera y retorna tokens JWT
5. Usuario es redirigido a la pantalla principal

**Flujos Alternos:**
- Credenciales inválidas → Mostrar error "Credenciales inválidas"
- Usuario no encontrado → Mostrar error "Credenciales inválidas" (no revelar si el usuario existe)

---

### US-003: Cierre de Sesión

**Como** usuario autenticado,  
**quiero** poder cerrar sesión,  
**para** proteger mi información cuando no esté usando la aplicación.

**Criterios de Aceptación:**
- [ ] El usuario puede cerrar sesión desde cualquier pantalla
- [ ] Al cerrar sesión, los tokens son eliminados
- [ ] El usuario es redirigido a la pantalla de login
- [ ] No se puede acceder a pantallas protegidas después de cerrar sesión

---

### US-004: Refresco de Token

**Como** usuario autenticado,  
**quiero** que mi sesión se mantenga activa automáticamente,  
**para** no tener que iniciar sesión repetidamente.

**Criterios de Aceptación:**
- [ ] El sistema usa refresh tokens para obtener nuevos access tokens
- [ ] El refresh token tiene una expiración más larga (7 días)
- [ ] El access token tiene una expiración más corta (24 horas)
- [ ] El refresco es transparente para el usuario

---

## Especificación Técnica

### API Endpoints

#### POST `/api/v1/auth/register`
Registra un nuevo usuario.

**Request Body:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123",
  "firstName": "Juan",
  "lastName": "Pérez"
}
```

**Response (201 Created):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "message": "User registered successfully"
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validation failed",
  "message": "Email already registered"
}
```

---

#### POST `/api/v1/auth/login`
Autentica un usuario.

**Request Body:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "message": "Login successful"
}
```

---

#### POST `/api/v1/auth/refresh?refreshToken=xxx`
Refresca el access token.

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "message": "Token refreshed successfully"
}
```

---

#### GET `/api/v1/auth/validate?token=xxx`
Valida si un token es válido.

**Response (200 OK):**
```json
true
```

---

### JWT Configuration

| Parameter | Value | Description |
|-----------|-------|-------------|
| Algorithm | HS256 | HMAC with SHA-256 |
| Access Token Expiration | 24 hours | Short-lived token |
| Refresh Token Expiration | 7 days | Long-lived token |
| Secret Key | Environment variable | Must be changed in production |

### Token Structure

```json
{
  "sub": "1",
  "email": "usuario@ejemplo.com",
  "type": "access",
  "iat": 1699000000,
  "exp": 1699086400
}
```

---

## Mobile Implementation

### Screens

1. **LoginScreen** - Formulario de inicio de sesión
2. **RegisterScreen** - Formulario de registro
3. **AuthLayout** - Layout compartido para pantallas de auth

### State Management

```typescript
// store/authStore.ts
interface AuthStore {
  user: User | null;
  accessToken: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  login: (tokens: AuthTokens) => Promise<void>;
  register: (data: RegisterData) => Promise<void>;
  logout: () => void;
  refreshTokens: () => Promise<void>;
}
```

### Secure Storage

Los tokens se almacenan usando `expo-secure-store` para mayor seguridad en el dispositivo.

---

## Security Considerations

1. **Contraseñas**: Nunca se almacenan en texto plano. Se usa BCrypt para hashing.
2. **Tokens**: Se transmiten en el header `Authorization: Bearer <token>`.
3. **HTTPS**: Requerido en producción para todas las comunicaciones.
4. **Rate Limiting**: Se implementará para prevenir ataques de fuerza bruta.
5. **Input Validation**: Todos los inputs son validados en backend y frontend.

---

## Testing

### Backend Tests

```java
// Test de registro exitoso
@Test
void shouldRegisterUserSuccessfully() {
    RegisterRequest request = new RegisterRequest(
        "test@example.com", "password123", "Test", "User"
    );
    
    RegistrationResult result = registerUserUseCase.register(
        request.email(), request.password(), 
        request.firstName(), request.lastName()
    );
    
    assertTrue(result.success());
    assertNotNull(result.user().getId());
}
```

### Mobile Tests

```typescript
// Test de login exitoso
describe('AuthScreen', () => {
  it('should login successfully with valid credentials', async () => {
    const { getByTestId } = render(<LoginScreen />);
    
    fireEvent.changeText(getByTestId('email-input'), 'test@example.com');
    fireEvent.changeText(getByTestId('password-input'), 'password123');
    fireEvent.press(getByTestId('login-button'));
    
    await waitFor(() => {
      expect(authStore.login).toHaveBeenCalled();
    });
  });
});
```

---

## Referencias

- [RFC 7519 - JWT](https://tools.ietf.org/html/rfc7519)
- [OWASP Authentication Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html)
- [Spring Security Documentation](https://spring.io/projects/spring-security)