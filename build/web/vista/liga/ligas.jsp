<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;"> <!-- Le ponemos una foto de fondo con su estilo -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>LIGAS INSCRITAS</title>

        <!-- Aqui le pondremos estilos a cada una de las clases que hemos creado dentro de este jsp para mejorar la vista de ligas.jsp -->

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

        <!-- Creamos una funcion que nos ayude para eliminar la liga que consideremos preguntandonos antes si estamos seguros -->

        <script>

            function borrarLiga(codigo) {

                if (confirm("¿Estas seguro/a de eliminar esta liga?")) {

                    window.location.href = "${pageContext.request.contextPath}/SvLiga?accion=eliminar&codigo=" + codigo;

                }

            }

        </script>

    </head>

    <body>

        <div class="header">⚽ LIGAS INSCRITAS ⚽</div><!-- Este es el encabezado con su respectiva clase a cual estara fijada arriba siempre aunque hagamos scroll hacia abajo -->

        <hr>

        <div class="contenedor">

            <div style="width: 60%;">

                <form class="formulario" action="/LigasDeFutbol/SvLiga" method="post"><!-- Creamos el formulario donde podremos filtrar y hacer una busqueda de una liga por nombre -->

                    <input type="hidden" id="accion" name="accion" value="buscar"/>

                    Nombre: <input type="text" id="patron" name="patron"/>

                    <button type="submit">Buscar</button>

                </form>

                <table> <!-- Aqui creamos la tabla donde mostraremos todas las ligas con sus respectivos atributos y toda la linea a la vez sera un enlace para poder modificar esa liga haciendo clic sobre ella -->

                    <c:forEach var="iteracion" items="${ligaActual}">

                        <tr onclick="window.location.href = '${pageContext.request.contextPath}/SvLiga?accion=consultar&codigo=${iteracion.codigo}'">

                            <td>${iteracion.codigo}</td>

                            <td>${iteracion.nombre}</td>

                            <td>${iteracion.descripcion}</td>

                            <td>${iteracion.fundacion}</td>

                            <td onclick="event.stopPropagation();
                                    borrarLiga(${iteracion.codigo})" style="cursor: pointer;"> <!-- Aqui hemos creado una imagen de una papelera donde le hemos puesto la funcion de eliminar haciendo clic sobre ella -->

                                <img src="/LigasDeFutbol/imagenes/eliminar.png" style="width: 25px; background-color: #4CAF50"/>

                            </td>

                        </tr>

                    </c:forEach> <!-- En el caso de que la base de datos nos devuelva el array de ligas vacio nos mostraria este mensaje o si en la busqueda no encuentra ninguna coincidencia tambien lo devolveria -->

                    <c:if test="${empty ligaActual}">

                        <a class="empty">¡No hay ligas con ese nombre!</a>

                    </c:if>

                </table>

                <br><br>

                <!-- Aqui hemos puesto dos enlaces uno para volver al index.html y otro para acceder a liga.jsp y poder crear una nueva liga -->

                <a class="empty" href="/LigasDeFutbol/SvLiga?accion=nueva">Crear Liga</a>&nbsp;&nbsp;&nbsp;

                <a class="empty" href="/LigasDeFutbol/index.html">Volver</a>

            </div>

            <div class="infoDerecha"> <!-- Aqui hemos puesto un pequeño texto para el usuario donde la frase en azul es un enlace que te lleva a equipo.jsp para que el usuario cree un nuevo equipo antes de crear una liga si no lo tiene -->

                <p style="color: black; font-family: Comic Sans MS, cursive; font-size: 16px; margin-bottom: 15px; line-height: 1.6">

                    <img src="/LigasDeFutbol/imagenes/exclamacion.png" style="width: 25px"/>

                    <strong>¿Aún no tienes un equipo?</strong><br>

                    ¡No te preocupes! Puedes crear el tuyo propio y empezar a competir.

                </p>

                <p style="font-size: 15px; margin-top: 10px">

                    <img src="/LigasDeFutbol/imagenes/manoderecha.png" style="width: 40px"/>

                    <a href="/LigasDeFutbol/SvEquipo?accion=nueva" style="color:blue; font-family: Comic Sans MS, cursive; text-decoration: none; font-weight: bold">

                        Haz clic aquí para crear un equipo.

                    </a>

                </p>

            </div>

        </div>

        <img style="margin: 30px auto; display: block; border-radius: 8px; width: 50%;" src="imagenes/jugadores.png"/>

        <div class="piepagina">

            2025 Liga de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>