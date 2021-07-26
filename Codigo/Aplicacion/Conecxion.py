from SPARQLWrapper import SPARQLWrapper, JSON
import urllib3

sparql = SPARQLWrapper(
     'http://localhost:3030/ds/sparql')


def get_1():
    sparql.setQuery('''
        PREFIX foaf: <http://xmlns.com/foaf/0.1/>
        PREFIX vivo: <http://vivoweb.org/ontology/core#>
        PREFIX bibo: <http://purl.org/ontology/bibo#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX fab: <http://purl.org/fab/ns#>
        PREFIX bib: <http://zeitkunst.org/bibtex/0.1/bibtex.owl#>
        PREFIX co: <http://purl.org/ontology/co/core#>
        PREFIX dc: <http://purl.org/dc/elements/1.1/>
        PREFIX dct: <http://purl.org/dc/terms/>
        PREFIX data: <http://ky.utpl.edu.ec/publicicovid/data/>
    #Publicaciones en Ingles con su apellido
        select DISTINCT  ?nombreDocumento ?doi ?apellido where { 
        ?documento dct:title ?nombreDocumento.
        ?documento dct:language "en".
        ?documento bibo:doi ?doi .
        ?autorid data:doiDocu ?doi .
        ?autorid data:idpersona ?id .
        ?idpersona data:idpersona ?id .
        ?idpersona foaf:firstName ?apellido
        }limit 100
    

    ''')
    sparql.setReturnFormat(JSON)
    qres = sparql.query().convert()
    return qres


def get_2():
    sparql.setQuery('''
        PREFIX foaf: <http://xmlns.com/foaf/0.1/>
        PREFIX vivo: <http://vivoweb.org/ontology/core#>
        PREFIX bibo: <http://purl.org/ontology/bibo#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX fab: <http://purl.org/fab/ns#>
        PREFIX bib: <http://zeitkunst.org/bibtex/0.1/bibtex.owl#>
        PREFIX co: <http://purl.org/ontology/co/core#>
        PREFIX dc: <http://purl.org/dc/elements/1.1/>
        PREFIX dct: <http://purl.org/dc/terms/>
        PREFIX data: <http://ky.utpl.edu.ec/publicicovid/data/>
        PREFIX fabio: <http://purl.org/spar/fabio/>
        #Todas las publicaciones, nombre y fecha de publicación
        SELECT DISTINCT ?doi ?date ?nombreDocumento where { 
        ?documento dct:title ?nombreDocumento.
        ?documento bibo:doi ?doi .
        ?documento dct:date ?date
        }
        LIMIT 100


    ''')
    sparql.setReturnFormat(JSON)
    qres = sparql.query().convert()
    return qres

def get_3():
    sparql.setQuery('''
        PREFIX foaf: <http://xmlns.com/foaf/0.1/>
        PREFIX vivo: <http://vivoweb.org/ontology/core#>
        PREFIX bibo: <http://purl.org/ontology/bibo#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX fab: <http://purl.org/fab/ns#>
        PREFIX bib: <http://zeitkunst.org/bibtex/0.1/bibtex.owl#>
        PREFIX co: <http://purl.org/ontology/co/core#>
        PREFIX dc: <http://purl.org/dc/elements/1.1/>
        PREFIX dct: <http://purl.org/dc/terms/>
        PREFIX data: <http://ky.utpl.edu.ec/publicicovid/data/>
        PREFIX fabio: <http://purl.org/spar/fabio/>
        PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>
        #Publicaciones en Ingles con su apellido
        SELECT DISTINCT ?nombreDocumento ?shortDocumento ?url   WHERE { 
            ?documento dct:title ?nombreDocumento.
            ?documento bibo:shortTitle ?shortDocumento.
            ?url vcard:url ?shortDocument
        }


    ''')
    sparql.setReturnFormat(JSON)
    qres = sparql.query().convert()
    return qres

def get_4():
    sparql.setQuery('''
        PREFIX foaf: <http://xmlns.com/foaf/0.1/>
        PREFIX vivo: <http://vivoweb.org/ontology/core#>
        PREFIX bibo: <http://purl.org/ontology/bibo#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX fab: <http://purl.org/fab/ns#>
        PREFIX bib: <http://zeitkunst.org/bibtex/0.1/bibtex.owl#>
        PREFIX co: <http://purl.org/ontology/co/core#>
        PREFIX dc: <http://purl.org/dc/elements/1.1/>
        PREFIX dct: <http://purl.org/dc/terms/>
        PREFIX data: <http://ky.utpl.edu.ec/publicicovid/data/>
        #Publicaciones en Español con el Nombre del Autor
        SELECT DISTINCT ?nombreDocumento ?doi ?nombre WHERE { 
           ?documento dct:title ?nombreDocumento.
           ?documento dct:language "es".
           ?documento bibo:doi ?doi .
           ?autorid data:doiDocu ?doi .
           ?autorid data:idpersona ?id .
           ?idpersona data:idpersona ?id .
           ?idpersona foaf:firstName ?nombre
            }LIMIT 1000
    ''')
    sparql.setReturnFormat(JSON)
    qres = sparql.query().convert()
    return qres

def get_5():
    sparql.setQuery('''
        PREFIX foaf: <http://xmlns.com/foaf/0.1/>
        PREFIX vivo: <http://vivoweb.org/ontology/core#>
        PREFIX bibo: <http://purl.org/ontology/bibo#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX fab: <http://purl.org/fab/ns#>
        PREFIX bib: <http://zeitkunst.org/bibtex/0.1/bibtex.owl#>
        PREFIX co: <http://purl.org/ontology/co/core#>
        PREFIX dc: <http://purl.org/dc/elements/1.1/>
        PREFIX dct: <http://purl.org/dc/terms/>
        PREFIX data: <http://ky.utpl.edu.ec/publicicovid/data/>
        #Documentos que han escrito los autores
        SELECT DISTINCT ?nombreDocumento ?nombre (COUNT(?nombreDocumento) AS ?total) WHERE { 
            ?documento dct:title ?nombreDocumento.
            ?documento bibo:doi ?doi .
            ?autorid data:doiDocu ?doi .
            ?autorid data:idpersona ?id .
            ?idpersona data:idpersona ?id .
            ?idpersona foaf:firstName ?nombre .
            }GROUP BY ?nombreDocumento ?nombre ORDER BY DESC(?total)
    ''')
    sparql.setReturnFormat(JSON)
    qres = sparql.query().convert()
    return qres