import React from 'react';
import { sparqlConnect } from 'sparql-connect';
import Viz from './viz';
import Spinner from 'js/components/shared/spinner';
import D from 'js/i18n';

const queryBuilder = () => `
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dbo: <http://dbpedia.org/ontology/>
PREFIX gn: <http://www.geonames.org/ontology#>
PREFIX j.0: <https://ld.utpl.edu.ec/dataCOVID/ontology#>  
PREFIX schema: <http://schema.org/> 
prefix covidOnto: <https://ld.utpl.edu.ec/dataCOVID/ontology#>
prefix ogc: <http://www.opengis.net/ont/geosparql#>

PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
select distinct ?label ?activePop ?unemployedPop ?contours where { 
    ?country rdf:type dbo:Country.
    ?country dbo:name ?label.
    ?country dbo:population ?activePop.
    ?country covidOnto:geometry ?geometry.
    ?res gn:locatedIn ?country.  
    ?res j.0:quantity ?unemployedPop. 
    ?res schema:observationDate ?date. 
    BIND(STRDT(STR(?geometry), ogc:wktliteral) AS ?contours)
    
    FILTER CONTAINS (?date, "19/6/2020") 
    FILTER CONTAINS (str(?res), "/confirmed-cases/") 
    FILTER CONTAINS (str(?contours), "(")
    
} ORDER BY DESC(?unemployedPop)
`;

const connector = sparqlConnect(queryBuilder, {
	queryName: 'unemployementData',
});

const VizContainer = ({ unemployementData }) => (
	<div>
		<Viz data={unemployementData} />
	</div>
);

export default connector(VizContainer, {
	loading: () => <Spinner text={D.loading} />,
});
