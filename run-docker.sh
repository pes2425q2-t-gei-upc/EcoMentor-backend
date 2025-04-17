#!/bin/bash

# Intentar detener PostgreSQL, pero si falla no pasa nada
echo "Deteniendo PostgreSQL..."
sudo systemctl stop postgresql || true

# Intentar detener MongoDB, pero si falla no pasa nada
echo "Deteniendo MongoDB..."
sudo systemctl stop mongod || true

# Levantar los contenedores con Docker Compose
echo "Levantando contenedores con Docker Compose..."
docker compose up -d

# Verificar el estado de los contenedores
echo "Verificando el estado de los contenedores Docker..."
docker ps

# Indicar que el script terminó
echo "Contenedores Docker levantados y servicios locales detenidos."
