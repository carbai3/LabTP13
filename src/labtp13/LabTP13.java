/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labtp13;

import java.sql.Connection;
import java.sql.Date;
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

//            String sql="SELECT * FROM alumno WHERE estado = ?";
//            
//            PreparedStatement ps=con.prepareStatement(sql);
//            ps.setBoolean(1, estadoConsulta);
//            ResultSet datos=ps.executeQuery();
//            while(datos.next()){
//            
//                int idAlumno=datos.getInt("idAlumno");
//                int dni=datos.getInt("dni");
//                String apellido=datos.getString("apellido");
//                String nombre=datos.getString("nombre");
//                Date fechaN=datos.getDate("fechaNacimiento");
//                boolean estado=datos.getBoolean("estado");
//                
//                System.out.println("idAlumno "+idAlumno);
//                System.out.println("Apellido "+apellido);
//                System.out.println("Fecha de nacimiento "+fechaN);
//            }
//            
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
