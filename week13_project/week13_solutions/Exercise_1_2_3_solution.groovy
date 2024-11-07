package solutions

import org.bson.Document

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients

import groovy.json.JsonOutput
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Updates.*


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

// MAKING THE CONNECTION
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@cluster0.${properties.SERVER}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");

// GET DATABASE
def db = mongoClient.getDatabase(properties.DB);

// EXERCISE 1:
// Create a collection 'col' and create the following document
// This is an example of a Facebook JSON file which you might see 
// when getting data from the Facebook API. It might also be used to 
// contain profile information which can be easily shared across 
// your system components using the simple JSON format.
// source: https://www.sitepoint.com/facebook-json-example/
//	 [
//	      {
//	         "id": "X999_Y999",
//	         "from": {
//	            "name": "Tom Brady", "id": "X12"
//	         },
//	         "message": "Looking forward to 2010!",
//	         "actions": [
//	            {
//	               "name": "Comment",
//	               "link": "http://www.facebook.com/X999/posts/Y999"
//	            },
//	            {
//	               "name": "Like",
//	               "link": "http://www.facebook.com/X999/posts/Y999"
//	            }
//	         ],
//	         "type": "status",
//	         "created_time": "2010-08-02T21:27:44+0000",
//	         "updated_time": "2010-08-02T21:27:44+0000"
//	      },
//	      {
//	         "id": "X998_Y998",
//	         "from": {
//	            "name": "Peyton Manning", "id": "X18"
//	         },
//	         "message": "Where's my contract?",
//	         "actions": [
//	            {
//	               "name": "Comment",
//	               "link": "http://www.facebook.com/X998/posts/Y998"
//	            },
//	            {
//	               "name": "Like",
//	               "link": "http://www.facebook.com/X998/posts/Y998"
//	            }
//	         ],
//	         "type": "status",
//	         "created_time": "2010-08-02T21:27:44+0000",
//	         "updated_time": "2010-08-02T21:27:44+0000"
//	      }
//	   ]


def docList = [[
	id: 'X999_Y999',
	from: [id: 'X12', name: 'Tom Brady'],
	message: "Looking forward to 2010!",
	"actions": [[
        name: 'Comment',
		link: 'http://www.facebook.com/X999/posts/Y999'
     ],[
        name: 'Like',
        link: 'http://www.facebook.com/X999/posts/Y999'
     ]],
     type: 'status',
     created_time: "2010-08-02T21:27:44+0000",
     updated_time: "2010-08-02T21:27:44+0000"
],
[
	id: 'X998_Y998',
	from: [id: 'X18', name: 'Peyton Manning'],
	message: "Where\'s my contract?",
	"actions": [[
        name: 'Comment',
		link: 'http://www.facebook.com/X998/posts/Y998'
     ],[
        name: 'Like',
        link: 'http://www.facebook.com/X998/posts/Y998'
     ]],
     type: 'status',
     created_time: "2010-08-02T21:27:44+0000",
     updated_time: "2010-08-02T21:27:44+0000"
]]

def col = db.getCollection('mongodb-facebook')
col.drop()

for (d in docList) {
	def doc = Document.parse(JsonOutput.toJson(d))
	col.insertOne(doc)
}


assert col.countDocuments() == 2

// EXERCISE 2
// Update message field of document with id "X999_Y999" to "Remembering what happened in 2010"
col.updateOne(eq('id', 'X999_Y999'), set('message', 'Remembering what happened in 2010'))

// EXERCISE 3
// Delete the document with id: "id": "X998_Y998",
col.deleteOne(eq('id', 'X998_Y998'))
assert col.countDocuments() == 1



