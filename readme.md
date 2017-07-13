## Java Installation [Skip this if JDK is already installed]
1. Download and install latest [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) according to 32 Bit or 64 Bit architecture which confronts to 32 Bit -> x86 and 64 Bit -> x64
3. Copy the JDK installation directory path which will be along the lines of `[Drive letter]:\Program Files (x86)\Java\jdk1.[version number].[sub version number]_[build number]` for 32 Bit machines and `[Drive letter]:\Program Files\Java\jdk1.[version number].[sub version number]_[build number]` for 64 Bit machines
2. Right click on ***My Computer***. Got to ***Properties***.
3. Click on ***Advanced System Settings*** option.
4. Click on ***Environment Variables*** button.
5. Add a new ***System variable*** entry with key `JAVA_HOME` and value as the JDK installation directory path from step 2
6. Click on OK
7. Open command prompt.
8. Type `java -version` at the command prompt and hit Enter. It Should show the Java version and build number. This is to verify Java installation on windows. 
	
1. Download maven[http://www-us.apache.org/dist/maven/maven-3/3.5.0/binaries/apache-maven-3.5.0-bin.zip]
2. 