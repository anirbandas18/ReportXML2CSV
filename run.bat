@echo off
set /p mode= Provide run mode 'profile' or 'properties' : 
set /p input= Input directory path : 
set /p output= Output directory path : 
java -Xmx512m -jar target/ReportXML2CSV-0.0.1-SNAPSHOT.jar %mode% %input% %output%
pause