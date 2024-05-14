/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labtp13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author carba
 */
public class LabTP13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Class.forName("org.mariadb.jdbc.Driver");
            String bd = "jdbc:mysql://localhost:3306/universidad";
            String usuario = "root";
            String password = "";
            Connection con = DriverManager.getConnection(bd, usuario, password);

            boolean estadoConsulta = false;
            
            // Insertar 3 alumnos
            String sql = "INSERT INTO "
                    + "alumno(dni, apellido, nombre, fechaNacimiento, estado) "
                    + "VALUES (9998885,'Martinez','Maria','2000-04-25',1),"
                    + "(34455,'luna','carmen','1998-03-30',1),"
                    + "(12345678,'Quito','Esteban','1999-01-25',1)";

            PreparedStatement ps = con.prepareStatement(sql);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Alumno Agregado");
            }
            // Insertar 4 materias
            String sqlInsertMaterias = "INSERT INTO materia(nombre) VALUES "
                    + "('Matemáticas'),"
                    + "('Física'),"
                    + "('Química'),"
                    + "('Historia')";
            ps = con.prepareStatement(sqlInsertMaterias);
            int filasMaterias = ps.executeUpdate();
            if (filasMaterias > 0) {
                JOptionPane.showMessageDialog(null, "Materias Agregadas");
            }

            //  Inscribir a los 3 alumnos en 2 materias cada uno
            String sqlInscribirAlumnos = "INSERT INTO inscripcion(idAlumno, idMateria) VALUES "
                    + "(1, 1),"
                    + 
                    "(1, 2),"
                    + 
                    "(2, 1),"
                    + 
                    "(2, 3),"
                    + 
                    "(3, 2),"
                    + 
                    "(3, 4)";
            ps = con.prepareStatement(sqlInscribirAlumnos);
            int filasInscripciones = ps.executeUpdate();
            if (filasInscripciones > 0) {
                JOptionPane.showMessageDialog(null, "Alumnos Inscritos en Materias");
            }

            //Listar los datos de los alumnos con calificaciones superiores a 8
            String sqlAlumnosCalificaciones = "SELECT a.idAlumno, a.dni, a.apellido, a.nombre, i.calificacion "
                    + "FROM alumno a "
                    + "JOIN inscripcion i ON a.idAlumno = i.idAlumno "
                    + "WHERE i.calificacion > 8";
            ps = con.prepareStatement(sqlAlumnosCalificaciones);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAlumno = rs.getInt("idAlumno");
                int dni = rs.getInt("dni");
                String apellido = rs.getString("apellido");
                String nombre = rs.getString("nombre");
                double calificacion = rs.getDouble("calificacion");

                System.out.println("ID Alumno: " + idAlumno);
                System.out.println("DNI: " + dni);
                System.out.println("Apellido: " + apellido);
                System.out.println("Nombre: " + nombre);
                System.out.println("Calificación: " + calificacion);
                System.out.println("---------------------------");
            }

            // Desinscribir un alumno de una de las materias
            String sqlDesinscribirAlumno = "DELETE FROM inscripcion WHERE idAlumno = 1 AND idMateria = 2";
            ps = con.prepareStatement(sqlDesinscribirAlumno);
            int filasDesinscripcion = ps.executeUpdate();
            if (filasDesinscripcion > 0) {
                JOptionPane.showMessageDialog(null, "Alumno Desinscrito de la Materia");
            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargard driver");
        } catch (SQLException ex) {
            int codigoE = ex.getErrorCode();
            if (codigoE == 1062) {
                JOptionPane.showMessageDialog(null, "Entrada Duplicada");
            } else if (codigoE == 1049) {
                JOptionPane.showMessageDialog(null, "BD Desconocida");
            } else {
                JOptionPane.showMessageDialog(null, "Error ");
            }

            ex.printStackTrace();
            System.out.println("codigo de error " + ex.getErrorCode());
        }

    }

}
