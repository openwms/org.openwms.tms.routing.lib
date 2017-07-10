# Development Process

The TMS Routing component consists of a frontend and a backend software component. Both
parts are considered in the common Maven build process. After you've cloned the repository
you call Maven to build the project and create the deliverables:

```
$ mvn package
```

# Release

```
$ mvn clean deploy -Prelease,gpg
```
