echo "Setting Minikube Docker Env"
eval $(minikube -p minikube docker-env)
minikube image ls --format table

echo "Building the Docker Images"

echo "Building the Service"
pushd ../jgaming-service
./gradlew clean build -x test
docker build --platform=linux/amd64 -t joesharpcs/jgaming-service .
popd

echo "Building the UI"
pushd ../jgaming-ui
docker build --platform=linux/amd64 -t joesharpcs/jgaming-ui .
popd

