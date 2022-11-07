# Caso3-Infracomp
* Juan Felipe Serrano 201921654


librerias permitidas:
* java.security 
* javax.crypto

Fecha estimada de que todo este listo: El Viernes 4 de Noviembre a media noche

Responsabilidades:

| Numero del paso | Responsable de la implementacíon | Resumen |
| --------------------------------- | -------------------------------- | ------------- |
|14|  TODOS LOS INTEGRANTES |  INFORME DEL CASO 3 |
|1|  SERRANO(Nullvind)  | Mandar mensaje iniciar conexion |
|2|  SERRANO(Nullvind)  | Servidor Genera parametros (G,P,Gx,x) para DH |
|3|  SERRANO(Nullvind)  | Servidor comparte G, P y Gx con el cliente firmados por su llave privada|
|4|  SERRANO(Nullvind)  | Cliente verifica la firma |
|5|  SERRANO(Nullvind)  | Cliente le dice al servidor si acepta la conexion o si la rechaza |
|6A|  SERRANO(Nullvind)  | Cliente Calcula parametros (Gy,y) para DH|
|6B|  SERRANO(Nullvind) | Cliente manda Gy al servidor|
|7A|  SERRANO(Nullvind) | Servidor calcula la llave maestra compartida(Gy,x,G,P)|
|7B|  SERRANO(Nullvind) | Cliente calcula la llave maestra compartida(Gy,x,G,P)|
|7.1A|  <nombre integrante> | Cliente genera con la llave maestra: La llave simetrica para cifrar(K_AB1), la llave simetrica para HMAC(K_AB2) y iv1| 
|7.1B|  <nombre integrante> | Servidor genera con la llave maestra: La llave simetrica para cifrar(K_AB1), la llave simetrica para HMAC(K_AB2) y iv2| 
|8|  <nombre integrante>  | Cliente le manda al servidor su consulta cifrada por K_AB1 y K_AB2 con el iv1|
|9|  <nombre integrante>  | Servidor verifica la consulta por ambos cifrados |
|10| <nombre integrante>  | Servidor le comunica al cliente si acepta o no la consulta |
|11|  <nombre integrante> | Servidor manda la respuesta cifrada por K_AB1 y K_AB2 con el iv2|
|12|  <nombre integrante> | Cliente Verifica respuesta de ambos cifrados |
|13|  <nombre integrante> | Cliente manda si acepta o no la respuesta |

