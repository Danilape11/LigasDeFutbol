<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html style="background: url('/LigasDeFutbol/imagenes/fondo.jpg') no-repeat center center fixed;
      background-size: cover;"> <!-- Metemos una imagen de fondo y le damos su respectivo estilo -->

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>NUEVA LIGA</title>

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

            .titulo {
                font-size: 28px;
                color: #ffffff;
                margin: 30px;
                text-align: center;
            }

            hr {
                border: 0;
                height: 2px;
                background: #ffffff;
                margin: 20px auto;
                width: 80%;
            }

            .formulario {
                font-size: 18px;
                color: #ffffff;
                line-height: 1.8;
                margin-bottom: 20px;
                padding: 10px;
                width: 40%;
            }

            .formulario input[type="text"] {
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
                gap: 1px;
            }

            .imagen {
                width: 50%;
            }

            .imagen img {
                width: 535px;
                border-radius: 12px;
                transition: 0.3s;
            }

            .imagen img:hover {
                transform: scale(1.03);
            }

            .footer {
                background-color: #4CAF50;
                color: white;
                text-align: center;
                padding: 12px;
                margin-top: 50px;
                font-size: 14px;
            }

        </style>

    </head>

    <body>

        <div class="header">⚽ NUEVA LIGA ⚽</div> <!-- Encabezado con su clase que se queda fijo arrba aun haciendo scroll hacia abajo -->

        <hr>

        <div class="contenedor">

            <!-- Aqui creamos un formulario el cual el usuario va a rellenar para crear su liga o si esta modificando una liga en cada input del formulario apareceran los datos de la liga a modificar -->

            <form class="formulario" action="${pageContext.request.contextPath}/SvLiga" method="post">

                <input type="hidden" id="accion" name="accion" value="guardar"/>

                <input type="hidden" id="nombre" name="codigo" value="${ligaActual.codigo}"/>

                <label>Código:</label> ${ligaActual.codigo}<br>

                <label>Nombre:</label><br>

                <input type="text" id="nombre" name="nombre" value="${ligaActual.nombre}"/><br>

                <label>Descripción:</label><br>

                <input type="text" id="descripcion" name="descripcion" value="${ligaActual.descripcion}"/><br>

                <label>Fundación:</label><br>

                <input type="text" id="fundacion" name="fundacion" value="${ligaActual.fundacion}"/><br>

                <button type="submit">Guardar</button> <!-- Boton para guardar o crear la liga que te lleva a la pagina ligas.jsp con tu liga modificada o creada y tambien hay un boton de volver por si no quieres al final modificar o crear que te devuelve a la pagina anterior que es ligas.jsp -->

                <a class="volver" href="/LigasDeFutbol/SvLiga?accion=listar">Volver</a>

            </form>

            <div class="imagen">

                <img src="/LigasDeFutbol/imagenes/liga.png" alt="Imagen liga">

            </div>

        </div>

        <div class="footer">

            2025 Ligas de Fútbol | Desarrollado por Dani ⚽

        </div>

    </body>

</html>