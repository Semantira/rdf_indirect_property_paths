RDF in-direct paths
============================

The code has been written to solve some specific issue faced by the author while finding in-direct relations (path) between two concepts under various namespaces adopted by a domain ontology.

Note: Code has been written using Netbean (Java) 

###  Purpose
Code has been written per particular usage for author's project but may help someone else confronting similar issue.


## Supported Platforms

- **Java**<br>

currently it only supports RDF/XML only.


### Input
RDF file (sample files are in data folder)

The parser finds in-direct relations among RDF concepts e.g. relation between a 'Book' and publication date. There is no direct property that can relate these two and we have a multi-link path to find this relation i.e. 'Book' got a publication event, A publication even got property event time, Event time got a Calendar year that has a label. In short we will come up with following statements (for SPARQL)

{?book a bibo:Book}

{?event a blt:PublicationEvent}

{?book blt:publication ?event}

{?event event:time ?time}

{?time rdfs:label ?year}

The code returns simple string statements that can be converted to SPARQL statements or saved in database (whatever needed)

### Output

Node Paths Called
Paths: [
        {http://purl.org/ontology/bibo/Book [dct:creator] http://xmlns.com/foaf/0.1/Agent},   
        {http://purl.org/ontology/bibo/Book [dct:creator] http://xmlns.com/foaf/0.1/Person}, 
        {http://purl.org/ontology/bibo/Book [dct:creator] http://purl.org/dc/terms/Agent}
      ]
      
Node Paths Called

Paths: [
        {http://purl.org/ontology/bibo/Book [dct:creator] http://xmlns.com/foaf/0.1/Person}
        {http://xmlns.com/foaf/0.1/Person  [bio:event]  http://purl.org/vocab/bio/0.1/Birth}
      ]
      
Node Paths Called

Paths: [
        {http://purl.org/ontology/bibo/Book[dct:creator]http://xmlns.com/foaf/0.1/Person}
        {http://xmlns.com/foaf/0.1/Person[bio:event]http://purl.org/vocab/bio/0.1/Death}
       ]

### Implementation

## ChangeLog

