#Travel agency WEB

## How to start:
In central project directory:
* Run ``mvn clean install``
* Run ``cd web``
* Run ``mvn tomcat7:run`` 

* Web is available at localhost:8080/pa165

First you must log in. You can find log form with more informations at http://localhost:8080/pa165/auth/login
* If you will log in as admin: 
You can list all trips and see their details. You can also create new trip, update and delete existing trip. You can do the same for excursions.
You can list all users and see their details. You can change other users role and update some of yours personal informations.
You can list all reservations and see their details. You can also create new reservations delete yours existing reservations.
You can log out.

* If you will log in as customer: 
You can list all trips and see their details.
You can see you own personal details. You can also update some of yours personal informations.
You can list your reservations and see their details. You can also create new reservations and delete your existing reservations.
You can log out.
