import { sparqlConnect } from 'sparql-connect';

const queryBuilder = () => `
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
PREFIX dbo: <http://dbpedia.org/ontology/> 

SELECT DISTINCT  ?label ?id where {
	?id rdf:type dbo:Country.
    ?id dbo:name ?label.
    }
    ORDER BY ?label
`;

export default sparqlConnect(queryBuilder, {
	queryName: 'countries',
});
