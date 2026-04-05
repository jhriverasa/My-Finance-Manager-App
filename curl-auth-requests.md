# 📡 Comandos CURL para probar Endpoints de Autenticación

Todos los endpoints están corregidos y listos para usar.

---

## ✅ **ENDPOINTS DISPONIBLES**

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/api/auth/register` | POST | Registro de nuevo usuario |
| `/api/auth/login` | POST | Inicio de sesión |
| `/api/auth/refresh` | POST | Renovar token de acceso |
| `/api/auth/validate` | GET | Validar token JWT |

---

## 🚀 **EJECUTAR ESTOS COMANDOS EN ORDEN**

---

### 1. **Registrar un nuevo usuario**
```bash
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{ \"email\": \"test@example.com\", \"password\": \"Password123\", \"firstName\": \"Juan\", \"lastName\": \"Perez\" }"
```

✅ **Respuesta Esperada (201 Created):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "User registered successfully"
}
```

---

### 2. **Iniciar Sesión**
```bash
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{ \"email\": \"test@example.com\", \"password\": \"Password123\" }"
```

✅ **Respuesta Esperada (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

---

### 3. **Renovar Token de Acceso**
*(Reemplaza `TU_REFRESH_TOKEN` por el valor que obtuviste en login o registro)*
```bash
curl -X POST http://localhost:8080/api/auth/refresh ^
  -H "Content-Type: application/json" ^
  -d "{ \"refreshToken\": \"TU_REFRESH_TOKEN\" }"
```

✅ **Respuesta Esperada (200 OK):**
```json
{
  "accessToken": "NUEVO_TOKEN_GENERADO...",
  "refreshToken": "NUEVO_REFRESH_TOKEN...",
  "message": "Token refreshed successfully"
}
```

---

### 4. **Validar Token**
*(Reemplaza `TU_TOKEN` por el token a validar)*
```bash
curl -X GET "http://localhost:8080/api/auth/validate?token=TU_TOKEN"
```

✅ **Respuesta Esperada (200 OK):**
```json
{
  "valid": true,
  "message": "Token is valid"
}
```

---

## ❌ **ERRORES ESPERADOS**

### Login con credenciales incorrectas (401 Unauthorized):
```bash
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{ \"email\": \"test@example.com\", \"password\": \"MalPassword\" }"
```

### Registrar email ya existente (400 Bad Request):
```bash
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{ \"email\": \"test@example.com\", \"password\": \"Password123\", \"firstName\": \"Otro\", \"lastName\": \"Usuario\" }"
```

---

## 🛠️ **NOTAS:**
- Todos los endpoints son públicos (no requieren autenticación previa)
- Las contraseñas deben tener mínimo 8 caracteres
- Todos los errores retornan mensaje descriptivo en el body
- Se usan códigos HTTP correctos: 200, 201, 400, 401