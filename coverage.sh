java -jar jacoco/jacococli.jar dump --port=36320 --address=localhost --destfile=result.exec

java -jar jacoco/jacococli.jar report result.exec --classfiles=./build/classes --sourcefiles=./src/main/java --html report