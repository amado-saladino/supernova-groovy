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
This image can run in `Katacoda` playground. Json server can also run locally from `json-server` folder

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
