/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package equipo;

import ligas_de_futbol_entidades.EntEquipo;//Entidad de Equipo
import java.sql.Connection;
import javax.naming.Context;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;

/**
 *
 * @author AM8-PC16
 */

/**
 Clase que maneja operaciones de base de datos sobre la entidad Equipo
 */

public class MdEquipo {
    
        //variable con el nombre del recurso DataSource configurado en el context.xml

    private String datasource = "";

    //Constructor que recibe el nombre del DataSource
    
    public MdEquipo(String datasource) {

        this.datasource = datasource;

    }
    
    //Metodo que obtiene todos los equipos sin el filtro

    public ArrayList<EntEquipo> getEquipo() {

        return getEquipo(null);

    }
    
    //Metodo que obtiene un array de equipos con la opcion de filtrarlo por nombre

    public ArrayList<EntEquipo> getEquipo(String patron) {

        ArrayList<EntEquipo> salida = new ArrayList();//Array donde se guardan los equipos que recuperamos de la base de datos
        Connection con = null;

        try {

            Context ctx = new InitialContext();//Obtenemos el contexto inicial JNDI
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);//Buscamos el DataSource configurado en el servidor 
            con = (Connection) ds.getConnection();//Obtenemos la conexion a la base de datos

            String consulta = "SELECT * FROM equipo";//Creamos la consulta base

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
                int idLiga = rs.getInt("idLiga");
                String presidente = rs.getString("presidente");
                String entrenador = rs.getString("entrenador");
                int yearFundacion = rs.getInt("yearFundacion");

                //Creamos el objeto EntJugador y lo agregamos al array de salida
                
                EntEquipo equipo = new EntEquipo(codigo, nombre, idLiga, presidente, entrenador, yearFundacion);

                salida.add(equipo);
            }

        } catch (Exception ex) {

            Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }
    
    //Esta clase obtiene el equipo con el codigo que se le pasa por parametro

    public EntEquipo getEquipo(int codigoEquipo) {

        EntEquipo salida = null;
        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consultamos SQL para obtener un jugador por su codigo
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM equipo WHERE codigo=?");//Creamos la consulta SQL que le pediremos a la base de datos
            ps.setInt(1, codigoEquipo);
            ResultSet rs = ps.executeQuery();
            
            //Si encuentra una coincidencia se crea el objeto EntJugador

            while (rs.next()) {

                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                int idLiga = rs.getInt("idLiga");
                String presidente = rs.getString("presidente");
                String entrenador = rs.getString("entrenador");
                int yearFundacion = rs.getInt("yearFundacion");

                salida = new EntEquipo(codigo, nombre, idLiga, presidente, entrenador, yearFundacion);//Metemos lo devuelto de la consulta en la variable "salida" para despues devolverla

            }

        } catch (Exception ex) {

            Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }
    
    //Esta clase guarda un jugador que ya esta creado por si la has modificado

    public boolean guardarEquipo(int codigoEquipo, String nombre, int idLiga, String presidente, String entrenador, int yearFundacion) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consulta SQL para actualizar el jugador
            
            PreparedStatement ps = con.prepareStatement("UPDATE equipo SET nombre = ?, idLiga = ?, presidente = ?, entrenador = ?, yearFundacion = ? WHERE codigo = ? ");
            ps.setString(1, nombre);
            ps.setInt(2, idLiga);
            ps.setString(3, presidente);
            ps.setString(4, entrenador);
            ps.setInt(5, yearFundacion);
            ps.setInt(6, codigoEquipo);
            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }
    
    //Esta clase crea un jugador nuevo

    public boolean CrearEquipo(int codigoEquipo, String nombre, int idLiga, String presidente, String entrenador, int yearFundacion) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();
            
            //Consulta SQL para insertar un nuevo jugador en la base de datos el codigo no se usa por que lo pusimos autoincrement

            PreparedStatement ps = con.prepareStatement("INSERT INTO equipo (nombre, idLiga, presidente, entrenador, yearFundacion) values (?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setInt(2, idLiga);
            ps.setString(3, presidente);
            ps.setString(4, entrenador);
            ps.setInt(5, yearFundacion);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }
    
    //Esta clase elimina un jugador que ya habia sido creado anteriormente

    public boolean EliminarEquipo(int codigoEquipo) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();
            
            //Consulta SQL para eliminar un jugador por su codigo

            PreparedStatement ps = con.prepareStatement("DELETE FROM equipo WHERE codigo=?");
            ps.setInt(1, codigoEquipo);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdEquipo.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }

}
