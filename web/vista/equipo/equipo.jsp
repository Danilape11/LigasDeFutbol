<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;"> <!-- Aqui ponemos una imagen de fondo con su respectivo estilo -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>NUEVO EQUIPO</title>

        <!-- Aqui le damos estilo a las clases creadas mas abajo -->

        <style>

            body {
                margin: 0;
                font-family: "Comic Sans MS", cursive;
            }

            .header {
                background-color: #4CAF50;
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
                width: 50%;
            }

            .formulario input[type="text"],
            .formulario select {
                padding: 8px;
                border-radius: 6px;
                border: none;
                width: 300px;
                margin-bottom: 15px;
            }

            .formulario button {
                background-color: #60de5b;
                border: 2px solid white;
                border-radius: 8px;
                color: #ffffff;
                padding: 10px 18px;
                cursor: pointer;
                font-size: 16px;
                transition: 0.3s;
            }

            .formulario button:hover {
                background-color: #4CAF50;
            }

            a.volver {
                color: white;
                text-decoration: none;
                font-size: 16px;
                margin-left: 20px;
                transition: 0.3s;
            }

            a.volver:hover {
                color: #FFD700;
                text-decoration: underline;
            }

            .contenedor {
                display: flex;
                justify-content: flex-start;
                align-items: flex-start;
                margin: 40px;
                gap: 30px;
            }

            .imagen {
                margin-right: 200px;
                width: 38%;
            }

            .imagen img {
                width: 480px;
                border-radius: 12px;
                transition: 0.3s;
            }

            .imagen img:hover {
                transform: scale(1.03);
            }

            .piepagina {
                background-color: #4CAF50;
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

    </head>

    <body>

        <div class="header">⚽ NUEVO EQUIPO ⚽</div>

        <hr>

        <div class="contenedor">

            <!-- Aqui creamos un formulario para poder crear los nuevos datos de nuestro nuevo equipo o modificar si estamos modificando un equipo -->

            <form class="formulario" action="${pageContext.request.contextPath}/SvEquipo" method="post">

                <input type="hidden" id="accion" name="accion" value="guardar"/>

                <input type="hidden" id="nombre" name="codigo" value="${equipoActual.codigo}"/>

                <label>Código:</label> ${equipoActual.codigo}<br>

                <label>Nombre:</label><br>

                <input type="text" id="nombre" name="nombre" value="${equipoActual.nombre}"/><br>

                <label>Liga:</label><br>

                <select id="idLiga" name="idLiga">

                    <option value="0">Selecciona una liga</option>

                    <c:forEach var="iteracion" items="${ligaActual}">

                        <c:set var="seleccionado" value=""></c:set>

                        <c:if test="${equipoActual.idLiga == iteracion.codigo}">

                            <c:set var="seleccionado" value="selected"></c:set>

                        </c:if>

                        <option value="${iteracion.codigo}" ${seleccionado}>${iteracion.nombre}</option>

                    </c:forEach>

                </select><br>

                <label>Presidente:</label><br>

                <input type="text" id="presidente" name="presidente" value="${equipoActual.presidente}"/><br>

                <label>Entrenador:</label><br>

                <input type="text" id="entrenador" name="entrenador" value="${equipoActual.entrenador}"/><br>

                <label>Año de Fundación:</label><br>

                <input type="text" id="yearFundacion" name="yearFundacion" value="${equipoActual.yearFundacion}"/><br><br>

                <!-- Aqui he puesto un boton para poder guardar la informacion que el usuario a modificado o guardar el equipo nuevo creado y un enlace que te lleva a equipos.jsp por si el usuario no quiere modificar o crear -->

                <button type="submit">Guardar</button>

                <a class="volver" href="/LigasDeFutbol/SvEquipo?accion=listar">Volver</a>

            </form>

            <div class="imagen">

                <img src="/LigasDeFutbol/imagenes/equipo.png" alt="Imagen equipo">

            </div>

        </div>

        <div class="piepagina">

            2025 Ligas de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>