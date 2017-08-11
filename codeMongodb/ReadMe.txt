
Server:

As i am using MongoCLient i had to run the server on my local machine on cmd.

Run mongod       -----         C:\Program Files\MongoDB\Server\3.4\bin>mongod



sensor-emulator:

Ran this command :
java -jar -Dbase.value=150 -Dapi.url=http://localhost:8080/storedata/ sensor-emulator-0.0.1-SNAPSHOT.jar

this will call the Rest API throgh the post Method.(create method in ProjectCOntroller.java)

APIs:

Create : this will collect data from the sensor-emulator and store in the MongoDb using MongoClient.

Two entities metrics and alerts are used as collections in the datastore created.
	metrics :  timerange and weight
	alerts: timerange and status

And by parsing the body of the POST method using org.json i store the metrics in the datastore.

For storing the alerts i used EasyRules ---  ruleAlert.java
where i defined a condition and action .
So if the condition comes true then it will store the alerts in the datastore.

read(): will return all the metrics store in the datastore.

readBYTimeRage: will return the metrics stored between two timeranges.

readAlert(): will return all the alerts store in the datastore.

readBYTimeRageAlert: will return the alerts stored between two timeranges.

All the dependencies are in the pom.xml

I have attached the API URL and data output in the dataOutput.txt




