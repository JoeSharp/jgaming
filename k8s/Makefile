minikube:
	eval $(minikube -p minikube docker-env)
	minikube image ls --format table

build-service: minikube
	echo "Building the Service" && \
	pushd ../jgaming-service && \
	./gradlew clean build -x test && \
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-service . && \
	popd 

build-ui: minikube
	echo "Building the UI" && \
	pushd ../jgaming-ui && \
	docker build --platform=linux/amd64 -t joesharpcs/jgaming-ui . && \
	popd

deploy: build-service build-ui
	echo "Applying Components"
	kubectl apply -f gol-namespace.yaml
	kubens gol
	kubectl apply -f redis.yaml
	kubectl apply -f redis-config.yaml
	kubectl apply -f jgaming-service.yaml
	kubectl apply -f jgaming-ui.yaml

clean:
	echo "Cleaning Components"
	kubectl delete -f jgaming-ui.yaml
	kubectl delete -f jgaming-service.yaml
	kubectl delete -f redis-config.yaml
	kubectl delete -f redis.yaml
	kubectl delete -f gol-namespace.yaml
	kubens default
