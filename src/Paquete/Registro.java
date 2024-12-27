package Paquete;

import java.sql.*;
import java.time.LocalDate;

public class Registro {
    private Statement stmt;
    private ResultSet recordset;


    public void registroUsuario(Conexion obj1, String idUsuarios, String nombre, String apellido, String direccion, String telefono, String correoElectronico) throws Exception {
        Connection cnn = null;
        SetterYGetter obj = new SetterYGetter();
        try {
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            String varsql = "Insert INTO Usuarios(idUsuarios, nombre, apellido, direccion, telefono, CorreoElectronico) values('"+idUsuarios+"','"+nombre+"','"+apellido+"','"+direccion+"','"+telefono+"','"+correoElectronico+"')";
            stmt.executeUpdate(varsql);
            cnn.close();
        }catch (SQLException e){
            throw new Exception(e);
        }
    }

    public boolean verificar(Conexion obj1, String idUsuarios)throws Exception{
        Connection cnn = null;
        try{
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            String cadsql="Select * from Usuarios where idUsuarios like '"+idUsuarios+"'";
            recordset = stmt.executeQuery(cadsql);
            boolean registros = recordset.next();
            cnn.close();
            return registros;
        }catch (SQLException e){
            throw new Exception(e);

        }

    }
    public boolean verificarIdPrestamo(Conexion obj1, String idPrestamos)throws Exception{
        Connection cnn = null;
        try {
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            String cadSql = "Select * from Prestamos where idPrestamos like '"+idPrestamos+"'";
            recordset = stmt.executeQuery(cadSql);
            boolean registros = recordset.next();
            cnn.close();
            return registros;
        }catch (SQLException e){
            throw new Exception(e);
        }

    }

    public void registrarPrestamo(Conexion obj1, String idPrestamos, String idLibro, String idUsuarios,String  fechaPrestamo, String  fechaDevolucion )throws Exception{
        Connection cnn = null;
        Statement stmt = null;
        try {
            cnn = obj1.establecer_conexion();
            cnn.setAutoCommit(false); // Desactivar el autocommit para manejar transacciones

            stmt = cnn.createStatement();
            String cadsql = "SELECT disponibilidad FROM Libros WHERE idLibro = '" + idLibro + "'";
            ResultSet recordset = stmt.executeQuery(cadsql);
            if (recordset.next()) {
                String disponibilidad = recordset.getString("disponibilidad");
                if (disponibilidad.equals("no")) {
                    throw new Exception("El libro no se encuentra disponible");
                } else {
                    String varSql = "INSERT INTO Prestamos (idPrestamos, idLibro, idUsuarios, fechaPrestamo, fechaDevolucion) VALUES ('"
                            + idPrestamos + "','" + idLibro + "','" + idUsuarios + "','" + fechaPrestamo + "','" + fechaDevolucion + "')";
                    stmt.executeUpdate(varSql);

                    String disSql = "UPDATE Libros SET disponibilidad = 'no' WHERE idLibro = '" + idLibro + "'";
                    stmt.executeUpdate(disSql);

                    String vecSql = "UPDATE Libros SET vecesPedido = vecesPedido + 1 WHERE idLibro = '" + idLibro + "'";
                    stmt.executeUpdate(vecSql);

                    String prSql = "UPDATE Usuarios SET prestamosRealizados = prestamosRealizados + 1 WHERE idUsuarios = '" + idUsuarios + "'";
                    stmt.executeUpdate(prSql);

                    cnn.commit(); // Realizar el commit si todo ha ido bien
                    throw new Exception("¡Prestamo Realizado con Éxito!");
                }
            } else {
                throw new Exception("El libro no existe en la base de datos");
            }
        } catch (Exception e) {
            if (cnn != null) {
                cnn.rollback(); // Si hay excepción, realizar rollback para deshacer los cambios
            }
            throw e; // Relanzar la excepción para que se propague al código que llama al método
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (cnn != null) {
                cnn.setAutoCommit(true); // Restaurar el autocommit a su valor original
                cnn.close();
            }
        }
    }
    public void registrarDevolucion(Conexion obj1, String idLibro) throws Exception{
        Connection cnn = null;
        try {
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            String devSql =  "UPDATE Libros SET disponibilidad='Sí' where idLibro like '"+idLibro+"'";
            stmt.executeUpdate(devSql);
            cnn.close();
            System.out.println("libro Devuelto, vuelva pronto");
        }catch (SQLException e){
            throw new Exception(e);
        }

    }




}
