#
# BUILD
#

# build project
Push-Location ../src
mvn install
If ( -not $? ) { Pop-Location; Exit -1; }
Pop-Location

# deploy jar for dockerfile
Copy-Item ..\src\target\*.jar airport.jar

#
# DOCKERFILE
#

# remove old docker image
docker rm airport -f

# build & run docker image
$current_dir = (Get-Item -Path ".\").FullName
docker build --tag=airport $current_dir
docker run --name airport -it -p 8080:8080 -p 8081:8081 airport
