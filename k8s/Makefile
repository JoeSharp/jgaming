build: build-api build-ui

build-api: 
	echo "Building the Service" 
	../api/gradlew clean build -x test -p ../api/
	docker build --platform=linux/amd64 -t joesharp/jgaming-api ../api/

build-ui: 
	echo "Building the UI" 
	npm run build --prefix ../ui/
	docker build --platform=linux/amd64 -t joesharp/jgaming-ui ../ui/
