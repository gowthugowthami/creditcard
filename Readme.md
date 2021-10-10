# M1 Starter Web

M1 Starter Web is a maven archetype for a web enabled, data 
enabled web application.


* run `java -jar src/main/h2/h2.142.jar` in one terminal
* allow h2 to direct you to a login screen. Change the Jdbc Url 
to **jdbc:h2:~/m1-starter-web/src/main/h2/starter**
* click login on h2's gui
* open a second terminal and run `mvn:jetty:run`
* H2 will create the necessary database
* browse to `http://localhost:8080/`

**the database must be created **src/main/h2/starter**.

The project expects there to be a directory named **h2** within
the projects **src/main** directory. The basic datasource connects to an H2 instance 
at src/main/resources/h2/starter. The setup is a little more hands on 
and will make you appreciate the efforts that Spring and Grails 
have gone through to enable Developers : )


**M1 Todos**
* Method overload for Q.list one method without new Object[]{}
* replace ' with '' on save & update for string

mvn archetype:generate                       \
  -DarchetypeGroupId=dev.j3ee                \
  -DarchetypeArtifactId=m1-starter-web       \
  -DarchetypeVersion=0.2-SNAPSHOT            \
  -DgroupId=dev.j3ee                         \
  -DartifactId=barter