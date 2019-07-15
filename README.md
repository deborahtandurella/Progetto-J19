# Progetto-J19 (Clique)
Applicazione web  per critici gastronomici , i quali posso esprimere critiche riguardanti la loro esperienza nei ristoranti iscritti al servizio. I proprietari di ristoranti posso iscrivere le loro attività per essere valutate dai critici.

## Set up del sistema

### Requisiti
- [JAVA JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 
(testato sulle versioni 1.8.0_201 e 1.10)

- [JDBC](https://dev.mysql.com/downloads/connector/j/) - versione 5.1.47

- [Jetty](https://www.eclipse.org/jetty/) - versione 9.4.17.v20190418

- [MAVEN](https://maven.apache.org/) - versione 3.6.1

### Avvio del server
- il numero della porta del server è configurabile tramite linea di comando(se non specificata il programma usa la porta 8282)
- scaricare tutte le dipendenze,compilare il progetto e generare la directory **target** :

``` $ mvn compile```

- eseguire il progetto :

``` $  mvn exec:java -Dexec.mainClass=net.Main -Dexec.args="portNumber" ```

dove portNumber è il numero della porta desiderata. 
### Accedere al servizio
Supponendo che la connessione avvenga dalla stessa macchina su cui è attivo il server, connetersi al server alla pagina:
[Clique](http://localhost:8282/home)

In generale, se addressOfMyServer è l'indirizzo del server e ServerPort è la porta di quest'ultimo dedicata al servizio, connettersi tramite l'indirizzo http://addressOfMyServer:ServerPort/home


## Come eseguire i test automatici

- eseguire tutti test :

``` $  mvn test```

- eseguire un particolare metodo del file di test:

``` $  mvn test -Dtest=test.TestClassName#testMethodName```


## Built with
- [MAVEN](https://maven.apache.org/) - Dependecy Management
- [Free SQL Database](https://www.freesqldatabase.com/) - Database Host
- [JETTY](https://www.eclipse.org/jetty/) - Web Server
