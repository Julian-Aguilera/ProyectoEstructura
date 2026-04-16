# Proyecto Java

Este es un proyecto Java básico creado con Maven.

## Estructura del proyecto

- `src/main/java/`: Código fuente principal
- `src/test/java/`: Pruebas unitarias
- `pom.xml`: Archivo de configuración de Maven

## Cómo ejecutar

1. Compilar: `mvn compile`
2. Ejecutar: `mvn exec:java -Dexec.mainClass="com.example.Main"`
3. Ejecutar pruebas: `mvn test`

## Requisitos

- Java 11 o superior
- Maven

  /*
INSTRUCCIONES DEL CONTROLLER

Este controlador permite interactuar con el sistema
AutoRescate 24/7 desde consola.

Opciones disponibles en el menú principal:

1. Registrar solicitud
   - Permite ingresar una nueva solicitud de servicio.
   - Se solicita nombre del cliente.
   - Se selecciona tipo de servicio.
   - Se selecciona prioridad (normal o alta).

2. Atender siguiente solicitud
   - Atiende la próxima solicitud pendiente.
   - Primero salen las prioritarias.
   - Luego las normales por orden de llegada.

3. Finalizar solicitud actual
   - Marca como terminada la solicitud en curso.
   - Libera el sistema para atender otra.

4. Ver cola normal
   - Muestra solicitudes comunes pendientes.

5. Ver cola prioritaria
   - Muestra solicitudes críticas o urgentes.

6. Ver historial
   - Muestra acciones realizadas en el sistema.

7. Deshacer última acción
   - Revierte la última acción registrada.

0. Salir
   - Cierra el programa.
