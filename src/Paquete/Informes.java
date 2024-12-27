package Paquete;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;

public class Informes {
    private Statement stmt;
    private ResultSet recordset;

    public int generarInformePrestamos(Conexion obj)throws Exception {
        Connection cnn = null;
        try {
            cnn = obj.establecer_conexion();
            stmt = cnn.createStatement();
            String consultaCantidadPrestamos = "SELECT COUNT(*) as totalPrestamos  FROM Prestamos";
            recordset = stmt.executeQuery(consultaCantidadPrestamos);
            int total=0;
            if (recordset.next()){
                total = recordset.getInt("totalPrestamos");
            }
            cnn.close();
            return total;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public LinkedList<SetterYGetter> Libro_Popular(Conexion obj) throws Exception {
        Connection cnn = null;
        SetterYGetter busqueda = new SetterYGetter();
        LinkedList<SetterYGetter> listaBusqueda = new LinkedList<>();
        try {
            cnn = obj.establecer_conexion();
            stmt = cnn.createStatement();

            recordset = stmt.executeQuery("select * from Libros where vecesPedido<>0 order by vecesPedido desc");

            if(recordset.next()) {

                busqueda.setIdLibro(recordset.getString("idlibro"));
                busqueda.setTitulo(recordset.getString("titulo"));
                busqueda.setAutor(recordset.getString("autor"));
                busqueda.setGenero(recordset.getString("genero"));
                busqueda.setDisponibilidad(recordset.getString("disponibilidad"));
                busqueda.setVecesPedido(recordset.getInt("vecesPedido"));
                listaBusqueda.add(busqueda);
                cnn.close();
                return listaBusqueda;
            }
            return listaBusqueda;
        } catch (SQLException e) {
            cnn.close();
            throw new Exception("Error...en la consulta de unico registro");
        }

    }
    public LinkedList<SetterYGetter> usuarioMasPrestamos(Conexion obj) throws Exception {
        Connection cnn = null;
        SetterYGetter busqueda = new SetterYGetter();
        LinkedList<SetterYGetter> listaBusqueda = new LinkedList<>();
        try {
            cnn = obj.establecer_conexion();
            stmt = cnn.createStatement();
            recordset = stmt.executeQuery("select * from Usuarios order by prestamosRealizados desc");
            if (recordset.next()){
                busqueda.setNombre(recordset.getString("nombre"));
                busqueda.setApellido(recordset.getString("apellido"));
                busqueda.setPrestamosRealizados(recordset.getInt("prestamosRealizados"));
                listaBusqueda.add(busqueda);
                cnn.close();
                return listaBusqueda;

            }
            return listaBusqueda;
        }catch (SQLException e){
            throw new Exception(e);
        }
    }
    public void enviarNotificacion(Conexion obj1, String idUsuarios, String idPrestamo) throws Exception{
        Connection cnn = null;
        try {
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            String cadsql = "SELECT correoElectronico FROM Usuarios WHERE idUsuarios = '" + idUsuarios + "'";
            String cadsql2 = "SELECT fechaDevolucion FROM Prestamos WHERE idUsuarios = '" + idUsuarios + "'";
            String cadsql3 = "SELECT idLibro FROM Prestamos WHERE idUsuarios = '" + idUsuarios + "'";
            String cadsql4 = "SELECT idPrestamos FROM Prestamos WHERE idPrestamos = '" + idPrestamo + "'";

            ResultSet recordset = stmt.executeQuery(cadsql);
            ResultSet recordset2 = stmt.executeQuery(cadsql2);
            ResultSet recordset3 = stmt.executeQuery(cadsql3);
            ResultSet recordset4 = stmt.executeQuery(cadsql4);
            String host = null;
            if (recordset.next() && recordset2.next() && recordset3.next() && recordset4.next()) {
                String correo = recordset.getString("CorreoElectronico");
                String fechaDevolucion = recordset2.getString("fechaDevolucion");
                String idLibro = recordset3.getString("idLibro");
                String idPrestamos = recordset4.getString("idPrestamos");
                host = "smtp.gmail.com";
                String user = "bibliotecautpds3@gmail.com";
                String pass = "txzjbywnkfnsujdo";
                String to = correo;
                String from = "bibliotecautpds3@gmail.com";
                String subject = "Devolución de Libro";
                String messageText = "Estimado usuario, se le recuerda que debe devolver el libro" + idLibro + " que tiene en préstamo segun el codigo" + idPrestamos + "  fecha de devolución: " + fechaDevolucion;


                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.port", "587");



                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(user, pass);
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject(subject);
                    message.setText(messageText);
                    Transport.send(message);
                    System.out.println("Correo electrónico enviado exitosamente.");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
            } else {
                throw new Exception("No se encontró información del usuario o préstamo.");
            }

        }catch (SQLException e){
            throw new Exception(e);
        }

    }
}
