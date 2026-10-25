# Taller de Persistencia de Datos – Gestión de Productos

Aplicación de consola en **Java** con operaciones **CRUD** (Create, Read, Update, Delete) que guarda los datos en una base de datos **SQLite** (`productos.db`), de modo que la información se conserva al cerrar y volver a ejecutar el programa.

## Abrir y ejecutar (sin configurar nada)

1. Haz clic en el botón:

   [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/Nevits2024/TALLER1)

2. Espera a que el Codespace termine de cargar (la primera vez tarda uno o dos minutos).
3. En la terminal del Codespace, ejecuta:

   ```bash
   mvn -q exec:java -Dexec.mainClass=org.example.Main
   ```

4. Usa el menú para registrar, consultar, actualizar o eliminar productos.

## Probar la persistencia

1. Registra uno o más productos.
2. Elige la opción **5. Salir**.
3. Vuelve a ejecutar el comando del paso 3.
4. Elige **2. Consultar productos**: los datos siguen guardados.

> **Importante:** los datos se guardan dentro de *ese* Codespace. Si cierras la pestaña, **reabre el mismo Codespace** desde <https://github.com/codespaces> en lugar de crear uno nuevo con el botón; uno nuevo empieza vacío.

## Tecnologías

- Java 21
- Maven
- SQLite (`sqlite-jdbc`)
- Dev Container para configuración automática del entorno

## Estructura de la tabla `productos`

| Campo    | Tipo    | Descripción                |
|----------|---------|----------------------------|
| codigo   | TEXT    | Clave primaria del producto |
| nombre   | TEXT    | Nombre del producto        |
| precio   | REAL    | Precio (no negativo)       |
| cantidad | INTEGER | Stock disponible (no negativo) |
