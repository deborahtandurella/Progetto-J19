# Progetto-J19 (Clique)
Applicazione web  per critici gastronomici , i quali posso esprimere critiche riguardanti la loro esperienza nei ristoranti iscritti al servizio. I proprietari di ristoranti posso iscrivere le loro attività per essere valutate dai critici.

## Come iniziare
### Avvio del server( porta 8282 )
- scaricare tutte le dipendenze,compilare il progetto e generare la directory **target** :

``` $ mvn compile```

- eseguire il progetto :

``` $  mvn exec:java -Dexec.mainClass=net.Main```
### Client
Supponendo che la connessione avvenga dalla stessa macchina su cui è attivo il server, connetersi al server tramite un browser alla pagina :
[Clique](http://localhost:8282/home)


## Requisiti
- [JAVA JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 
(testato sulle versioni 1.8.0_201 e 1.10)

- [JDBC](https://dev.mysql.com/downloads/connector/j/)

- [Jetty](https://www.eclipse.org/jetty/)

## Come eseguire i test automatici

- eseguire tutti test :

``` $  mvn test```

- eseguire un particolare metodo del file di test:

``` $  mvn test -Dtest=test.TestClassName#testMethodName```


## Built with
- [MAVEN](https://maven.apache.org/) - Dependecy Management
- [Free SQL Database](https://www.freesqldatabase.com/) - Database Host
- [JETTY](https://www.eclipse.org/jetty/) - Web Server
