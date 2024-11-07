package exercises

import static com.mongodb.client.model.Accumulators.*
import static com.mongodb.client.model.Aggregates.*
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Sorts.*
import static com.mongodb.client.model.Projections.*

import org.bson.Document

import com.mongodb.client.MongoClients

import com.mongodb.client.model.Filters

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.BucketAutoOptions
import com.mongodb.client.model.Facet



// Load credentials from src/main/resources/mongodb.properties
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
    properties.load(it)
}

def printResult(exercise, col, pipeline) {
	def result = col.aggregate(pipeline).into([])
	println("----------------------")
	println("EXERCISE ${exercise}")
	result.each { println it }
}

// Create connection
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@cluster0.${properties.SERVER}.mongodb.net/${properties.DB}?retryWrites=true&w=majority")
def db = mongoClient.getDatabase(properties.DB)
def col = db.getCollection("movies")

// Solution 1: Aggregation pipeline to filter movies released after 2000, group by genre, and sort by average IMDb rating
def pipeline_1 = [
    match(gte("year", 2000)),
    project(new Document("genres", 1) // Projecting genres and imdb.rating fields
        .append("imdbRating", "\$imdb.rating")
    ),
    group('\$genres', avg('avgRating', '\$imdbRating')), // Group by genres and calculate avgRating
    sort(descending('avgRating')) // Sort by avgRating
]
printResult(1, col, pipeline_1)


// Solution 2: Aggregation pipeline with projection and limit stages
//def pipeline_2 = [
//    match(gte("year", 2000)),
//    group('\$genres', avg('avgRating', '\$imdb.rating')), // Grouping by genres
//    project(fields(include("genres", "avgRating"))), // Project only genres and avgRating
//    sort(descending('avgRating')), // Sort by avgRating
//    limit(3) // Limit to top 3
//]
//printResult(2, col, pipeline_2)

// Solution 3: Filter movies after 2010 with IMDb >= 8, group by director, and calculate total movies and avg rating
//def pipeline_3 = [
//    match(and(gte("year", 2010), gte("imdb.rating", 8))), // Filter by year and IMDb rating
//    group('\$directors', sum('totalMovies', 1), avg('avgRating', '\$imdb.rating')), // Group by directors
//    sort(descending('avgRating')) // Sort by avgRating
//]
//printResult(3, col, pipeline_3)


// Solution 4: Filter movies with "Action" genre, unwind genres, and group by genre
//def pipeline_4 = [
//    match(Filters.in("genres", ["Action"])), // Filter by "Action" genre
//    unwind('\$genres'), // Unwind genres array
//    group('\$genres', sum('totalMovies', 1), avg('avgRating', '\$imdb.rating')), // Group by genres
//    sort(descending('avgRating')) // Sort by avgRating
//]
//printResult(4, col, pipeline_4)

// Solution 5: Using $project for field selection and transformation (word count of movie titles)
//def pipeline_5 = [
//    project(fields(
//        include("title", "year"), // Include title and year
//        	new Document("wordCount", 
//				new Document("\$size", 
//					new Document("\$split", ["\$title", " "]))) // Calculate the word count of the title by splitting and counting words
//    ))
//]
//printResult(5, col, pipeline_5)


// Solution 6: Using $merge for writing results to another collection (grouping by decade)
//def pipeline_6 = [
//    match(lt('year', 2000)), // Filter movies released before 2000
//    group([ 'decade': [ '\$subtract': [ '\$year', [ '\$mod': [ '\$year', 10 ] ] ] ] ], // Group by decade
//        avg('avgRating', '\$imdb.rating')
//    ),
//    merge('decade_ratings') // Merge results into another collection
//]
//printResult(6, col, pipeline_6)
