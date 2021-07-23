import { sparqlConnect } from 'sparql-connect';

const queryBuilder = dsd => `
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbo: <http://dbpedia.org/ontology/>
PREFIX gn: <http://www.geonames.org/ontology#>
select DISTINCT ?id ?label where { 
	?country rdf:type ${dsd}.
    ?res gn:locatedIn ?country.
    FILTER CONTAINS (str(?res), "statistic")
    BIND(SUBSTR(str(?res),49) AS ?labelAux)
    BIND(STRBEFORE(?labelAux,"/CO") AS ?label)
    BIND(?label AS ?id)
}
`;

export default sparqlConnect(queryBuilder, {
	queryName: 'dimensions',
	params: ['dsd'],
});
