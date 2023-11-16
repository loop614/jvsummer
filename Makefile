.PHONY: run

run:
	mvn exec:java

pack_run:
	mvn package
	mvn exec:java

test:
	mvn test

run_build:
	java -jar target/jvsummer-0.1.0.jar de

install:
	mvn install

package:
	mvn package

compile:
	mvn compile
