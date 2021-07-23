/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbcejemplos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriterI;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFWriter;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;

/**
 *
 * @author Alexis Montoya
 */
public class TripletasRDF {

    /**
     * @param args the command line arguments
     */
    static String DataFilePath = "C:/Users/Alexis Montoya/Desktop/SBC/TripletasProyecto/"; //Data
    static String personas = "person.csv";
    static String book = "book.csv";
    static String documento = "Document.csv";
    static String gb = "GlobalCitation.csv";
    static String journals = "journal-article.csv";
    static String authorship = "auship.csv";

    static String GenFilePath = "C:/Users/Alexis Montoya/Desktop/SBC/TripletasProyecto/GeneralRDF4.rdf";

    public static void main(String[] args) throws FileNotFoundException {

        //CAMBIAR POR RUTAS DEL EQUIPO DONDE SE EJECUTE
        //Get data from CSV and store in a list
        List<person> persons = readPersonsFromCSV(DataFilePath + personas);
        List<libro> libros = readbooksFromCSV(DataFilePath + book);
        List<document> docu = readDocsFromCSV(DataFilePath + documento);
//        List<article> articulo = read(DataFilePath + gb);
        List<journales> jpurn = readJournsFromCSV(DataFilePath + journals);
        List<authorship> ship = readShipsFromCSV(DataFilePath + authorship);
        //List<person> persons = readDocumentFromCSV(DataFilePath + personas);
        Model model = ModelFactory.createDefaultModel();
        File f = new File(GenFilePath); //File to save the results of RDF Generation
        FileOutputStream os = new FileOutputStream(f);
        String dataPrefix = "http://ky.utpl.edu.ec/publicicovid/data#";//cambiar /por # .... data/
        model.setNsPrefix("data", dataPrefix);
        Model datmodel = ModelFactory.createDefaultModel();
        //Fijar prefijos de vocabularios incorporados en Jena

        String EventPrefix = "http://purl.org/NET/c4dm/event.owl#";
        model.setNsPrefix("event", EventPrefix);
        Model evetmodel = ModelFactory.createDefaultModel();

        String CPrefix = "http://purl.org/spar/c4o/";
        model.setNsPrefix("c4o", CPrefix);
        Model cpmodel = ModelFactory.createDefaultModel();

        String vcard = "http://www.w3.org/2006/vcard/ns#";
        model.setNsPrefix("vcard", vcard);
        Model vcmodel = ModelFactory.createDefaultModel();

        String foaf = "http://xmlns.com/foaf/0.1/";
        model.setNsPrefix("foaf", foaf);
        Model foafmodel = ModelFactory.createDefaultModel();

        String dbo = "http://dbpedia.org/ontology/";
        model.setNsPrefix("dbo", dbo);
        Model dboModel = ModelFactory.createDefaultModel();

        String vivo = "http://vivoweb.org/ontology/core#";
        model.setNsPrefix("vivo", vivo);
        Model vvmodel = ModelFactory.createDefaultModel();

        String bibo = "http://purl.org/ontology/bibo#";
        model.setNsPrefix("bibo", bibo);
        Model bibomodel = ModelFactory.createDefaultModel();

        String dct = "http://purl.org/dc/terms/";
        model.setNsPrefix("dct", dct);
        Model dctmodel = ModelFactory.createDefaultModel();

        String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        model.setNsPrefix("rdf", rdf);
        Model rdfmodel = ModelFactory.createDefaultModel();

        Resource fuente = null, paper = null, palabra = null, autor = null, libro = null, journals = null, tship = null;

        for (libro lib : libros) {
            System.out.println(lib);
            libro = model.createResource(bibo + lib.getIsbn());

        }
        for (journales jour : jpurn) {
            System.out.println(jour);
            journals = model.createResource()
                    .addProperty(dctmodel.getProperty(dct + "title"), jour.getTitle())
                    .addProperty(bibomodel.getProperty(bibo + "shortTitle"), jour.getShorttitle())
                    .addProperty(vcmodel.getProperty(vcard + "url"), jour.getUrl());

        }
        //paper.addProperty(RDF.type,journals);
        for (document doc : docu) {
            System.out.println(doc);
            paper = model.createResource(dataPrefix + doc.getDoi())//dataPrefix prefix en vez de bibo 
                    .addProperty(bibomodel.getProperty(bibo + "abstract"), doc.getAbst())
                    .addProperty(vcmodel.getProperty(vcard + "url"), doc.getUrl())
                    .addProperty(dctmodel.getProperty(dct + "title"), doc.getTitle())
                    .addProperty(dctmodel.getProperty(dct + "date"), doc.getDate())
                    .addProperty(vvmodel.getProperty(vivo + "numPage"), doc.getPage())
                    .addProperty(dctmodel.getProperty(dct + "language"), doc.getLanguaje())
                    .addProperty(bibomodel.getProperty(bibo + "doi"), doc.getDoi())
                    .addProperty(bibomodel.getProperty(bibo + "volume"), doc.getVolumen());
                    // agrergar propiedad rdf type
        }

        for (person b : persons) {
            System.out.println(b);
            autor = model.createResource(dataPrefix + b.getId())
                    .addProperty(FOAF.lastName, b.getNombre())
                    .addProperty(FOAF.firstName, b.getApellido());

        }
        for (authorship osh : ship) {
            System.out.println(osh);
            tship = model.createResource(dataPrefix + osh.getIdperson())
                    .addProperty(datmodel.getProperty(dataPrefix + "idpersona"), osh.getIdperson())
                    .addProperty(datmodel.getProperty(dataPrefix + "doiDocu"), osh.getDoidoc());

        }

        paper.addProperty(RDF.type, journals);
        libro.addProperty(RDF.type, paper);

        StmtIterator iter = model.listStatements();

        System.out.println(
                "TRIPLES");
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
        // now write the model in XML form to a file

        System.out.println(
                "MODELO RDF------");
        model.write(System.out,
                "RDF/XML-ABBREV");

        // Save to a file
        RDFWriterI writer = model.getWriter("RDF/XML");

        writer.write(model, os,
                "");

        //Close models
        dboModel.close();

        model.close();
    }

