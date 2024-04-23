echo "Setting Minikube Docker Env"
eval $(minikube -p minikube docker-env)
minikube image ls --format table

echo "Building the Docker Images"
pushd ../jgaming-service/docker
./build-docker.sh
popd
