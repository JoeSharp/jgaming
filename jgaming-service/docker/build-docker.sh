pushd ..

./gradlew clean build
docker build --platform=linux/amd64 -t joesharpcs/jgaming-service .

popd