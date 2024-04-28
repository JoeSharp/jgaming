build: build-api build-ui

build-api: 
	echo "Building the Service" 
	../api/gradlew clean build -x test -p ../api/
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-api ../api/

build-ui: 
	echo "Building the UI" 
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-ui ../ui/
