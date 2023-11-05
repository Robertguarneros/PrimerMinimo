# PrimerMinimo 

## Parte 1: 5 puntos

### 1.-Identificar las entidades de información y sus propiedades. Podéis hacer una propuesta de atributos básica.

Para la primera parte del mínimo 1, he decidio utilizar la siguiente estructura:
- Package **edu.upc.edu** contiene lo siguiente:
> - **exceptions:** folder donde se guardan las excepciones utilizadas en GameManager, GameManagerImpl y en GameService. Son útiles para poder responder a las peticiones con ciertos códigos y poder identificar si hay errores y cúales son al momento de usar Swagger.
> - **models:** folder donde tenemos los tres archivos de las clases de objetos Game, User y Match. Y subpackage auxiliarclasses donde tenemos LevelStats, MatchStats y NextLevel. Estas clases son necesarias para poder responder a las consultas que requieren atributos no incluidos en las clases de Objetos como la fecha o poder cambiar de nivel. Creo que esta es una buena de mantener las clases organizadas y separadas evitando tener circulos con objetos de transferencia.
> - **services:** aquí tenemos el fichero GameService. Aquí es donde se implementa todo lo relacionado al API REST.
> - **GameManager:** interfaz donde describimos las funciones necesarias para que funcione nuestra API así como las 8 operaciones del enunciado.
> - **GameManagerImpl:** implementación de la interfaz GameManager.
> - **Main:** Main donde se crea el servicio con Jersey y Swagger.
- Package **test** contiene la implementación del test con JUNIT en GameManagerImpl

 ### 2.- Especificación del componente que implementará las operaciones descritas anteriormente: GameManager Java:
Necesitamos los siguientes métodos:
- numberOfUsers para obtener el número de usuarios
- numberOfGames para obtener el número de juegos
- getGame para obtener un juego dado un ID
- getUser para obtener un usuario dado un ID
- getMatch para obtener la partida actual de un usuario
- createUser para crear un usuario
- createGame para crear un juego
- createMatch(String gameID, String userID)throws GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsCurrentlyInMatchException;

Operaciones del enunciado:
- getLevelFromMatch para obtener el nivel actual de la partida actual de un usuario. Puede tener excepciones si el usuario no existe, o si el usuario no esta en una partida.
- getMatchPoints para obtener los puntos de la partida actual de un usuario. Puede tener excepciones si el usuario no existe, o si el usuario no esta en una partida.
- NextLevel para cambiar incrementar el nivel de la partida de un usuario. Puede tener excepciones si el usuario no existe, o si el usuario no esta en una partida.
- EndMatch para terminar la partida actual de un usuario. Puede tener excepciones si el usuario no existe, o si el usuario no esta en una partida.
- getUserHistoryForGame para obtener una lista de usuarios que han jugado a un juego ordenado por puntos en orden descendiente. Puede tener excepciones si el juego no existe.
- getUserMatches para obtener una lista con las partidas que ha jugado un usuario. Puede tener excepciones si el usuario no existe. 
- getStatsFromUserPerGame para obtener información de las partidas de un usuario en un juego específico. Puede tener excepciones si el usuario no existe o si el juego no existe. 

### 3.- Implementación de una Fachada (patrón de diseño) que implemente el interfaz definido previamente.
Ver la implementación en GameManagerImpl.

### 4.- Implementación de un test (JUNIT) sobre el componente desarrollado. Se debe implementar cuatro operaciones de test anteriormente descritas.
Ver la implementacion en GameManagerImplTest. 

**Nota:** _Con JUNIT no funcionaba el test de createMatch ya que regresaba errores null._ 
Después de consultar con chatGPT, me sugirió que utilizará HashMaps para no tener que hacer búsqudas y poder localizar la información más rápido y con menos complejidad. 
Utilicé Javadocs de HashMap https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html

Después de aprender a usar HashMaps logré implementarlo y funcionó todo de manera correcta. 

## PARTE II: 5 puntos
### 1.- Definir (servicio, operaciones, rutas, métodos HTTP, peticiones, respuestas, códigos de respuesta) e implementar un servicio REST que permita realizar las operaciones especificadas en la primera parte del ejercicio. Se recomienda que todas las operaciones deben retornar “objetos de transferencia” y evitar ciclos/relaciones. Si los objetos de negocio son complejos se complica la serialización/deserialización. El servicio debe utilizar el componente construido en el punto anterior.
Ver la implementación utilizando las operaciones definidas en GameManagerImpl en el fichero GameService.
**Es importante comentar que nunca me funcionó bien swagger, lo pude abrir un par de veces pero no pude utilizarlo bien para pobrar todos los métodos con HTTP**