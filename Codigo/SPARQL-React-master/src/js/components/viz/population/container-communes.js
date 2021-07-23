import React from 'react';
import { sparqlConnect } from 'sparql-connect';
import Viz from './viz';
import Spinner from 'js/components/shared/spinner';
import D from 'js/i18n';

const queryBuilder = departement => `

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
PREFIX dbo: <http://dbpedia.org/ontology/> 
PREFIX j.0: <https://ld.utpl.edu.ec/dataCOVID/ontology#> 
PREFIX schema: <http://schema.org/> 
PREFIX gn: <http://www.geonames.org/ontology#>

SELECT DISTINCT  ?label ?value ?province where {
    ?province rdf:type dbo:Province.
    ?province dbo:province <${departement}>.
    ?province dbo:name ?label.
    ?res gn:locatedIn ?province.  
    ?res j.0:quantity ?value.
    ?res schema:observationDate ?date.
    
    FILTER CONTAINS (?date, "19/06/2020") 
    FILTER CONTAINS (str(?res), "/confirmed-cases/") 
    }
    ORDER BY ?label
`;

const connector = sparqlConnect(queryBuilder, {
	queryName: 'communePopulation',
	params: ['country'],
});

const ContainerCommunes = ({ communePopulation }) => (
	<Viz data={communePopulation} />
);

export default connector(ContainerCommunes, {
	loading: () => <Spinner text={D.loading} />,
});
