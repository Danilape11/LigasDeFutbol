/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package liga;

import ligas_de_futbol_entidades.EntLiga;//Entidad de Liga
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AM8-PC16
 */

/**
 Clase que maneja operaciones de base de datos sobre la entidad Liga
 */

public class MdLiga {

    //variable con el nombre del recurso DataSource configurado en el context.xml
    
    private String datasource = "";

    //Constructor que recibe el nombre del DataSource
    
    public MdLiga(String datasource) {

        this.datasource = datasource;

    }
    
    //Metodo que obtiene todas las ligas sin el filtro

    public ArrayList<EntLiga> getLiga() {

        return getLiga(null);

    }
    
    //Metodo que obtiene un array de ligas con la opcion de filtrarlo por nombre

    public ArrayList<EntLiga> getLiga(String patron) {

        ArrayList<EntLiga> salida = new ArrayList();// Array donde se guardan las ligas que recuperamos de la base de datos
        Connection con = null;

        try {

            Context ctx = new InitialContext();// Obtenemos el contexto inicial JNDI
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);//Buscamos el DataSource configurado en el servidor
            con = (Connection) ds.getConnection();//Obtenemos la conexion a la base de datos

            String consulta = "SELECT * FROM liga";//Creamos la consulta base

            if (patron != null) {//Si hay un patron de busqueda se le agrega WHERE a la consulta

                consulta = consulta + " WHERE nombre like '%" + patron + "%'";

            }

            //Se prepara y se ejecuta la consulta 
            
            PreparedStatement ps = con.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            //Aqui se procesan los datos recibidos
            
            while (rs.next()) {

                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String fundacion = rs.getString("fundacion");

                //Creamos el objeto EntLiga y lo agregamos al array de salida
                
                EntLiga liga = new EntLiga(codigo, nombre, descripcion, fundacion);

                salida.add(liga);

            }

        } catch (Exception ex) {

            Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }

    //Esta clase obtiene la liga con el codigo que se le pase por parametro
    
    public EntLiga getLiga(int codigoLiga) {

        EntLiga salida = null;
        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consultamos SQL para obtener una liga por su codigo
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM liga WHERE codigo=?"); //Creamos la consulta SQL que le pediremos a la base de datos
            ps.setInt(1, codigoLiga);
            ResultSet rs = ps.executeQuery();

            // Si encuentra una coincidencia se crea el objeto EntLiga
            
            while (rs.next()) {

                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String fundacion = rs.getString("fundacion");

                salida = new EntLiga(codigo, nombre, descripcion, fundacion); //Metemos lo devuelto de la consulta en la variable "salida" para despues devolverla

            }

        } catch (Exception ex) {

            Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }

    //Esta clase guarda una liga que ya esta creada por si la has modificado
    
    public boolean guardarLiga(int codigoLiga, String nombre, String descripcion, String fundacion) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consultar SQL para actualizar la liga
            
            PreparedStatement ps = con.prepareStatement("UPDATE liga SET nombre = ?, descripcion = ?, fundacion = ? WHERE codigo = ?");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, fundacion);
            ps.setInt(4, codigoLiga);
            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }

    //Esta clase crea una liga nueva 
    
    public boolean CrearLiga(int codigoLiga, String nombre, String descripcion, String fundacion) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consulta SQL para insertar una nueva liga en la base de datos el codigo no se usa por que lo pusimos autoincrement
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO liga (nombre, descripcion, fundacion) values (?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, fundacion);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }

    //Esta clase elimina una liga que ya habia sido creada anteriormente
    
    public boolean EliminarLiga(int codigoLiga) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consulta SQL para eliminar una liga por su codigo
            
            PreparedStatement ps = con.prepareStatement("DELETE FROM liga WHERE codigo=?");
            ps.setInt(1, codigoLiga);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdLiga.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }

}
