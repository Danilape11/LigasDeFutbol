/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package jugador;

import equipo.MdEquipo;//Modelo para acceder a datos de equipos
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import ligas_de_futbol_entidades.EntEquipo;//Entidad de equipo
import ligas_de_futbol_entidades.EntJugador;//Entidad de jugador

/**
 *
 * @author AM8-PC16
 */

// Servlet que maneja todas las acciones relacionadas con los jugadores como listar, consultar, guardar, eliminar, etc

public class SvJugador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        /**
         Obtenemos los parametros codigo y accion de la solicitud
         */
        
        String codigo = request.getParameter("codigo");
        String accion = request.getParameter("accion");
        
        /**
         Se instancian los modelos Jugador y Equipo de la base de datos
         */

        MdJugador modelo = new MdJugador("jdbc/ligafutbol");
        MdEquipo modeloE = new MdEquipo("jdbc/ligafutbol");

        /**
         Si no se especifica la accion o es "listar" se listan todos los jugadores
         */
        
        if (accion == null || accion.equals("listar")) {

            ArrayList<EntJugador> jugadores = modelo.getJugador();//Obtiene todas los jugadores

            request.setAttribute("jugadorActual", jugadores);//Esto lo envia a la vista
            request.getRequestDispatcher("/vista/jugador/jugadores.jsp").forward(request, response);

            //Si la accion es "consultar", se busca un jugador por su codigo
            
        } else if (accion.equals("consultar")) {

            int iCodigo = Integer.parseInt(codigo);//Convertimos el codigo a entero

            EntJugador jugador = modelo.getJugador(iCodigo);//Buscamos el jugador por codigo
            ArrayList<EntEquipo> equipo = modeloE.getEquipo();//Obtenemos todos los equipos

            request.setAttribute("equipoActual", equipo);//Y los enviamos a la vista 
            request.setAttribute("jugadorActual", jugador);
            request.getRequestDispatcher("/vista/jugador/jugador.jsp").forward(request, response);

            //Si la accion es "guardar" se guarda o se actualiza el jugador 
            
        } else if (accion.equals("guardar")) {

            //Se obtienen los datos del formulario
            
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String posicion = request.getParameter("posicion");
            String piernaHabil = request.getParameter("piernaHabil");
            String idEquipo = request.getParameter("idEquipo");
            String codigoParam = request.getParameter("codigo");

            if (nombre != null && apellidos != null && posicion != null && piernaHabil != null) {

                int iCodigo = 0;
                int eCodigo = Integer.parseInt(idEquipo);

                if (codigoParam != null && !codigoParam.isEmpty()) {

                    iCodigo = Integer.parseInt(codigoParam);//Convertimos el codigo a entero si no esta vacio

                }

                boolean exito;
                
                //Si el codigo es mayor a 0 se actualiza el jugador

                if (iCodigo > 0) {

                    exito = modelo.guardarJugador(iCodigo, nombre, apellidos, eCodigo, posicion, piernaHabil);

                    //Si no se crea un jugador nuevo desde cero
                    
                } else {

                    exito = modelo.CrearJugador(iCodigo, nombre, apellidos, eCodigo, posicion, piernaHabil);

                }
                
                //Si exito es true se vuelven a listar los jugadores

                if (exito) {

                    ArrayList<EntJugador> jugadores = modelo.getJugador();

                    request.setAttribute("jugadorActual", jugadores);
                    request.getRequestDispatcher("/vista/jugador/jugadores.jsp").forward(request, response);

                    //Si es false se vuelve a jugador.jsp con los datos  que has introducido
                    
                } else {

                    ArrayList<EntEquipo> equipo = modeloE.getEquipo();

                    EntJugador jugador = new EntJugador(iCodigo, nombre, apellidos, eCodigo, posicion, piernaHabil);

                    request.setAttribute("equipoActual", equipo);
                    request.setAttribute("jugadorActual", jugador);
                    request.getRequestDispatcher("/vista/jugador/jugador.jsp").forward(request, response);

                }

            }
            
            // Si la accion es eliminar se elimina el jugador por codigo

        } else if (accion.equals("eliminar")) {

            int iCodigo = Integer.parseInt(codigo);
            
            //Si la eliminacion de el jugador se hace se listan los jugadores nuevamente

            if (iCodigo > 0 && modelo.EliminarJugador(iCodigo)) {

                ArrayList<EntJugador> jugador = modelo.getJugador();

                request.setAttribute("jugadorActual", jugador);
                RequestDispatcher dispacher = request.getRequestDispatcher("/vista/jugador/jugadores.jsp");
                dispacher.forward(request, response);

            }
            
            //Si la accion es nueva se dirige a jugador.jsp para crear uno nuevo

        } else if (accion.equals("nueva")) {

            EntJugador jugador = new EntJugador(0, "", "", 0, "", "");//Jugador vacio

            ArrayList<EntEquipo> equipo = modeloE.getEquipo();

            request.setAttribute("equipoActual", equipo);
            request.setAttribute("jugadorActual", jugador);
            request.getRequestDispatcher("/vista/jugador/jugador.jsp").forward(request, response);

            //Si la accion es buscar se realiza una busqueda de jugadores por patron
            
        } else if (accion.equals("buscar")) {

            String patron = request.getParameter("patron");//Este es el patron de busqueda
            
            ArrayList<EntJugador> arJugador = modelo.getJugador(patron);//Buscamos jugadores que coincidan

            request.setAttribute("jugadorActual", arJugador);
            request.getRequestDispatcher("/vista/jugador/jugadores.jsp").forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
