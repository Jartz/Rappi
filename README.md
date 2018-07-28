# Rappi- Movie/TvSeries Test
<p>Diseño de App Android con consumo de RestApi, para la visualizacion de imagenes de cartelera,informacion y trailer de seres TV y peliculas .
la aplicacion puede ser usada de forma offline ya que guarda en el cache la informacion</p>

# Autor
<p>Julian Andres Ramos Trujillo - 27Julio 2018</p>



## Include
Plataforma
<li><a href="https://developer.android.com/studio/">Android</a> - Kotlin SDk 23-26</li>
<br>

Api
<li><a href="https://developers.themoviedb.org/4">https://developers.themoviedb.org/4</a></li>
<li><a href="https://developers.google.com/youtube/">Youtube Api</a></li>
<br>

Cache
<li>10MB</li>
<li>Max Time:20Min</li>
<br>

## Configuración
La carpeta Util contiene archivos necesarios para configurar el proyecto datos tale como
<li>BaseApi</li>
<li>api_key</li>
<li>time_cache_min</li>
<li>cache_size</li>

<br>
Librerias
<li><a href="http://square.github.io/retrofit/">Retrofit:</a> Manejo de consumo de rest api en la App android</li>
<li><a href="https://developers.themoviedb.org/4">OkHttp:</a>Gestionar el Cache para las consultas online</li>
<li><a href="https://github.com/bumptech/glide">Glide:</a>visualizacion de imagenes remotas en ImageView</li>

## Vistas/Capa Presentacion
El sistema cuenta con 4 vistas principales:
<br>
<li><strong>Activity_main</strong> :Esta actividad, servirar como contenedor de los respectivos fragmentos y como layout de menu inferior.</li>
<li><strong>fragment_home</strong> :El usuario podra visualizar 2 secciones (recyclerView) de Peliculas y series; estos dos reciclerview se desplega de forma horizontal</li>
<li><strong>fragment_Category</strong> :El usuario podra visualizar las categorias (Top,Popular,Upcomming) tanto de peliculas como de series. de acuerdo a la opcion del menu inferior</li>
<li><strong>fragment_Single</strong> :Podra Ver el detalle de la pelicula informacion tal como (Nombre completo,Año de emision,Descripcion general) y ademas de esto podra observar un trailer con inicio automatico</li>
<li><strong>fragment_Search</strong> : Puedes realizar un filtro de peliculas o Tv series, cada vez que introduces un caracter, el sistema buscara en la ba se deatos</li>


## Clases/Negocio
<br>
<li><strong>ApiService</strong> :Interfaz Gestiona la consulta a la RestApi por paremitrazacion de Retrofit y serializa en los modelos.</li>
<li><strong>loadMovie</strong> :Funcion que solicita el consumo de api en el area de peliculas.Recibe como parametros(Reciclerview,ListArray<mMovie>,context)</li>
<li><strong>loadTvSeries</strong> :Funcion que solicita el consumo de api en el area de TvSeries.Recibe como parametros(Reciclerview,ListArray<mMovie>,context).</li>
<li><strong>SearchMultiple</strong> : Funcion solicita el consumo de api de search multpiple es activadad por addTextChangedListener del EditText.Recibe como parametros(Reciclerview,ListArray<mMovie>,context).</li>
<li><strong>Adapter(Movie,TvSeties,Search)</strong> : Adaptadores que Gestiona la visualizaicion de datos en reciclerview.</li>

## Capa Persistencia
<br>
<li><strong>CacheSetting</strong> : Se encargar de gestionar el almacenamiento en el cache, de las consulta realizada a la api.su configuracion actual es de 10mb y persistencia de 20min</li>


--------------------------------------

# Preguntas Realizadas
<strong>1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?</strong>
<br>
RTA:/ Consiten en que objeto solo debe realizar una única cosa. y su proposito es proteger nuestro código frente a cambios
<br>
<strong>2. Qué características tiene, según su opinión, un “buen” código o código limpio? </strong>
<br>
Un buen codigo o codigo limpio, es un codigo que cualquier programador puede entender sin necesidad de preguntar al que lo desarrollo.


