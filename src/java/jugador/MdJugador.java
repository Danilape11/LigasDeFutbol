/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package jugador;

import ligas_de_futbol_entidades.EntJugador;//Entidad de Jugador
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
 Clase que maneja operaciones de base de datos sobre la entidad Jugador
 */

public class MdJugador {

        //variable con el nombre del recurso DataSource configurado en el context.xml
    
    private String datasource = "";

    public MdJugador(String datasource) {
        
        //Constructor que recibe el nombre del DataSource

        this.datasource = datasource;

    }
    
    //Metodo que obtiene todos los jugadores sin el filtro

    public ArrayList<EntJugador> getJugador() {

        return getJugador(null);

    }
    
    //Metodo que obtiene un array de jugadores con la opcion de filtrarlo por nombre

    public ArrayList<EntJugador> getJugador(String patron) {

        ArrayList<EntJugador> salida = new ArrayList();// Array donde se guardan los jugadores que recuperamos de la base de datos
        Connection con = null;

        try {

            Context ctx = new InitialContext();//Obtenemos el contexto inicial JNDI
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);//Buscamos el DataSource configurado en el servidor
            con = (Connection) ds.getConnection();//Obtenemos la conexion a la base de datos

            String consulta = "SELECT * FROM jugador";//Creamos la consulta base

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
                String apellidos = rs.getString("apellidos");
                int idEquipo = rs.getInt("idEquipo");
                String posicion = rs.getString("posicion");
                String piernaHabil = rs.getString("piernaHabil");

                //Creamos el objeto EntJugador y lo agregamos al array de salida
                
                EntJugador jugador = new EntJugador(codigo, nombre, apellidos, idEquipo, posicion, piernaHabil);
                salida.add(jugador);

            }

        } catch (Exception ex) {

            Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }
    
    //Esta clase obtiene el jugador con el codigo que se le pase por parametro

    public EntJugador getJugador(int codigoJugador) {

        EntJugador salida = null;
        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();

            //Consultamos SQL para obtener un jugador por su codigo
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM jugador WHERE codigo=?");
            ps.setInt(1, codigoJugador);
            ResultSet rs = ps.executeQuery();
            
            //Si encuentra una coincidencia se crea el objeto EntJugador

            while (rs.next()) {

                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int idEquipo = rs.getInt("idEquipo");
                String posicion = rs.getString("posicion");
                String piernaHabil = rs.getString("piernaHabil");

                salida = new EntJugador(codigo, nombre, apellidos, idEquipo, posicion, piernaHabil);//Metemos lo devuelto de la consulta en la variable "salida" para despues devolverla 

            }

        } catch (Exception ex) {

            Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return salida;

    }
    
    //Esta clase guarda un jugador que ya esta creado por si lo has modificado

    public boolean guardarJugador(int codigoJugador, String nombre, String apellidos, int idEquipo, String posicion, String piernaHabil) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();
            
            //Consulta SQL para actualizar el jugador

            PreparedStatement ps = con.prepareStatement("UPDATE jugador SET nombre = ?, apellidos = ?, idEquipo = ?, posicion = ?, piernaHabil = ? WHERE codigo = ?");
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setInt(4, idEquipo);
            ps.setString(3, posicion);
            ps.setString(3, piernaHabil);
            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }
    
    //Esta clase crea un jugador nuevo

    public boolean CrearJugador(int codigoJugador, String nombre, String apellidos, int idEquipo, String posicion, String piernaHabil) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();
            
            //Consulta SQL para insertar un nuevo jugador en la base de datos el codigo no se usa por que pusimos autoincrement

            PreparedStatement ps = con.prepareStatement("INSERT INTO jugador (nombre, apellidos, idEquipo, posicion, piernaHabil) values (?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setInt(3, idEquipo);
            ps.setString(4, posicion);
            ps.setString(5, piernaHabil);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }
    
    //Esta clase elimina un jugador que ya habia sido creado anteriormente

    public boolean EliminarJugador(int codigoJugador) {

        Connection con = null;

        try {

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + this.datasource);
            con = (Connection) ds.getConnection();
            
            //Consulta SQL para eliminar un jugador por su codigo

            PreparedStatement ps = con.prepareStatement("DELETE FROM jugador WHERE codigo=?");
            ps.setInt(1, codigoJugador);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {

            Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ex) {

                    Logger.getLogger(MdJugador.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        }

        return false;

    }

}
