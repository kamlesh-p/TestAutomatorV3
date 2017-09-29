# TestAutomatorV3
test automator version3:



Automator is the tool designed to generate test artifacts for testing web services.
 
It takes input from an input Excel file containing node names
and node details like length, is Enum, is Mandatory Filed etc.
 
It performs the below tasks:
1. Creates testCases depending upon the node details from input Excel
2. creates a validation groovy script which is used in data driven testing of web services.
3. creates Test data file(DataSource file and DataSink file), used as input and output file in data driven test
4. creates a property file used in soapui map input excel properties.
