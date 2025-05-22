<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 
    Document   : jugadores
    Created on : 21 abr 2025, 15:40:03
    Author     : AM8-PC16
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;"> <!-- Aqui metemos una imagen de fondo con su respectivo estilo -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Aqui le damos estilo a las clases que he creado mas abajo -->

        <style>

            body {
                margin: 0;
                font-family: "Comic Sans MS", cursive;
                background: transparent;
            }

            .header {
                background-color: rgba(76, 175, 80, 0.95);
                color: white;
                text-align: center;
                padding: 20px;
                font-size: 30px;
                position: sticky;
                top: 0;
                z-index: 1000;
            }

            .formulario {
                font-size: 18px;
                color: #ffffff;
                line-height: 1.8;
                padding: 10px;
            }

            .formulario input[type="text"] {
                padding: 8px;
                border-radius: 6px;
                border: none;
                width: 280px;
                margin-bottom: 10px;
            }

            .formulario button {
                background-color: #60de5b;
                border: 2px solid white;
                border-radius: 8px;
                color: #ffffff;
                padding: 8px 14px;
                cursor: pointer;
                font-size: 16px;
                transition: 0.3s;
            }

            .formulario button:hover {
                background-color: #4CAF50;
            }

            a {
                color: #ffffff;
                text-decoration: none;
                transition: 0.3s;
            }

            a:hover {
                color: #FFD700;
                text-decoration: underline;
            }

            .empty {
                font-family: "Comic Sans MS", cursive;
                color: #ffffff;
            }

            table {
                margin-top: 20px;
                border-collapse: collapse;
            }

            td {
                padding: 8px 12px;
            }

            tr {
                color: #ffffff;
                font-family: Comic Sans MS, cursive;
                transition: 0.3s;
                cursor: pointer;
            }

            tr:hover {
                color: #FFD700;
            }

            img {
                transition: 0.3s;
            }

            img:hover {
                transform: scale(1.03);
            }

            .contenedor {
                display: flex;
                justify-content: flex-start;
                align-items: flex-start;
                margin: 40px;
                gap: 30px;
            }

            .piepagina {
                background-color: rgba(76, 175, 80, 0.95);
                color: white;
                text-align: center;
                padding: 12px;
                margin-top: 50px;
                font-size: 14px;
            }

            hr {
                border: 0;
                height: 2px;
                background: #ffffff;
                margin: 20px auto;
                width: 80%;
            }

        </style>

        <title>JUGADORES INSCRITOS</title>

    </head>

    <body>

        <h1 class="header">⚽ JUGADORES INSCRITOS ⚽</h1>

        <hr>

        <!-- Creamos una funcion con JavaScript para poder eliminar el jugador que el usuario quiera y la misma funcion pregunta antes si esta seguro de ello -->

        <script language="">

            function borrarJugador(codigo) {

                if (confirm("¿Estas seguro/a de eliminar este jugador?")) {

                    window.location.href = "${pageContext.request.contextPath}/SvJugador?accion=eliminar&codigo=" + codigo;

                }

            }

        </script>

        <div class="contenedor">

            <div style="width: 60%">

                <!-- Creamos un formulario para poder filtrar los jugadores por su nombre -->

                <form class="formulario" action="/LigaDeFutbol/SvJugador" method="post">

                    <input type="hidden" id="accion" name="accion" value="buscar"/>

                    Nombre: <input type="text" id="patron" name="patron"/>

                    <button style="background-color: #60de5b; border: 2px solid white; border-radius: 8px; color: #ffffff" type="submit">Buscar</button>

                </form>

                <!-- Aqui creamos una tabla con los equipos que nos devuelve la base de datos que a su vez son enlaces para poder acceder a jugador.jsp y poder modificarlos -->

                <table>

                    <c:forEach var="iteracion" items="${jugadorActual}">

                        <tr onclick="window.location.href = '${pageContext.request.contextPath}/SvJugador?accion=consultar&codigo=${iteracion.codigo}'">

                            <td>${iteracion.codigo}</td>

                            <td>${iteracion.nombre}</td>

                            <td>${iteracion.apellidos}</td>

                            <td>${iteracion.posicion}</td>

                            <td>${iteracion.piernaHabil}</td>

                            <td>${iteracion.idEquipo}</td>

                            <td onclick="event.stopPropagation(); borrarJugador(${iteracion.codigo})" style="cursor: pointer;">

                                <img src="/LigasDeFutbol/imagenes/eliminar.png" style="width: 25px; background-color: #4CAF50"/></td>

                            <!-- Aqui hemos puesto una imagen de una papelera que llama a la funcion de borrar para poder eliminar el jugador que el usuario quiera -->

                        </tr>

                    </c:forEach>

                    <!-- En este if si la base de datos no devuelve nada mostrara el mensaje o si filtras por nombre y no hay ninguno con ese nombre lo mostrara tambien -->

                    <c:if test="${empty jugadorActual}">

                        <a class="empty"> ¡No hay jugadores con ese nombre!</a>

                    </c:if>

                </table>

                <br>

                <!-- Aqui he puesto dos enlaces uno para crear nuevos jugadores desde cero que te lleva a jugador.jsp y otro para volver a la pagina principal index.html -->

                <a class="empty" href="/LigasDeFutbol/SvJugador?accion=nueva">Crear Jugador</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <a class="empty" href="/LigasDeFutbol/index.html">Volver</a>

            </div>

            <img src="/LigasDeFutbol/imagenes/jugadores.png" alt="Imagen jugadores" style="width: 575px; border-radius: 8px">

        </div>

        <div class="piepagina">

            2025 Ligas de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>