    private static List<person> readPersonsFromCSV(String fileName) {
        List<person> persons = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                person persono = createPerson(attributes);

                // adding person into ArrayList
                persons.add(persono);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return persons;
    }

    private static List<libro> readbooksFromCSV(String fileName) {
        List<libro> books = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                libro persono = createLibro(attributes);

                // adding person into ArrayList
                books.add(persono);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return books;
    }

    private static List<journales> readJournsFromCSV(String fileName) {
        List<journales> journs = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                journales persono = createJour(attributes);

                // adding person into ArrayList
                journs.add(persono);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return journs;
    }

    private static List<document> readDocsFromCSV(String fileName) {
        List<document> documents = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                document doc = createDocmen(attributes);

                // adding person into ArrayList
                documents.add(doc);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return documents;
    }

    private static List<authorship> readShipsFromCSV(String fileName) {
        List<authorship> auth = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(";");

                authorship doc = createAthr(attributes);

                // adding person into ArrayList
                auth.add(doc);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return auth;
    }

    private static person createPerson(String[] metadata) {

        String id = metadata[0];
        String nombre = metadata[1];
        String apellido = metadata[2];

        // create and return person of this metadata
        return new person(id, nombre, apellido);
    }

    private static libro createLibro(String[] metadata) {

        String isbn = metadata[0];
        String doi = metadata[1];

        // create and return person of this metadata
        return new libro(isbn, doi);
    }

    private static document createDocmen(String[] metadata) {

        String abst = metadata[0];
        String url = metadata[1];
        String title = metadata[2];
        String date = metadata[3];
        String page = metadata[4];
        String languaje = metadata[5];
        String doi = metadata[6];
        String volumen = metadata[7];
        // create and return person of this metadata
        return new document(abst, url, title, date, page, languaje, doi, volumen);
    }

    private static authorship createAthr(String[] metadata) {

        String idperson = metadata[0];
        String doidoc = metadata[1];
        // create and return person of this metadata
        return new authorship(idperson, doidoc);
    }

    private static journales createJour(String[] metadata) {

        String title = metadata[0];
        String shorttitle = metadata[1];
        String url = metadata[2];
        // create and return person of this metadata
        return new journales(title, shorttitle, url);
    }

}
//Person Class

class person {

    private String id;
    private String nombre;
    private String apellido;

    public person(String id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}

class libro extends document {

    private String isbn;

    public libro(String isbn, String doi) {
        super(doi);
        this.isbn = isbn;
    }

   

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}

class document {

    private String abst;
    private String url;
    private String title;
    private String date;
    private String page;
    private String languaje;
    private String doi;
    private String volumen;

    public document(String abst, String url, String title, String date, String page, String languaje, String doi, String volumen) {
        this.abst = abst;
        this.url = url;
        this.title = title;
        this.date = date;
        this.page = page;
        this.languaje = languaje;
        this.doi = doi;
        this.volumen = volumen;
    }

    public document(String doi) {
        this.doi = doi;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLanguaje() {
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

}

class article {

}

class authorship {

    private String idperson;
    private String doidoc;

    public authorship(String idperson, String doidoc) {
        this.idperson = idperson;
        this.doidoc = doidoc;
    }

    public String getIdperson() {
        return idperson;
    }

    public void setIdperson(String idperson) {
        this.idperson = idperson;
    }

    public String getDoidoc() {
        return doidoc;
    }

    public void setDoidoc(String doidoc) {
        this.doidoc = doidoc;
    }

}

class journales {

    private String title;
    private String shorttitle;
    private String url;

    public journales(String title, String shorttitle, String url) {
        this.title = title;
        this.shorttitle = shorttitle;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShorttitle() {
        return shorttitle;
    }

    public void setShorttitle(String shorttitle) {
        this.shorttitle = shorttitle;
    }
}
