# Development Process
The TMS Routing service consists of a frontend and a backend part. Both parts are considered in the common Maven build process. After you've
cloned the repository you call Maven to build the project and create the executable:

```
$ mvn package
```

# Release
Building the service and deploying it to the Sonatype staging repository works like:
 
```
$ mvn clean deploy -Prelease,gpg
```
