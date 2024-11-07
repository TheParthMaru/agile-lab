package exercises

import org.bson.Document

import com.mongodb.client.MongoClients

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;


def path = 'src/main/resources/top-rated-movies.json'

def solution = { filePath ->
	// load credentials from src/main/resources/mongodb.properties
	// this file should contain
	//		USN=yourUsername
	//		PWD=yourPassword
	// 		SERVER=serverToken
	//		DATABASE=yourDatabaseName
	def properties = new Properties()
	def propertiesFile = new File('src/main/resources/mongodb.properties')
	propertiesFile.withInputStream {
		properties.load(it)
	}
	
	// create connection and upload contents
	def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@cluster0.${properties.SERVER}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");
	def db = mongoClient.getDatabase(properties.DB);
	def col = db.getCollection("movies-collection")
	
	// reset collection
	col.deleteMany(new Document())
	
	// get info from file
	def jsonSlurper = new JsonSlurper()
	def file = new File(filePath)
	def movies = jsonSlurper.parseText(file.text) as List
	
	// add new movie
	def newMovie = [
			title: "David Attenborough: A Life on Our Planet",
			year: 2020,
			genres: [ "Dobumentary", "Biography" ],
			imdbRating: 9,
			actors: [ "David Attenborough", "Max Hughes"]
		]
	movies.add(newMovie)
	
	// upload data to MongoDB
	for (obj in movies) {
		def doc = Document.parse(JsonOutput.toJson(obj))
		col.insertOne(doc)
	}
	
	// filter those movies with a rating that is greater than or equal to 8
	def resultList = col.find(gte('imdbRating', 8.0)).sort(orderBy(ascending("year")))
	resultList.toList()
		.findAll{it.genres.contains("Biography")}
		.sort{ it.year + it.title }
		.collect{"""
${it.year}: ${it.title} (${it.genres})"""}
}



// desk check your solution twice: both attempts should give the same result
println('first attempt:')
println(solution(path).join(""))
