jGaming Kubernetes Setup

Setup the minikube docker environment
```bash
eval $(minikube -p minikube docker-env)
```

Build the local docker image in the same terminal

List the locally built images
```bash
minikube image ls --format table
```

# Minikube Config
minikube addons enable ingress
minikube addons enable metrics-server
