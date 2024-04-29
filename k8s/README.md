jGaming Kubernetes Setup

Setup the minikube docker environment
```bash
eval $(minikube -p minikube docker-env)
```

Once you close Minikube, run the following to reset to local docker
```bash
eval $(minikube docker-env --unset)
```

Build the local docker image in the same terminal

List the locally built images
```bash
minikube image ls --format table
```

# Minikube Config
minikube addons enable ingress
minikube addons enable metrics-server
