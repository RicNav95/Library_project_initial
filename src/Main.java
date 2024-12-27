/*
* Ricardo Navarro 8-902-1987
* Manuel Alvarado 8-1004-1411
* Daniel Perdomo 20-70-3962
* Jackeline Saldaña 8-1000-629*/
import Paquete.*;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) throws Exception {
        Conexion obj = new Conexion("Biblioteca(Proyecto_Final_ds3).accdb");
        try {
            Connection cnn = obj.establecer_conexion();
        } catch (Exception e) {
            System.out.println(e);
        }
        String idUsuario, nombre, apellido, direccion, telefono, correoElectronico, idLibro,
                idPrestamos, titulo, autor, genero, ISBN, editorial, consulta;
        Date  fechaPublicacion;
        String  fechaPrestamo,fechaDevolucion;
        int menu=0, menuEstudiante=0, menuBibliotecario=0, menuInforme=0;
        boolean error;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<SetterYGetter> lista = new LinkedList<>();
        Registro obj1 = new Registro();
        SetterYGetter obj2 = new SetterYGetter();
        Informes obj3 = new Informes();
        Busqueda obj4 = new Busqueda();
        while (menu !=3) {
            do {
                error = false;
                try {
                    System.out.println("\n");
                    System.out.println("¡Bienvenido al sistema de la Biblioteca UTP\n");
                    System.out.println("Menú");
                    System.out.println("1.Usuario");
                    System.out.println("2.Bibliotecario");
                    System.out.println("3.Salir");
                    menu = Integer.parseInt(br.readLine());
                    if (!(menu >=1 && menu<=3)){
                        System.out.println("error en la seleccion de opciones slo valido opciones del 1 al 3");
                        error = true;
                    }
                }catch (Exception e){
                    System.out.println(e);
                    error=true;
                }
                switch (menu) {
                    case 1:
                        do {
                            error = false;
                            try {
                                System.out.println("Menu de acciones");
                                System.out.println("1.Registrase");
                                System.out.println("2.Ingresar");
                                menuEstudiante = Integer.parseInt(br.readLine());
                                if (!(menuEstudiante>=1 && menuEstudiante<=2)){
                                    System.out.println("error de seleccion solo son 2 opciones");
                                    error = true;
                                }
                            }catch (Exception e){
                                System.out.println(e);
                                error = true;
                            }
                            switch (menuEstudiante) {
                                case 1:
                                    try {
                                        System.out.println("Ingrese su cedula");
                                        idUsuario = br.readLine();
                                        obj2.setIdUsuario(idUsuario);
                                        if (!obj1.verificar(obj,idUsuario)){
                                            System.out.println("Ingrese su nombre");
                                            nombre = br.readLine();
                                            obj2.setNombre(nombre);
                                            System.out.println("Ingrese su apellido");
                                            apellido = br.readLine();
                                            System.out.println("Ingrese su direccion");
                                            direccion = br.readLine();
                                            System.out.println("Ingrese su telefono");
                                            telefono = br.readLine();
                                            System.out.println("ingrese su correo Electronico");
                                            correoElectronico = br.readLine();
                                            obj1.registroUsuario(obj, idUsuario, nombre, apellido, direccion, telefono, correoElectronico);
                                            if (!obj1.verificar(obj,idUsuario)){
                                                try{
                                                    System.out.println("Catalogo de Busqueda :");
                                                    System.out.println("Puede buscar por : Titulo del libro, Categoria, Autor");
                                                    System.out.println("Ingrese su busqueda");
                                                    consulta= br.readLine();
                                                    lista = obj4.consulta_libros(obj, consulta);
                                                    System.out.print ("\nIdLibro" + "\t\t" + "Titulo" + "\t\t\t\t\t\t" + "Autor" + "\t\t\t\t\t" + "Genero"+ "\t\t\t\t\t" + "Disponible");
                                                    for (int i=0; i<lista.size();i++)
                                                        System.out.print ("\n"+lista.get(i).getIdLibro()+"\t\t"+lista.get(i).getTitulo()+
                                                                "\t\t\t"+lista.get(i).getAutor()+"\t\t\t"+lista.get(i).getGenero()
                                                                +"\t\t"+lista.get(i).getDisponibilidad());
                                                }
                                                catch(Exception e){
                                                    System.out.print (e);  }
                                            }
                                        }else {
                                            System.out.println("Usuario ya registrado en la base de datos");

                                        }
                                    }catch (Exception e){
                                        System.out.println(e);
                                    }
                                    break;

                                case 2:
                                    try{
                                        System.out.println("ingrese su cedula para ingresar ");
                                        idUsuario = br.readLine();
                                        if (obj1.verificar(obj,idUsuario)){
                                                try{
                                                    System.out.println("Catalogo de Busqueda :");
                                                    System.out.println("Puede buscar por : Titulo del libro, Categoria, Autor");
                                                    System.out.println("Ingrese su busqueda");
                                                    consulta= br.readLine();
                                                    lista = obj4.consulta_libros(obj, consulta);
                                                    System.out.print ("\nIdLibro" + "\t\t" + "Titulo" + "\t\t\t\t\t\t" + "Autor" + "\t\t\t\t\t" + "Genero"+ "\t\t\t\t\t" + "Disponible");
                                                    for (int i=0; i<lista.size();i++)
                                                        System.out.print ("\n"+lista.get(i).getIdLibro()+"\t\t"+lista.get(i).getTitulo()+
                                                                "\t\t\t"+lista.get(i).getAutor()+"\t\t\t"+lista.get(i).getGenero()
                                                                +"\t\t"+lista.get(i).getDisponibilidad());
                                                }
                                                catch(Exception e){
                                                    System.out.print (e);  }

                                        }else {
                                            System.out.println("El usuario no se encuentra registrado");
                                        }
                                    }catch (Exception e){
                                        System.out.println(e);
                                    }
                                    break;

                            }
                        }while (error);
                        break;
                    case 2:
                        do{
                            error = false;
                            try {
                                System.out.println("Menu de Accion");
                                System.out.println("1.Registrar Prestamo");
                                System.out.println("2.Registrar devolucion");
                                System.out.println("3.Enviar Notificación");
                                System.out.println("4.Informes");
                                menuBibliotecario = Integer.parseInt(br.readLine());
                                if (!(menuBibliotecario>=1 && menuBibliotecario<=4)){
                                    System.out.println("error de seleccion solamante son 4 opciones");
                                }
                            }catch (Exception e){
                                System.out.println(e);
                            }
                            switch (menuBibliotecario){
                                case 1:
                                    try {
                                        System.out.println("ingrese codigo de prestamo");
                                        idPrestamos = br.readLine();
                                        obj2.setIdPrestamo(idPrestamos);
                                        if(!obj1.verificarIdPrestamo(obj,idPrestamos)) {
                                            System.out.println("ingrese el codigo del libro");
                                            idLibro = br.readLine();
                                            obj2.setIdLibro(idLibro);
                                            System.out.println("ingrese la cedula");
                                            idUsuario = br.readLine();
                                            obj2.setIdUsuario(idUsuario);
                                            if(obj1.verificar(obj,idUsuario)) {
                                                System.out.println("fecha de prestamo del libro");
                                                fechaPrestamo = br.readLine();

                                                obj2.setFechaPrestamo(fechaPrestamo);
                                                System.out.println("introduzca la fecha de devolucion ");
                                                fechaDevolucion= br.readLine();

                                                obj2.setFechaDevolucion(fechaDevolucion);
                                                obj1.registrarPrestamo(obj, idPrestamos, idLibro, idUsuario, fechaPrestamo, fechaDevolucion);
                                            }else {
                                                System.out.println("El usuario no existe en la base de datos");
                                            }
                                        }else {
                                            System.out.println("Id de prestamo ya existente use uno nuevo");
                                        }
                                    }catch (Exception e){
                                        System.out.println(e);
                                    }
                                    break;
                                case 2:
                                    System.out.println("Devoluciones");
                                    System.out.println("ingrese el id del libro");
                                    idLibro = br.readLine();
                                    obj2.setIdLibro(idLibro);
                                    obj1.registrarDevolucion(obj,idLibro);
                                    break;
                                case 3:try {
                                    System.out.println("Notificaciones");
                                    System.out.println("ingrese la cedula del usuario");
                                    idUsuario = br.readLine();
                                    System.out.println("ingrese el id del prestamo");
                                    idPrestamos = br.readLine();
                                    obj2.setIdUsuario(idUsuario);
                                    obj3.enviarNotificacion(obj, idUsuario, idPrestamos);
                                }catch (Exception e){
                                    System.out.println(e);
                                }
                                    break;

                                case 4:
                                    System.out.print("cantidad de prestamos realizados \t");
                                    System.out.println(obj3.generarInformePrestamos(obj)+"\n");
                                    System.out.println("\n");
                                    try {
                                        lista = obj3.Libro_Popular(obj);
                                        System.out.println("Libro mas popular");
                                        System.out.print("\nidLibro" + "\t\t" + "Titulo" + "\t\t\t\t\t" + "Autor" + "\t\t\t\t\t" + "Genero" + "\t\t\t\t" + "disponibilidad" + "\t\t" + "cantidad de veces prestados");
                                        for (int i = 0; i < lista.size(); i++) {
                                            System.out.println("\n" + lista.get(i).getIdLibro() + "\t\t" + lista.get(i).getTitulo() + "\t\t" + lista.get(i).getAutor() + "\t\t"
                                                    + lista.get(i).getGenero() + "\t\t" + lista.get(i).getDisponibilidad() + "\t\t" + lista.get(i).getVecesPedido());
                                        }
                                    }catch (Exception e){
                                        System.out.println(e);
                                    }
                                    lista= obj3.usuarioMasPrestamos(obj);
                                    System.out.println("\n");
                                    System.out.println("\nusuario con mas presatmos realizados\n");
                                    System.out.println("\nnombre"+"\t\t"+"apellido\t\t"+"prestamos realizados");
                                    for (int i = 0; i<lista.size();i++){
                                        System.out.println("\n"+lista.get(i).getNombre()+"\t\t"+lista.get(i).getApellido()+"\n\n\n"+lista.get(i).getPrestamosRealizados());
                                    }
                                    break;
                            }
                        }while (error);
                        break;
                    case 3:
                        System.out.println("¡vuelva pronto!");

                }


            } while (error);
        }
    }
}
