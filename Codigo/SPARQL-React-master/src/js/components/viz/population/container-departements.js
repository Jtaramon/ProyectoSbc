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

select distinct ?label ?poblacion ?value  where { 
    ?country rdf:type dbo:Country.
    ?country dbo:name ?label.
    ?country dbo:population ?poblacion.
    ?res gn:locatedIn ?country.  
    ?res j.0:quantity ?value. 
    ?res schema:observationDate ?date. 
    
    FILTER CONTAINS (?date, "19/6/2020") 
    FILTER CONTAINS (str(?res), "/confirmed-cases/") 
    
} ORDER BY DESC(?value)
`;

const connector = sparqlConnect(queryBuilder, {
	queryName: 'departementPopulation',
});

const ContainerDepartements = ({ departementPopulation }) => (
	<Viz data={departementPopulation} />
);

export default connector(ContainerDepartements, {
	loading: () => <Spinner text={D.loading} />,
});
