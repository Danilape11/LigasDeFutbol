<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- 
    Document   : jugador
    Created on : 21 abr 2025, 15:40:12
    Author     : AM8-PC16
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;">   <!-- Ponemos una imagen de fondo con su respectivos estilos -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Aqui le damos estilo a las clases que he creado mas abajo -->

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

            .formulario{
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
                margin-top: 50px;
                margin-right: 160px;
                width: 38%;
            }

            .imagen img {
                width: 580px;
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

        <title>NUEVO JUGADOR</title>

    </head>

    <body>

        <div class="header">⚽ NUEVO JUGADOR ⚽</div>

        <hr>

        <!-- Creamos un formulario para poder modificar a los jugadores o para crear jugadores desde cero -->

        <div class="contenedor">

            <form class="formulario" action="${pageContext.request.contextPath}/SvJugador" method="post">

                <input type="hidden" id="accion" name="accion" value="guardar"/>

                <input type="hidden" id="nombre" name="codigo" value="${jugadorActual.codigo}"/>

                <label for="codigo">Codigo: ${jugadorActual.codigo}</label><br><br>

                <label for="nombre">Nombre:</label>

                <input type="text" id="nombre" name="nombre" value="${jugadorActual.nombre}"/><br><br>

                <label for="apellidos">Apellidos:</label>

                <input type="text" id="apellidos" name="apellidos" value="${jugadorActual.apellidos}"/><br><br>

                <label for="idEquipo">Equipo: </label>

                <select id="idEquipo" name="idEquipo">

                    <option value="0">Selecciona un equipo</option>

                    <c:forEach var="iteracion" items="${equipoActual}">

                        <c:set var="seleccionado" value=""></c:set>

                        <c:if test="${jugadorActual.idEquipo == iteracion.codigo}">

                            <c:set var="seleccionado" value="selected"></c:set>

                        </c:if>

                        <option value="${iteracion.codigo}" ${seleccionado}>${iteracion.nombre}</option>

                    </c:forEach>

                </select><br><br>

                <label for="posicion">Posicion: </label>

                <input type="text" id="posicion" name="posicion" value="${jugadorActual.posicion}"/><br><br>

                <label for="piernaHabil">Pierna Habil:</label>

                <input type="text" id="piernaHabil" name="piernaHabil" value="${jugadorActual.piernaHabil}"/><br><br><br>

                <!-- Aqui he puesto un boton de guardar para guardar la informacion del jugador creado nuevo o si estamos modificando un jugador ya creado que te lleva a jugadores.jsp y un enlace de volver a la pagina jugadores.jsp por si no quiere el usuario crear o modificar nada -->

                <button type="submit">Guardar</button>

                <a class= "volver" href="/LigasDeFutbol/SvJugador?accion=listar">Volver</a>

            </form>

            <div class="imagen">

                <img src="/LigasDeFutbol/imagenes/jugador.png" alt="Imagen jugador">

            </div>

        </div>

        <div class="piepagina">

            2025 Ligas de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>
