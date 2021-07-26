import Conecxion as con


res=""
n= int(input("1. ¿Cuáles son las publicaciones en ingles y sus autores?\n 2.¿Cuáles son todas las publicaciones y fechas de publicación?\n 3. ¿Cuáles son los nombres de las publicaciones, los títulos cortos y el url?\n 4. ¿Cuáles son las publicaciones en español y sus autores?\n 5.¿Cuáles son los números de Documentos que han escrito los autores?\n"))

if n==1:
    qres = con.get_1()
    for i in range(len(qres['results']['bindings'])):
        result = qres['results']['bindings'][i]
        name, ing, image_url = result['nombreDocumento']['value'], result['doi']['value'], result['apellido']['value']
        mensaje = "nombreDocumento: " + name + " doi: " + ing + " apellido:" + image_url
        print(mensaje)
elif n==2:
    qres = con.get_2()
    for i in range(len(qres['results']['bindings'])):
        result = qres['results']['bindings'][i]
        name, ing, image_url = result['doi']['value'], result['date']['value'], result['nombreDocumento']['value']
        mensaje = "Doi: " + name + " Fecha: " + ing + " Titulo:" + image_url
        print(mensaje)
elif n==3:
    qres = con.get_3()
    for i in range(len(qres['results']['bindings'])):
        result = qres['results']['bindings'][i]
        name, ing, image_url = result['nombreDocumento']['value'], result['shortDocumento']['value'], result['url']['value']
        mensaje = "Title: " + name + " Short-Title: " + ing + " Uri:" + image_url
        print(mensaje)
elif n==4:
    qres = con.get_4()
    for i in range(len(qres['results']['bindings'])):
        result = qres['results']['bindings'][i]
        name, ing, image_url = result['nombreDocumento']['value'], result['doi']['value'], result['nombre']['value']
        mensaje = "Title: " + name + " Doi: " + ing + " Nombre:" + image_url
        print(mensaje)
elif n==5:
    qres = con.get_5()
    for i in range(len(qres['results']['bindings'])):
        result = qres['results']['bindings'][i]
        name, image_url = result['nombreDocumento']['value'], result['nombre']['value']
        mensaje = "Title: " + name + " Nombre:" + image_url
        print(mensaje)

