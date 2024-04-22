pushd ..

./gradlew clean build -x test
docker build --platform=linux/amd64 -t joesharpcs/jgaming-service .

popd