<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notificación de Error - DentalCare</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #333333;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h1 {
            color: #d32f2f;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .error-card {
            background-color: #ffebee;
            border-left: 4px solid #d32f2f;
            padding: 15px;
            margin: 15px 0;
            border-radius: 4px;
        }
        .error-card p {
            color: #b71c1c;
            font-weight: bold;
            margin: 0;
            font-size: 16px;
        }
        p {
            font-size: 14px;
            line-height: 1.6;
            margin: 10px 0;
        }
        .label {
            font-weight: bold;
            color: #555555;
        }
        .footer {
            text-align: center;
            font-size: 12px;
            color: #777777;
            margin-top: 20px;
            border-top: 1px solid #eeeeee;
            padding-top: 10px;
        }
        @media only screen and (max-width: 600px) {
            .container {
                margin: 10px;
                padding: 15px;
            }
            h1 {
                font-size: 20px;
            }
            .error-card p {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Notificación de Error</h1>
        <div class="error-card">
            <p>Error: ${error_name!"Error Desconocido"}</p>
        </div>
        <p><span class="label">Detalles:</span> ${details!"Sin detalles disponibles"}</p>
        <p><span class="label">Ubicación:</span> ${ubicacion!"Excepción no localizada"}</p>
        <p><span class="label">URL:</span> ${url!"URL Desconocida"}</p>
        <div class="footer">
            <p>Esta es una notificación automática de la aplicación <strong>DentalCare</strong>.</p>
        </div>
    </div>
</body>
</html>