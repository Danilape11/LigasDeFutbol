/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package equipo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import liga.MdLiga;
import ligas_de_futbol_entidades.EntEquipo;//Entidad Equipo
import ligas_de_futbol_entidades.EntLiga;//Entidad Liga

/**
 *
 * @author AM8-PC16
 */

/**
 Este es el servlet de Equipo que gestiona todo lo relacionada con equipo como listar, consultar, guardar, eliminar, etc
 */

public class SvEquipo extends HttpServlet {

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
         Se instancia el modelo Equipo y el modelo Liga de la base de datos
         */

        MdEquipo modelo = new MdEquipo("jdbc/ligafutbol");
        MdLiga modeloL = new MdLiga("jdbc/ligafutbol");
        
        /**
         Si no se especifica la accion o es "listar" se listan todos los equipos
         */

        if (accion == null || accion.equals("listar")) {

            ArrayList<EntEquipo> equipos = modelo.getEquipo();//Obtiene todos los equipos

            request.setAttribute("equipoActual", equipos);//Esto lo envia a la vista
            request.getRequestDispatcher("/vista/equipo/equipos.jsp").forward(request, response);

            //Si la accion es "consultar" se busca un equipo por su codigo
            
        } else if (accion.equals("consultar")) {

            int iCodigo = Integer.parseInt(codigo);//Convertimos el codigo a entero

            ArrayList<EntLiga> liga = modeloL.getLiga();//Obtenemos todas las ligas

            request.setAttribute("ligaActual", liga);

            EntEquipo equipo = modelo.getEquipo(iCodigo);//Buscamos el equipo por codigo
            request.setAttribute("equipoActual", equipo);//Y lo enviamos a la vista
            request.getRequestDispatcher("/vista/equipo/equipo.jsp").forward(request, response);

            //Si la accion es "guardar" se guarda o se actualiza el Equipo
            
        } else if (accion.equals("guardar")) {
            
            //Se obtienen los datos del formulario

            String nombre = request.getParameter("nombre");
            String yearFundacion = request.getParameter("yearFundacion");
            String presidente = request.getParameter("presidente");
            String entrenador = request.getParameter("entrenador");
            String idLiga = request.getParameter("idLiga");
            String codigoParam = request.getParameter("codigo");

            if (nombre != null && yearFundacion != null && presidente != null && entrenador != null && idLiga != null) {

                int iCodigo = 0;
                int fundacion = Integer.parseInt(yearFundacion);
                int lCodigo = Integer.parseInt(idLiga);

                if (codigoParam != null && !codigoParam.isEmpty()) {

                    iCodigo = Integer.parseInt(codigoParam);//Convertimos el codigo a entero si no esta vacio

                }

                boolean exito;

                //Si el codigo es mayor a 0 se actualiza el equipo
                
                if (iCodigo > 0) {

                    exito = modelo.guardarEquipo(iCodigo, nombre, lCodigo, presidente, entrenador, fundacion);

                    //Si no se crea un equipo nuevo desde cero
                    
                } else {

                    exito = modelo.CrearEquipo(iCodigo, nombre, lCodigo, presidente, entrenador, fundacion);

                }
                
                //Si exito es true se vuelven a listar los equipos

                if (exito) {

                    ArrayList<EntEquipo> equipos = modelo.getEquipo();

                    request.setAttribute("equipoActual", equipos);
                    request.getRequestDispatcher("/vista/equipo/equipos.jsp").forward(request, response);

                    //Si es false se vuelve a equipo.jsp con los datos que has introducido
                    
                } else {

                    ArrayList<EntLiga> liga = modeloL.getLiga();

                    request.setAttribute("ligaActual", liga);
                    EntEquipo equipo = new EntEquipo(iCodigo, nombre, lCodigo, presidente, entrenador, fundacion);
                    request.setAttribute("equipoActual", equipo);
                    request.getRequestDispatcher("/vista/equipo/equipo.jsp").forward(request, response);

                }

            }
            
            //Si la accion es "eliminar" se elimina el equipo por codigo

        } else if (accion.equals("eliminar")) {

            int iCodigo = Integer.parseInt(codigo);
            
            //Si la eliminacion del equipo se hace se listan los equipos nuevamente

            if (iCodigo > 0 && modelo.EliminarEquipo(iCodigo)) {

                ArrayList<EntEquipo> equipo = modelo.getEquipo();

                request.setAttribute("equipoActual", equipo);
                RequestDispatcher dispacher = request.getRequestDispatcher("/vista/equipo/equipos.jsp");
                dispacher.forward(request, response);

            }
            
            //Si la accio es "nueva" se dirige a equipo.jsp para crear uno nuevo

        } else if (accion.equals("nueva")) {

            EntEquipo equipo = new EntEquipo(0, "", 0, "", "", 0);//Equipo vacio

            ArrayList<EntLiga> liga = modeloL.getLiga();

            request.setAttribute("ligaActual", liga);
            request.setAttribute("equipoActual", equipo);
            request.getRequestDispatcher("/vista/equipo/equipo.jsp").forward(request, response);

            //Si la accion es "buscar" se realiza una busqueda de equipos por patron
            
        } else if (accion.equals("buscar")) {

            String patron = request.getParameter("patron");//Este es el patron de busqueda
            
            ArrayList<EntEquipo> arEquipo = modelo.getEquipo(patron);//Buscamos equipos que coincidan

            request.setAttribute("equipoActual", arEquipo);
            request.getRequestDispatcher("/vista/equipo/equipos.jsp").forward(request, response);

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
