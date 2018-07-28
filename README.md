# Rappi- Movie/TvSeries Test
<p>Diseño de App Android con consumo de RestApi, para la visualizacion de imagenes de cartelera,informacion y trailer de seres TV y peliculas .
la aplicacion puede ser usada de forma offline ya que guarda en el cache la informacion</p>

## Funcionalidad.
<li>Ver seccion de peliculas y tv series</li>
<li>Ver las Seccion de top,popular y upcoming por pelicula  o serie</li>
<li>Ver el datelle de cada elementos(peliculas o serie)</li>
<li>Reproduccion automtica de trailer en detalle</li>
<li>Busqueda de peliculas o series al teclar solamente</li>
<li>Alamcenamiento en cache de listado y filtros realizados para reproduccion Offline</li>

## Compatibilidad
Plataforma
<li><strong>Minimum Android SDK</strong>: Glide v4 requires a minimum API level of 26.</li>
<li><strong>Compile Android SDK</strong>: Glide v4 requires you to compile against API 23 or later.</li>
<li>Retrofit:2.3.0'</li>
<li>okhttp:3.11.0</li>
<li>glide:4.2.0</li>
<li>rxandroid:2.0.1</li>
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
La carpeta Util contiene archivos necesarios para configurar el proyecto datos tale como.
<li><strong>base_api</strong>: url de consumo de api https://api.themoviedb.org/3/</li>
<li><strong>api_key</strong>: llave para consumo de Api.</li>
<li><strong>time_cache_min</strong>: tiempo maximo de persistencia en cache.</li>
<li><strong>cache_size</strong>: tamaño de alamacenamiento en cache.</li>

<br>
Librerias
<li><a href="http://square.github.io/retrofit/">Retrofit:</a> Manejo de consumo de rest api en la App android.</li>
<li><a href="https://developers.themoviedb.org/4">OkHttp:</a>Gestionar el Cache para las consultas online.</li>
<li><a href="https://github.com/bumptech/glide">Glide:</a>visualizacion de imagenes remotas en ImageView.</li>

## Vistas/Capa Presentacion
El sistema cuenta con 4 vistas principales:
<br>
<li><strong>Activity_main</strong> :Esta actividad, servirar como contenedor de los respectivos fragmentos y como layout de menu inferior.</li>
<li><strong>fragment_home</strong> :El usuario podra visualizar 2 secciones (recyclerView) de Peliculas y series; estos dos reciclerview se desplega de forma horizontal.</li>
<li><strong>fragment_Category</strong> :El usuario podra visualizar las categorias (Top,Popular,Upcomming) tanto de peliculas como de series. de acuerdo a la opcion del menu inferior.</li>
<li><strong>fragment_Single</strong> :Podra Ver el detalle de la pelicula informacion tal como (Nombre completo,Año de emision,Descripcion general) y ademas de esto podra observar un trailer con inicio automatico.</li>
<li><strong>fragment_Search</strong> : Puedes realizar un filtro de peliculas o Tv series, cada vez que introduces un caracter, el sistema buscara en la ba se deatos.</li>


## Clases/Negocio
<br>
<li><strong>ApiService</strong> :Interfaz Gestiona la consulta a la RestApi por paremitrazacion de Retrofit y serializa en los modelos.</li>
<li><strong>Cartelera</strong> :clase que tiene como objectivo la manipulacion de datos obtenidos por medio de las funciones
<li>setListFilter</li>
<li>setListByMediType</li>
</li>

<li><strong>setListFilter</strong> : constructor de la clase Cartelera recibe como parametro (query: String, reciclerView: RecyclerView, context: Context, progress: ProgressBar) que tiene como objectivo enviar a reciclerview la solicitud filtrada.por el string obtenido en query</li>
<li><strong>setListByMediType</strong> :  constructor de la clase Cartelera que recibe como parametros (typeApi:String,categoryApi: String,reciclerView: RecyclerView,context: Context,progress:ProgressBa). con el fin de visualizar en reciclerview</li>

## Capa Persistencia
<br>
<li><strong>CacheSetting</strong> : Se encargar de gestionar el almacenamiento en el cache, de las consulta realizada a la api.su configuracion actual es de 10mb y persistencia de 20min</li>

--------------------------------------

# Preguntas Realizadas
<strong>1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?</strong>
<br>
Consiten en que objeto solo debe realizar una única cosa. y su proposito es proteger nuestro código frente a cambios
<br>
<strong>2. Qué características tiene, según su opinión, un “buen” código o código limpio? </strong>
<br>
Un buen codigo o codigo limpio, es un codigo que cualquier programador puede entender sin necesidad de preguntar al que lo desarrollo.


--------------------------------------

# Autor
<p>Julian Andres Ramos Trujillo - 27 Julio 2018</p>

# Licencia

```
  Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

