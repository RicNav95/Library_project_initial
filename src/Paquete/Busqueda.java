package Paquete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Busqueda {
    private Statement stmt;
    private ResultSet recordset;
    public LinkedList <SetterYGetter> consulta_libros (Conexion obj3, String consulta) throws Exception {
        Connection cnn=null;
        LinkedList<SetterYGetter> Listabusqueda = new LinkedList<>();

        try {
            cnn = obj3.establecer_conexion();
            stmt = cnn.createStatement();

            recordset = stmt.executeQuery("select * from Libros where titulo = '"+consulta+"' or genero ='"+consulta+"' or autor = '"+consulta+"'");

            while (recordset.next()) {
                SetterYGetter busqueda= new SetterYGetter();

                busqueda.setIdLibro(recordset.getString("idlibro"));
                busqueda.setTitulo(recordset.getString("titulo"));
                busqueda.setAutor(recordset.getString("autor"));
                busqueda.setGenero(recordset.getString("genero"));
                busqueda.setDisponibilidad(recordset.getString("disponibilidad"));
                Listabusqueda.add(busqueda);

            }
            cnn.close();
            return Listabusqueda;
        }
        catch (SQLException e) {
            cnn.close();
            throw new Exception ("Error...en la consulta de todos los registros");
        }
    }
}
