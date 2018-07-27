# TestAutomatorV3
test automator V3:



Automator is the tool designed to generate test artifacts for testing web services.
 
It takes input from an input Excel file containing node names
and node details like length, is Enum, is Mandatory Filed etc.
 
It performs the below tasks:
1. Creates testCases depending upon the node details from input Excel
2. Creates a validation Groovy script which is used in data driven testing of web services.
3. Creates Test data file(DataSource file and DataSink file), used as input and output file in data driven test
4. Creates a property file used in SoapUI map input excel properties.



To create executable jar file:
- run `mvn assembly:assembly` in test-amr-client
