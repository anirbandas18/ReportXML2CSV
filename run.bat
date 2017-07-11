@echo off
set /p mode= Provide run mode 'profile' or 'properties' : 
set /p input= Input directory path : 
set /p output= Output directory path : 
java -Xmx512m -jar ReportGenerator.jar %mode% %input% %output%
pause