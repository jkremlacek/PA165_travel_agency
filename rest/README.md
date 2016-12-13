#Travel agency REST (for trips)

## How to start:
In central project directory:
* Run ``mvn clean install``
* Run ``cd rest``
* Run ``mvn`` 

* Rest is available at localhost:8080/pa165/rest

List all trips
```
curl -i -X GET http://localhost:8080/pa165/rest/trips
```
Find trips by id
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/1
```
Delete trip
```
curl -i -X DELETE http://localhost:8080/pa165/rest/trips/1
```
Create trip
```
curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Nice Austria","dateFrom":"2017-02-02 21:57","dateTo":"2017-02-09 21:57","destination":"Austria, Alps","description":null,"capacity":50,"price":6399.00,"excursions":[],"reservations":[]}' http://localhost:8080/pa165/rest/trips/create
```
Update trip
```
curl -X PUT -i -H "Content-Type: application/json" --data '{"id":4,"name":"Endurance through USA","dateFrom":"2017-12-20 15:09","dateTo":"2018-01-20 15:09","destination":"USA","description":null,"capacity":100,"price":149999.00,"excursions":[],"reservations":[]}' http://localhost:8080/pa165/rest/trips/update
```
List trips with free capacity
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/free
```
Find trips by destination
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/destination?destination=USA
```
Find trips in next X days
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/nextDays?number=20
```
Find trips by participants
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/trip?tripId=3
```
Find by name
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/name?name=Gorgeous%20London
```
Find trips by excursion
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/excursion?excursionId=3
```

Find by capacity
```
curl -i -X GET http://localhost:8080/pa165/rest/trips/capacity?capacity=150
```

Find by price
```
curl -i -X GET 'http://localhost:8080/pa165/rest/trips/price?min=100&max=90000'
```
Find by date
```
curl -i -X GET 'http://localhost:8080/pa165/rest/trips/date?from=2016-02-02&to=2018-02-02'
```



