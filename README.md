## ⚙️ Spring Cloud Config

Esta API soporta configuración centralizada mediante **Spring Cloud Config Server**.

Por defecto arranca con la configuración local de `application.properties` (MySQL), pero si se dispone de un Config Server propio, basta con apuntar la URL del repositorio de configuración y la API tomará los valores según el perfil activo.

### Perfiles disponibles

| Perfil | Base de datos | Uso recomendado |
|--------|--------------|-----------------|
| `dev`  | H2 en memoria | Desarrollo local |
| `prod` | MySQL / MariaDB | Producción |

Para cambiar de perfil, edita `application.properties`:

```properties
spring.profiles.active=dev   # o prod
```

### Variables de entorno (perfil `prod`)

El perfil de producción requiere las siguientes variables de entorno definidas antes de arrancar la API. En **PowerShell**:

```powershell
$env:KOMOREBI_DB_HOST="localhost"
$env:KOMOREBI_DB_NAME="komorebidb"
$env:KOMOREBI_DB_USER="root"
$env:KOMOREBI_DB_PASSWORD="tu_contraseña"
```

> ⚠️ Las variables deben definirse en la **misma sesión de terminal** desde la que se arranca la API.

---

## 🧪 Testing integrado con GitHub Actions

Cada vez que se abre una **Pull Request** hacia `master` o `develop`, se ejecuta automáticamente la colección de tests de **Hoppscotch** mediante el workflow definido en `.github/workflows/`.

```
Pull Request → GitHub Actions → Hoppscotch Tests → ✅ Merge permitido / ❌ Merge bloqueado
```

### Requisitos para que funcione

Los siguientes archivos deben estar en la raíz del proyecto:

```
📁 proyecto/
├── hoppscotch-collection.json   ← colección de tests
├── hoppscotch-env.json          ← variables de entorno para los tests
└── .github/
    └── workflows/
        └── hoppscotch-tests.yml
```

### Cobertura de tests

- **Auth** — registro, login (201, 400, 401, 409)
- **Schools** — CRUD completo (200, 201, 400, 404, 204)
- **Projects** — CRUD completo (200, 201, 400, 404, 204)

---

## 🚀 Modo de arranque

### Desarrollo (H2)

```bash
./mvnw spring-boot:run
```

### Producción (MySQL)

```powershell
# 1. Define las variables de entorno
$env:KOMOREBI_DB_HOST="localhost"
$env:KOMOREBI_DB_NAME="komorebidb"
$env:KOMOREBI_DB_USER="root"
$env:KOMOREBI_DB_PASSWORD="tu_contraseña"

# 2. Arranca la API
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8081`.

---

## 🛠️ Stack tecnológico

- **Java 21** + **Spring Boot 3.4**
- **Spring Security** + **JWT**
- **Spring Cloud Config**
- **Spring Data JPA** + **Hibernate**
- **MySQL** / **H2**
- **Maven**

---

## 👥 Autores

| Nombre | Rol |
|--------|-----|
| María del Mar Andrés | Desarrolladora |
| Marcos Martínez | Desarrollador |
| Jorge Díez | Desarrollador |






