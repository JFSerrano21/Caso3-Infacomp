# Caso3-Infacomp
* Juan Felipe Serrano 201921654


librerias permitidas:
* java.security 
* javax.crypto

Fecha estimada de que todo este listo: El Viernes 4 de Noviembre a media noche

Responsabilidades:

| Numero del paso | Responsable de la implementacíon | Resumen |
| --------------------------------- | -------------------------------- | ------------- |
|14|  TODOS LOS INTEGRANTES |  INFORME DEL CASO 3 |
|1|  <nombre integrante>  | Mandar mensaje iniciar conexion |
|2|  <nombre integrante>  | Servidor Genera parametros (G,P,Gx,x) para DH |
|3|  <nombre integrante>  | Servidor comparte G, P y Gx con el cliente firmados por su llave privada|
|4|  <nombre integrante>  | Cliente verifica la firma |
|5|  <nombre integrante>  | Cliente le dice al servidor si acepta la conexion o si la rechaza |
|6A|  <nombre integrante>  | Cliente Calcula parametros (Gy,y) para DH|
|6B|  <nombre integrante>  | Cliente manda Gy al servidor|
|7A|  <nombre integrante> | Servidor calcula la llave maestra compartida(Gy,x,G,P), luego genera: La llave simetrica para cifrar(K_AB1), la llave simetrica para HMAC(K_AB2) y iv2|
|7B|  <nombre integrante> | Cliente calcula la llave maestra compartida(Gy,x,G,P), luego genera: La llave simetrica para cifrar(K_AB1), la llave simetrica para HMAC(K_AB2) y iv1|
|8|  <nombre integrante>  | Cliente le manda al servidor su consulta cifrada por K_AB1 y K_AB2 con el iv1|
|9|  <nombre integrante>  | Servidor verifica la consulta por ambos cifrados |
|10| <nombre integrante>  | Servidor le comunica al cliente si acepta o no la consulta |
|11|  <nombre integrante> | Servidor manda la respuesta cifrada por K_AB1 y K_AB2 con el iv2|
|12|  <nombre integrante> | Cliente Verifica respuesta de ambos cifrados |
|13|  <nombre integrante> | Cliente manda si acepta o no la respuesta |

