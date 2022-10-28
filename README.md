# SpringBatch


For spring boot application:
Before run this application you should perform the follows steps:
- Postgre DB install on your machine
- Postgre should be run on 5432 port number
- A new db must be created with "template1" name
  
  Note: If you do NOT want to apply the above steps You must modify the "application.yml" file according to your desired.
  
  This application retrieves data from people.csv file via Spring Batch API and then writes this data into the database.
  
  You can able to CRUD oparation on the API service.
  If you want to use swagger ui for API documentation, please click the link:
  http://localhost:8080/swagger-ui.html
  
  To run demo (Spring boot), the follows commands should be run on the terminal
  ```sh
   ./gradlew bootRun
  ```
  
