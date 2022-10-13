# Car Rental Service 

REST API for a simple car rental service.
 
## Description

A REST API that fulfil the requirements detailed below:

Register a car with its plate number, model and year

Search for available cars to rent from date/time, to date/time, maximum rental price per hour

Register a user (to rent a car)

Book a car from date/time, to date/time, user id, car id

## Running

1. Java 8 and Maven 3 should be installed.

2. Change directory to the root folder of the application.

3. Run the below maven command to generate the JAR file.

```bash
mvn clean package
```

4. Run the JAR file

```bash
java -jar target/car-rental-service-0.0.1-SNAPSHOT.jar
```

5. Go to the below URL - the URL will be redirected to Swagger UI.

```bash
http://localhost:8080
Or
https://prudentialcarrental-prucarbooking.azuremicroservices.io/swagger-ui.html
```

## Endpoints

1. register user (/users) - HTTP POST
  ```
  https://prudentialcarrental-prucarbooking.azuremicroservices.io/users
  ```
- Sample Request - body

```
{
  "email": "sharath.babu@gmail.com",
  "name": "Sharath Babu"
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2022-10-05T02:07:47.07",
  "name": "Sharath Babu",
  "email": "sharathbabu1323@gmail.com"
}
```

2. register car (/cars) - HTTP POST
  ```
   https://prudentialcarrental-prucarbooking.azuremicroservices.io/users
  ``` 
- Sample Request - body

```
{
  "model": "BMW 650",
  "plateNumber": "AWE2443",
  "year": 2019,
  "availableFrom": "2022-10-05T21:00",
  "availableTo": "2022-10-30T21:00",
  "pricePerHour": 10
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2022-10-05T02:09:32.519",
  "plateNumber": "AWE2443",
  "model": "BMW  650",
  "year": 2019,
  
}
```
3. update car availability (/cars/availability/{carId}) - HTTP PUT
```
https://prudentialcarrental-prucarbooking.azuremicroservices.io//cars/availability/1

- Sample Request (/cars/availability/1) - body
```
{
  "availableFrom": "2022-10-13T21:00",
  "availableTo": "2022-10-30T21:00",
  "pricePerHour": 10
}
- Sample Response
```
{
    "id": 1,
    "created": "2022-10-04T11:41:37.29",
    "plateNumber": "AWE2443",
    "model": "BMW - X6",
    "year": 2019,
    "pricePerHour": 10,
    "availableFrom": "2022-10-13T21:00:00",
    "availableTo": "2022-10-30T21:00:00"
}

4. search cars availability (/cars) - HTTP GET
  ```
   https://prudentialcarrental-prucarbooking.azuremicroservices.io/cars?maxPricePerHour=100&page=0&pageSize=5&rentFrom=2022-10-05T21%3A00&rentTo=2021-10-30T21%3A00
   ```
   
- Sample Request - body

```
/cars?maxPricePerHour=100&page=0&pageSize=5&rentFrom=2022-10-05T21%3A00&rentTo=2022-10-30T21%3A00
```

- Sample Response

```
[
  {
    "id": 2,
    "created": "2022-10-05T02:22:33.009",
    "plateNumber": "34DS3",
    "model": "Toyota Camry",
    "year": 2005,
    "pricePerHour": 10,
    "availabeFrom": "2022-10-05T21:00:00",
    "availabeTo": "2022-10-30T21:00:00"
  },
  {
    "id": 1,
    "created": "2022-10-05T02:15:30.827",
    "plateNumber": "AWE2443",
    "model": "BMW - 650",
    "year": 2019,
    "pricePerHour": 10,
    "availabeFrom": "2022-10-05T21:00:00",
    "availabeTo": "2022-10-30T21:00:00"
  }
]
```

5. create booking (/bookings) - HTTP POST
   ```
   https://prudentialcarrental-prucarbooking.azuremicroservices.io/bookings
   ```
- Sample Request - body

```
{
  "carId": 1,
  "userId": 1,
  "beginning": "2022-10-06T21:00",
  "end": "2021-10-07T21:00"  
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2022-10-5T02:25:07.536",
  "beginning": "2022-10-06T21:00:00",
  "end": "2022-10-07T21:00:00",
  "user": {
    "id": 1,
    "name": "Sharath Babu",
    "email": "sharathbabu1323@gmail.com"
  },
  "car": {
    "id": 1,
    "plateNumber": "AWE2443",
    "model": "BMW 650",
    "year": 2019
  }
}
```

## Technologies

1. Java 8

2. Spring boot with Spring data JPA and Hibernate.

3. Maven 3

4. Postgres database

5. Junit 4

6. Swagger UI

7. SonarLint
