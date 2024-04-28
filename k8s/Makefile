build-service: 
	echo "Building the Service" 
	../jgaming-service/gradlew clean build -x test -p ../jgaming-service/
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-service ../jgaming-service/

build-ui: 
	echo "Building the UI" 
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-ui ../jgaming-ui/

build: build-service build-ui
