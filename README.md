# AppMaberintos

[imagen1]:https://github.com/odrajaf/TutorialesPDM/blob/master/images/mabe1.png
[imagen2]:https://github.com/odrajaf/TutorialesPDM/blob/master/images/mabe2.png
[imagen3]:https://github.com/odrajaf/TutorialesPDM/blob/master/images/mabe3.png

La aplicación Maberintos se ha creado usando Android Studio 2.3.3 usando canvas como principal motor de desarrollo, además del uso del acelerometro.

El juego consite en que se le lanza una pregunta con una imagen al jugador a la vez que un laberinto que deberá de resolver usando el acelerometro para mover la bola. Una captura del primer nivel es la siguiente: 

![alt text][imagen1]

Las vidas que se ven como corazones, solo se agotan si se terminan los 60s que se dan por vida. En caso de agotar todas las vidas se muestra un mensaje de Game Over y reinicia el nivel. Las colisiones que tiene la bola azul hacen que el jugador tenga que empezar desde el principio del laberinto pero sin gastar vidas.

Una vez el jugador ha logrado llegar a la meta aparece de nuevo la pregunta del inicio teniendo que responder correctamente para pasar de nivel sino volverá tener que pasarse el mismo nivel de nuevo.

![alt text][imagen2]

En caso de responder correctamente se guardará mediante **sqlite** el nivel por el que se queda, para luego en el menu de inicio al darle a continuar sigar por el nivel que dejó.

Las opciones también se guardan mediante sqlite pudiendo guardar la sensibilidad del acelerometro, el color de la bola y si vibra o no cuando colisiona la bola.

![alt text][imagen3]
