# Usa una imagen de OpenJDK para Java 17 como base
FROM amazoncorretto:17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el JAR construido desde el sistema de archivos local al contenedor
COPY target/internship-backend-0.0.1-SNAPSHOT.jar .

# Copia el archivo .env al contenedor
COPY .env .

# Crea un script para cargar las variables de entorno
RUN echo '#!/bin/sh' > /app/run.sh && \
    echo 'export $(grep -v '^#' /app/.env | xargs)' >> /app/run.sh && \
    echo 'exec java -jar internship-backend-0.0.1-SNAPSHOT.jar' >> /app/run.sh && \
    chmod +x /app/run.sh

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["/app/run.sh"]
