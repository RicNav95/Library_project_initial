package Paquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
        private String nombre_bd;

        private Connection cnn;
        public Conexion(String nombre_bd){
            this.nombre_bd=nombre_bd;
        }
        public Connection establecer_conexion() throws Exception {
            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                cnn= DriverManager.getConnection("jdbc:ucanaccess://"+nombre_bd);
                return cnn;
            }
            catch (ClassNotFoundException e){
                throw new Exception ("\nPara el programador: "+e+
                        "\n\nPara el usuario: Error...No se pudo cargar el driver puente Jdbc_Odbc");
            }
            catch (SQLException e){
                throw new Exception ("\nPara el programador: "+e+
                        "\n\nPara el usuario: Error... No se pudo establecer la conexion");
            }
        }


}
