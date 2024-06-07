# Simplificando o desenvolvimento de Rest APl com #Kotlin e #Ktor
Este é um repositório Usado na sessão sobre Ktor e Kotlin na comunidade Kotlin Dev Brasil 🇧🇷 


###
```http request
GET http://0.0.0.0:8080/
Accept: application/json`
```

###
```http request
GET http://0.0.0.0:8080/movies
Accept: application/json
```



###
```http request
POST http://0.0.0.0:8080/movies
Content-Type: application/json

{
"id": 1,
"title": "Iron Man",
"year": 2008
}
```

###
```http request
GET http://0.0.0.0:8080/movies/1
Accept: application/json
```



###
```http request
DELETE http://0.0.0.0:8080/movies/1
Accept: application/json
```