# Use the MySQL 8.0.39 image as the base
FROM mysql:8.0.39

# IMPORTANT: Replace the root password with a stronger one.
ENV MYSQL_ROOT_PASSWORD=password

# Expose MySQL port 3306 to allow connections from outside the container
EXPOSE 3306

# Copy the SQL script to the MySQL entrypoint directory
# This ensures the script will run when the container is initialized for the first time
COPY ./create_database.sql /docker-entrypoint-initdb.d/