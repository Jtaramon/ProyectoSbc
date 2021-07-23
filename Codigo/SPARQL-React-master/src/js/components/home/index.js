import React from 'react';
import { Link } from 'react-router-dom';
import { items } from 'js/components/router/component-list';
import obtencion from '../../../img/obtencion.png';
import generacion from '../../../img/generacion.png';
import almacenamiento from '../../../img/almacenamiento.PNG';
import proceso from '../../../img/proceso.png';
import {NavigationUnfoldLess} from "material-ui/svg-icons/index.es";

export default () => (

	<ul>
		{items.map(
			({ route, title }, i) =>
				i !== 0 &&
				(route ? (
					<li key={i} className="item">
						<Link to={route}>{title}</Link>
					</li>
				) : (
					<React.Fragment key={i}>
						<br />
						<h2 className="item-title centered">
							<i>{title}</i>
						</h2>
					</React.Fragment>
				))
		)}
		<h1><strong>Proceso</strong></h1>
		<p>El proceso inicia con la busqueda de fuentes de datos relacionados con estadísticas del Covid 19 en Latinoamérica, seguidamente se procede a extraer esta infromación para
			proceder a limpiarla y estructurarla como paso previo para la representación empleando el modelo ontológico, posteriormente empleando Java y Jena (librería para procesamiento LOD).
		Se procedio a generar las tripletas (Sujeto - Predicado - Objeto), las cuales representan la información de forma entendible para máquinas y humanos. Posteriormente las tripletas fueron
			alojadas en  GraphDB (Triple Store) el cual provee varias ventajas, entre ellas un endpoint Sparql necesario para la explotación de datos. Finalmente se desarrollo una aplicación web
		empleando React Js, para de este modo permitis al usuario realizar las consultas del grafo de conocimiento sin necesidad de un conocimiento especializado.</p>
		<img style={{width: "100%"}} src={proceso}/>
		
		<h1><strong>Generación (Obtención, Limpieza, Enlazado de datos)</strong></h1>
		<p>Para la parte de generación, que comprende la obtención, limpieza y enlazado, se está considerando la aplicación de la siguiente estrategia, que consiste en:
			<ul>
				<li>Extracción de todos los datos de los diferentes dataset antes analizados.</li>
				<li>Limpieza y disposición de datos en otro fichero.</li>
				<li>Filtrado de datos en función a país con el objetivo de obtener únicamente datos de los países latinoamericanos.</li>
				<li>Filtrado de campos acorde a los requeridos en las clases del modelo ontológico.</li>
				<li>Creación de Script para lectura de fichero y posterior uso de los datos.</li>
			</ul>
		</p>
		<img style={{width: "90%"}}  src={obtencion}/>
		<img style={{width: "90%"}} src={generacion}/>
	</ul>

);
