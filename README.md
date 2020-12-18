# SemanticWebProject
Our Project is based on Lyon City Guide most of the data are situated in Lyon area. The project is divided into 2 parts:
## 1.Extract Data Project Folder
* Read the static data either from CSV or from JSON file as well as API.
* Generate a model as per our ontology.
* Save it in Triple Store in our case Apache Jena Fuseki.
* We have also stored dynamic bicycle and weather data in our triple store by reading respective APIs.

## 2. Web development for Lyon City Guide in bicycleSharingStations Project Folder

* Contains REST API to query RDF data from triple store in CityGuideService.java file.
* In our Bootstrap interface we have 3 tabs:
### 1.Home Page- Static Data
*  Available Choices
1.   We can view available Hospitals
2.  Available Bus number and their corresponding stops.
3.  Available Metro Line and their corresponding stops.
4.  Available Tram Line and their corresponding stops.
5.   Train stations available all over in France and the trains arrival and departure time with some more information.
6.  Bicycle stops in lyon and their capacity.
### 2. Search Live Bicycle- Dynamic Data
 Here we can select data and time and can see the real time bicycle data under Available column with current weather informations.

### 3.Museum:Queried from wikidata end point
 Fetching information about museums in lyon from wikidata end point by querying and displaying in our UI.

## How to Run the Project 

We have 2 project folders: extractData and bicycleSharingStations. 

* Run Fuseki Sever.
* Add new dataset as "city"
* Open the project extractData and open the package, complete (extractdata\src\main\java\complete\).
* Run 'CityStaticCreateModel.java’ program to extract static data of Hospitals,Bicycle,SNCF,Bus,Tram,Metro.Wait until the process completes, it gives a success message, "Finished!", on console.
* Run DynamicLyon.java, AddWeather.java files to extract dynamic data. Wait until the process completes.
 
Now the first part of the project is done. All the data are retrieved,modelled and saved in triple store. To verify that browse
 the http://localhost:3030/city, with any query from CityGuideService.java file and you can see the data.

* Open the project folder bicycleSharingStations and open package com.emse.lyonCityGuide .
* Run the file BicycleSharingStationApplication.java.You will get a message "Tomcat started on port(s): 8080".
* Now browse the http://localhost:8080 to see the web site. Select your choice from dropdown list, you can see the data.

## Video Demo
 (Demo  URL : [Click Here](https://youtu.be/yym4AZcQvnQ)) 






 
