# Usa una imagen base de Java 17, que es un entorno de ejecución ya listo.
FROM eclipse-temurin:17-jdk-focal

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia los archivos necesarios para el build.
COPY .mvn .mvn
COPY mvnw pom.xml ./

# AÑADE ESTA LÍNEA para dar permisos de ejecución al script mvnw
RUN chmod +x ./mvnw

# Descarga las dependencias de Maven. Esto se hace en un paso separado para optimizar la caché de Docker.
RUN ./mvnw dependency:go-offline

# Copia el código fuente completo del proyecto.
COPY src ./src

# Empaqueta tu aplicación en un archivo .jar.
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080 para que la aplicación sea accesible desde el exterior.
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor inicie.
ENTRYPOINT ["java", "-jar", "target/InventarioAPP-0.0.1-SNAPSHOT.jar"]