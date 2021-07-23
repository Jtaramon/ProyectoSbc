import { sparqlConnect } from 'sparql-connect';

const queryBuilder = () => `
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
PREFIX dbo: <http://dbpedia.org/ontology/> 

SELECT DISTINCT  ?label ?id where {
    ?country dbo:name ?nameCountry.
      ?id rdf:type dbo:Province.
    ?id dbo:province ?country.
      ?id dbo:name ?label1.
	bind(concat(?label1,"-",?nameCountry) as ?label)
    }
    ORDER BY ?label
`;

export default sparqlConnect(queryBuilder, {
	queryName: 'departements',
});
