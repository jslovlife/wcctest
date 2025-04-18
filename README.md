# Demo Test for WCC
This project is using jdk17 with spring boot 3.4.4

### How to create a mysql db in docker
Execute command as below:
> docker-compose up -d

Execute command as below if wish to remove the docker container:
>docker-compose down -v --remove-orphans && docker volume rm demo_mysql_data

### How to start the project
> ./mvnw spring-boot:run

### How to package the project
Execute command as below:
> mvn clean package

You may find the jar file under "target" folder