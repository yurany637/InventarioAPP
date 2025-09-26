# Usa una imagen base de Java 17 optimizada
FROM eclipse-temurin:17-jdk-alpine

# Instalar dumb-init para manejo correcto de señales
RUN apk add --no-cache dumb-init

# Crea un usuario no-root para mayor seguridad
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de Maven wrapper
COPY --chown=spring:spring .mvn .mvn
COPY --chown=spring:spring mvnw pom.xml ./

# Da permisos de ejecución al script mvnw
RUN chmod +x ./mvnw

# Descarga las dependencias (optimización de caché de Docker)
RUN ./mvnw dependency:go-offline

# Copia el código fuente
COPY --chown=spring:spring src ./src

# Construye la aplicación
RUN ./mvnw clean package -DskipTests

# Cambia al usuario no-root
USER spring:spring

# Expone el puerto (Render usa la variable PORT)
EXPOSE 8080

# Configura variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de ejecución con dumb-init para manejo correcto de señales
ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-jar", "-Xmx400m", "-Dserver.port=${PORT:-8080}", "target/InventarioAPP-0.0.1-SNAPSHOT.jar"]