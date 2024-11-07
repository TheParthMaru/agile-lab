package exercises

import org.bson.Document

import com.mongodb.client.MongoClients

import static com.mongodb.client.model.Filters.*;

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// load credentials from src/main/resources/mongodb.properties
// this file should contain 
//		USN=yourUsername
//		PWD=yourPassword
//		DB=yourDatabaseName 
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// parse JSON file


// create connection and upload contents


