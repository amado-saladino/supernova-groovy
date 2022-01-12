# Overview

Groovy Flavor of [Supernova](https://github.com/amado-saladino/supernova) framework.

## Components

- RestUtils
- TestCase (UI)
- JSON server
- Scripts
- Data files
- YAML configuration

## JSON server

Json server could run in a container, container components reside in `json-server` folder.

```shell
docker run -d -p 81:80 -v $PWD:/data -e "DB=users.json" json-server:v1
# or
docker run -d -p 81:80 -v $PWD:/data -e "DB=users.json" ghcr.io/amado-saladino/json-server:v1
```

### port

container port is defined in `json-server.json` config file

## Scripts

Scripts can be injected in a web page, there is `jquery.js` to inject jQuery in a web page

## Data placeholders

Placeholders could be easily replaced by values
*e.g. user-placeholders.json* has some placeholders, these placeholders
could be replaced using `RestUtils.resolveVarsInString` method.

## Groovy

Enjoy the high flexibility of `Dynamic Programming` of Groovy lang.

## Run

`gradle clean test`

## Run Custom Tests

```shell
gradle clean test --tests TestTwo  # class
gradle clean test --tests TestTwo.*json*  # pattern of method
gradle clean test --tests TestTwo."use excel sheet as data source"  # a specific method
```

## Run in Docker

### Build the image

```sh
docker build -t gradle-test .
```

### Run test

```sh
docker run -v `pwd`:/app -v gradle-repo:/root/.gradle/caches/modules-2/files-2.1 -u root amadosaladino/selenium-gradle
```

### Run a specific test method

```sh
docker run -v gradle-repo:/root/.gradle/caches/modules-2/files-2.1 -v `pwd`:/app -u root amadosaladino/selenium-gradle clean test --tests TestOne."show page elements"
```

## View Test Report

```shell
docker run --rm --name=report -d -v $PWD/build/reports/tests/test:/usr/share/nginx/html -p 80:80 nginx:1.14
```

## Screenshot Viewer

Screenshots taken during test run could be displayed this way:

```shell
docker run -d -v $PWD/screenshots:/Pictures:ro -p 81:80 --name gallery ghcr.io/linuxserver/photoshow
```