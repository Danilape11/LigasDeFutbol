/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package liga;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import java.util.HashSet;
import liga.MdLiga;
import ligas_de_futbol_entidades.EntLiga;//Entidad de liga

/**
 *
 * @author AM8-PC16
 */

/**
 Este es el servlet de Liga que gestiona todo lo relacionada con liga como listar, consultar, guardar, eliminar, etc
 */

public class SvLiga extends HttpServlet {

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
         Se instancia el modelo Liga de la base de datos
         */
        
        MdLiga modelo = new MdLiga("jdbc/ligafutbol");

         /**
         Si no se especifica la accion o es "listar" se listan todas las ligas
         */
        
        if (accion == null || accion.equals("listar")) {

            ArrayList<EntLiga> ligas = modelo.getLiga();//Obtiene todas las ligas
            
            request.setAttribute("ligaActual", ligas);//Esto lo envia a la vista
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vista/liga/ligas.jsp");
            dispatcher.forward(request, response);

        //Si la accion es "consultar", se busca una liga por su codigo    
            
        } else if (accion.equals("consultar")) {

            int iCodigo = Integer.parseInt(codigo); //Convertimos el codigo a entero

            EntLiga liga = modelo.getLiga(iCodigo); //Buscamos la liga por codigo
            request.setAttribute("ligaActual", liga); // Y lo enviamos a la vista
            request.getRequestDispatcher("/vista/liga/liga.jsp").forward(request, response);

            //Si la accion es "guardar" se guarda o se actualiza la liga
            
        } else if (accion.equals("guardar")) {

            //Se obtienen los datos del formulario
            
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String fundacion = request.getParameter("fundacion");
            String codigoParam = request.getParameter("codigo");

            if (nombre != null && descripcion != null && fundacion != null) {

                int iCodigo = 0;

                if (codigoParam != null && !codigoParam.isEmpty()) {

                    iCodigo = Integer.parseInt(codigoParam); //Convertimos el codigo a entero si no esta vacio

                }

                boolean exito;

                //Si el codigo es mayor a 0 se actualiza la liga 
                
                if (iCodigo > 0) {

                    exito = modelo.guardarLiga(iCodigo, nombre, descripcion, fundacion);

                    //Si no se crea una liga nueva desde cero
                    
                } else {

                    exito = modelo.CrearLiga(iCodigo, nombre, descripcion, fundacion);

                }

                //Si exito es true se vuelven a listar las ligas
                
                if (exito) {

                    ArrayList<EntLiga> ligas = modelo.getLiga();
                    request.setAttribute("ligaActual", ligas);
                    request.getRequestDispatcher("/vista/liga/ligas.jsp").forward(request, response);

                    // Si es false se vuelve a liga.jsp con los datos que has introducido
                    
                } else {

                    EntLiga liga = new EntLiga(iCodigo, nombre, descripcion, fundacion);
                    request.setAttribute("ligaActual", liga);
                    request.getRequestDispatcher("/vista/liga/liga.jsp").forward(request, response);

                }

            }

            // Si la accion es eliminar se elimina la liga por codigo
            
        } else if (accion.equals("eliminar")) {

            int iCodigo = Integer.parseInt(codigo);

            // Si la eliminacion de la liga se hace se listan las ligas nuevamente 
            
            if (iCodigo > 0 && modelo.EliminarLiga(iCodigo)) {

                ArrayList<EntLiga> liga = modelo.getLiga();

                request.setAttribute("ligaActual", liga);
                RequestDispatcher dispacher = request.getRequestDispatcher("/vista/liga/ligas.jsp");
                dispacher.forward(request, response);

            }

            //Si la accion es nueva se dirige a liga.jsp para crear una nueva 
            
        } else if (accion.equals("nueva")) {

            EntLiga liga = new EntLiga(0, "", "", ""); //Liga vacia
            request.setAttribute("ligaActual", liga);
            request.getRequestDispatcher("/vista/liga/liga.jsp").forward(request, response);

            //Si la accion es buscar se realiza una busqueda de ligas por patron
            
        } else if (codigo != null || (accion.equals("buscar"))) {

            String patron = request.getParameter("patron"); // Este es el patron de busqueda

            ArrayList<EntLiga> arLiga = modelo.getLiga(patron); // Buscamos ligas que coincidan

            request.setAttribute("ligaActual", arLiga);
            request.getRequestDispatcher("/vista/liga/ligas.jsp").forward(request, response);

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
