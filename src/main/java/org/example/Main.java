package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:sqlite:productos.db";

    public static void main(String[] args) {
        crearTabla();
        Scanner sc = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Registrar producto (Create)");
            System.out.println("2. Consultar productos (Read)");
            System.out.println("3. Actualizar producto (Update)");
            System.out.println("4. Eliminar producto (Delete)");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> registrar(sc);
                case 2 -> listar(sc);
                case 3 -> actualizar(sc);
                case 4 -> eliminar(sc);
            }
        } while (op != 5);
        sc.close();
    }

    public static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                "codigo TEXT PRIMARY KEY, " +
                "nombre TEXT NOT NULL, " +
                "precio REAL NOT NULL, " +
                "cantidad INTEGER NOT NULL)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    // 1. CREATE
    public static void registrar(Scanner sc) {
        System.out.print("Código: "); String cod = sc.nextLine();
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Precio: "); double pre = sc.nextDouble();
        System.out.print("Cantidad: "); int cant = sc.nextInt();
        sc.nextLine();

        if (pre < 0 || cant < 0) {
            System.out.println("Error: No se permiten valores negativos.");
            return;
        }

        // Especificamos las columnas explícitamente para evitar errores de mapeo
        String sql = "INSERT INTO productos (codigo, nombre, precio, cantidad) VALUES (?, ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(URL);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cod);
            ps.setString(2, nom);
            ps.setDouble(3, pre);
            ps.setInt(4, cant);
            ps.executeUpdate();
            System.out.println("¡Producto registrado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al registrar (¿Código repetido?): " + e.getMessage());
        }
    }

    // 2. READ
// 2. READ (Listar todo o buscar por código)
    public static void listar(Scanner sc) {
        System.out.print("¿Deseas buscar por código específico? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingresa el código: "); String cod = sc.nextLine();
            String sql = "SELECT * FROM productos WHERE codigo = ?";
            try (Connection c = DriverManager.getConnection(URL); PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, cod);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Encontrado -> " + rs.getString("codigo") + " | " + rs.getString("nombre") + " | $" + rs.getDouble("precio") + " | Stock: " + rs.getInt("cantidad"));
                } else { System.out.println("No existe un producto con ese código."); }
            } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        } else {
            String sql = "SELECT * FROM productos";
            try (Connection c = DriverManager.getConnection(URL); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                System.out.println("\n--- INVENTARIO ---");
                while (rs.next()) {
                    System.out.println("Código: " + rs.getString("codigo") + " | " + rs.getString("nombre") + " | $" + rs.getDouble("precio") + " | Stock: " + rs.getInt("cantidad"));
                }
            } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        }
    }
    // 3. UPDATE
    public static void actualizar(Scanner sc) {
        System.out.print("Código del producto a actualizar: "); String cod = sc.nextLine();
        System.out.print("Nuevo precio: "); double pre = sc.nextDouble();
        System.out.print("Nueva cantidad: "); int cant = sc.nextInt();
        sc.nextLine();

        if (pre < 0 || cant < 0) { System.out.println("Error: Valores no pueden ser negativos."); return; }

        String sql = "UPDATE productos SET precio = ?, cantidad = ? WHERE codigo = ?";
        try (Connection c = DriverManager.getConnection(URL); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, pre); ps.setInt(2, cant); ps.setString(3, cod);
            if (ps.executeUpdate() > 0) System.out.println("¡Actualizado con éxito!");
            else System.out.println("Producto no encontrado.");
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    // 4. DELETE
    public static void eliminar(Scanner sc) {
        System.out.print("Código del producto a eliminar: "); String cod = sc.nextLine();
        String sql = "DELETE FROM productos WHERE codigo = ?";
        try (Connection c = DriverManager.getConnection(URL); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cod);
            if (ps.executeUpdate() > 0) System.out.println("¡Eliminado con éxito!");
            else System.out.println("Producto no encontrado.");
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }
}