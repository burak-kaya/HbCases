Feature: Demo

  Background:
    * url 'https://62332be78b6a290be4e18e1f.mockapi.io/'
    * header content-type = 'application/json'
    * def jsonPath = '../Json/'

  Scenario: Get fruit with name
    * param name = 'apple'
    * path '/allGrocery'
    * method GET
    * status 200
    * print response
    * assert response[0].id != ''
    * print response[0].name

  Scenario: Nullcheck for all params
    * path '/allGrocery'
    * method GET
    * status 200
    * print response

    * def idList = $[*].id
    * def nameList = $[*].name
    * def priceList = $[*].price
    * def stockList = $[*].stock

    * def isNotNull = function(arg){ return arg != null }
    * match idList == '#[]? isNotNull(_)'
    * match nameList == '#[]? isNotNull(_)'
    * match priceList == '#[]? isNotNull(_)'
    * match stockList == '#[]? isNotNull(_)'

  Scenario: Add new fruit

    * def jsonBody = read(jsonPath + 'addRequestModel.json')
    * jsonBody.name = 'melon'
    * jsonBody.price = 80
    * jsonBody.stock = 400
    * print jsonBody

    * path '/add'
    * request jsonBody
    * method POST

    * match $.id != null
    # NOT: bu api bir kayıt işlemi yapıyormuş ve response mesajı geliyormuş gibi düşünüp,
    # mesaj kontrolu gerçekleştirdim
    * match $.message == 'success'
    * print response

