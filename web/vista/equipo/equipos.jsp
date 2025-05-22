<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- 
    Document   : equipos
    Created on : 21 abr 2025, 15:39:44
    Author     : AM8-PC16
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;"> <!-- Aqui le ponemos una imagen de fondo y le ponemos su estilo -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Aqui damos estilo a las clases creadas mas abajo del codigo -->

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

            .infoDerecha {
                padding: 20px;
                border-radius: 10px;
                width: 320px;
                background-color: rgba(223, 240, 216, 0.9);
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

        <title>EQUIPOS INSCRITOS</title>

    </head>

    <body>

        <h1 class="header">⚽ EQUIPOS INSCRITOS ⚽</h1>

        <hr>

        <!-- Aqui hemos creado una funcion con javascript para poder eliminar los equipos que consideremos preguntando si estamos seguro de ello -->

        <script language="">

            function borrarEquipo(codigo) {

                if (confirm("¿Estas seguro/a de eliminar este equipo?")) {

                    window.location.href = "${pageContext.request.contextPath}/SvEquipo?accion=eliminar&codigo=" + codigo;

                }

            }

        </script>

        <div class="contenedor">

            <div style="width: 60%">

                <!-- Aqui creamos un formulario para poder filtrar los equipos por nombre -->

                <form class="formulario" action="/LigasDeFutbol/SvEquipo" method="post">

                    <input type="hidden" id="accion" name="accion" value="buscar"/>

                    Nombre: <input type="text" id="patron" name="patron"/>

                    <button style="background-color: #60de5b; border: 2px solid white; border-radius: 8px; color: #ffffff" type="submit">Buscar</button>

                </form>

                <!-- Aqui creamos una tabla con todos los equipos que hay en la base de datos o con el filtro si lo hemos utilizado con sus respectivos atributos a su vez la linea es un enlace que te lleva a modificar los datos del equipo al que le hagas clic -->

                <table>

                    <c:forEach var="iteracion" items="${equipoActual}">

                        <tr onclick="window.location.href = '${pageContext.request.contextPath}/SvEquipo?accion=consultar&codigo=${iteracion.codigo}'">

                            <td>${iteracion.codigo}</td>

                            <td>${iteracion.nombre}</td>

                            <td>${iteracion.idLiga}</td>

                            <td>${iteracion.presidente}</td>

                            <td>${iteracion.entrenador}</td>

                            <td>${iteracion.yearFundacion}</td>

                            <td onclick="event.stopPropagation(); borrarEquipo(${iteracion.codigo})" style="background: url('/LigasDeFubtol/imagenes/eliminar.png') no-repeat;">

                                <img src="/LigasDeFutbol/imagenes/eliminar.png" style="width: 25px; background-color: #4CAF50"/></td> <!-- Aqui hemos puesto una imagen de una papelera que a su vez utiliza la funcion creada anteriormente que nos permite eliminar el equipo que queramos -->

                        </tr>

                    </c:forEach>

                    <!-- Aqui en este if si la base de datos no nos devuelve nada o si cuando filtramos un nombre de un equipo no obtiene resultados nos pondra este mensaje de que no hay equipos -->

                    <c:if test="${empty equipoActual}">

                        <a class="empty">¡No hay equipos con ese nombre!</a>

                    </c:if>

                </table>

                <br><br>

                <!-- Aqui hemos puesto dos enlaces uno para poder crear un equipo desde 0 y otro el cual nos devuelve a la pagina principal index.html -->

                <a class="empty" href="/LigasDeFutbol/SvEquipo?accion=nueva">Crear Equipo</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <a class="empty" href="/LigasDeFutbol/index.html">Volver</a>

            </div>

            <!-- Aqui hemos puesto informacion para el usuario junto con el segundo parrafo en azul que es un enlace por si no tienes jugadores para tu equipo poder crearlos antes de crear un equipo -->

            <div class="infoDerecha">

                <p style="color: black; font-family: Comic Sans MS, Comic Sans, cursive; font-size: 16px; margin-bottom: 15px; line-height: 1.6">

                    <img src="/LigasDeFutbol/imagenes/exclamacion.png" style="width: 25px"/>

                    <strong>¿Aún no tienes jugadores para tu equipo?</strong><br>

                    ¡No te preocupes! Puedes crear los tuyos aqui.

                </p>

                <p style="font-style: 15px; margin-top: 10px">

                    <img src="/LigasDeFutbol/imagenes/manoderecha.png" style="width: 40px"/>

                    <a href="/LigasDeFutbol/SvJugador?accion=nueva" style="color:blue; font-family: Comic Sans MS, Comic Sans, cursive; text-decoration: none; font-weight: bold">

                        Haz clic aqui para crear tus jugadores.

                    </a>

                </p>

            </div>

        </div>

        <img style="margin: 30px auto; display: block; border-radius: 8px; width: 50%" src="imagenes/equipos.png">

        <div class="piepagina">

            2025 Liga de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>
