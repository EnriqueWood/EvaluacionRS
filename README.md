
INSTRUCCIONES DE USO 

I. ¿Cómo ejecutar el programa?

	a. 	Navege hasta el carpeta en donde se encuentran los archivos descargados, 
		con extensión .java utilizando una terminal de Linux - MacOS o 
		Símbolo del sistema en caso de trabajar bajo ambiente Windows.

	b. 	Ejecute el siguiente comando para generar los archivos class:

		javac TestRedd.java

	c. 	Realice la ejecución del programa de la siguiente forma para realizar
	 	una ejecución de prueba:

		java TestRedd

II. Consideraciones

	i. De Ejecución:
	
		La ejecución de este programa puede ser realizada mediante parámetros 
		entregados al momento de abrir el programa de a siguiente manera:
	
		java TestRedd [-<parametro> <argumento>]

	
		Al ejecutar el programa sin parámetros, éste buscará dentro del directorio json, 
		que se encuentra donde están los archivos .class generados un archivo
		con nombre "redd-test-data.json".

	 	Para utilizar el programa con otro archivo json, o definir la salida en un 
	 	archivo de texto se deben considerar los siguientes parámtros:
	
			-i <ruta_archivo_de_entrada>	Ruta completa al archivo de entrada 
											(Debe ser un archivo con estructura JSON). 
											En caso de omitirse este parámetro, }
											el programa buscará un archvio llamado redd-test-data.json 
											ubicado en el mismo directorio que los archivos .class generados.
	
			-o <archivo_salida>				En caso de existir este parámetro, la salida
											del programa se generará en un archivo .txt 
											con el nombre dado, el nombre debe escribirse 
											entre comillas en caso de poseer espacios.
	
			-r <num_resultados>				Establece la cantidad de artículos semejantes a incluir en el
											listado (limitado según cantidad de productos en el archivo JSON).

			-p <articulo_buscar>			Establece el artículo a buscar, el nombre debe escribirse 
											entre comillas en caso de poseer espacios.
	

	ii. De Uso:
	
		El ingreso de nombre de artículos es sensible a mayúsculas y minúsculas.
		
		El programa hace uso de la librería json-simple 1.1.1, la cual es open-source y 
		se puede encontrar en la siguiente dirección 
		https://code.google.com/archive/p/json-simple/downloads, sin embargo en el 
		repositorio GIT ya se encuentra compilada.
	
		NOTA: Tildes y otros acentos gráficos dentro del programa han sido omitidos de forma intencional.
		
III. Contacto

	Ante cualquier consulta acerca de este programa puede contactarme al correo ewoodg@hotmail.com
