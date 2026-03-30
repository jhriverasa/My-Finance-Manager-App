# Infrastructure

This directory contains all infrastructure configuration for local development and deployment.

## Local Development

### Prerequisites
- Docker Desktop (or Docker Engine + Docker Compose)
- Git

### Quick Start

1. **Copy environment file**:
   ```bash
   cp .env.example .env
   ```

2. **Start services**:
   ```bash
   docker-compose up -d
   ```

3. **Verify services are running**:
   ```bash
   docker-compose ps
   ```

### Services

| Service | Port | Description |
|---------|------|-------------|
| PostgreSQL | 5432 | Primary database |
| pgAdmin | 5050 | Database management UI |

### Accessing Services

- **PostgreSQL**: `localhost:5432`
  - Database: `mfm_db`
  - User: `mfm_user`
  - Password: `mfm_password` (or set in `.env`)

- **pgAdmin**: `http://localhost:5050`
  - Email: `admin@mfm.local` (or set in `.env`)
  - Password: `admin` (or set in `.env`)

### Stopping Services

```bash
docker-compose down
```

To also remove volumes (⚠️ **deletes all data**):
```bash
docker-compose down -v
```

### Database Initialization

The `init.sql` script runs automatically on first container creation. It:
- Creates enum types for data integrity
- Creates all tables (user, account, subscription, transaction, crypto_holding)
- Sets up indexes for performance
- Creates triggers for `updated_at` timestamps

### Viewing Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f postgres
docker-compose logs -f pgadmin
```

### Database Backup

```bash
# Backup database
docker exec mfm-postgres pg_dump -U mfm_user mfm_db > backup.sql

# Restore database
docker exec -i mfm-postgres psql -U mfm_user mfm_db < backup.sql
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `POSTGRES_DB` | Database name | `mfm_db` |
| `POSTGRES_USER` | Database user | `mfm_user` |
| `POSTGRES_PASSWORD` | Database password | `mfm_password` |
| `POSTGRES_PORT` | Database port | `5432` |
| `PGADMIN_EMAIL` | pgAdmin login email | `admin@mfm.local` |
| `PGADMIN_PASSWORD` | pgAdmin login password | `admin` |
| `PGADMIN_PORT` | pgAdmin port | `5050` |

## Troubleshooting

### Port Already in Use

If you get a port conflict error, either:
1. Stop the service using that port
2. Change the port in `.env` and `docker-compose.yml`

### Container Won't Start

Check logs:
```bash
docker-compose logs postgres
```

### Reset Database

```bash
docker-compose down -v
docker-compose up -d
```

## Production Deployment

For production, consider:
- Using managed PostgreSQL (AWS RDS, Google Cloud SQL, Supabase)
- Removing pgAdmin or securing it properly
- Using secrets management (not `.env` files)
- Setting up automated backups
- Configuring SSL/TLS connections