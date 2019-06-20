java -jar jacoco/jacococli.jar dump --port=36322 --address=localhost --destfile=cov/result.exec

java -jar jacoco/jacococli.jar report cov/result.exec --classfiles=./build/classes --sourcefiles=./src/main/java --html report