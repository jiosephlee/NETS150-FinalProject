Team Members: Scott Klein, Joseph Lee, Daniel Duan

Project Name: Night in New York 

Project Description: Our program will generate an itinerary for users looking to explore New York
City based on their food preferences and activity preferences. After selecting what type of cuisine
they enjoy (Thai, Mexican, Italian, Korean, Chinese, and American) and what activities they like
(Arcade, Karaoke, Taking Photos, Bowling, our program scrapes Yelp to search for the Web's top two
recommended venues for each cuisine and each activity. We then use Dijkstra's Algorithm to return
the shortest possible route going through these venues, alternating between meals and activities. We
present these results to our user, crafting their perfect itinerary for a night in New York!

Categories: 
	1. Graph and graph algorithms: We implemented Dijkstra's algorithm in order to return an
	itinerary of the shortest path. After the user determines their preferred activities and
	cuisines, the distance between each activity is calculated using the haversine formula for
	coordinates. These distances are then used as the edge weights in a graph which Dijkstra's is
	used on. Dijkstra assumes that all distances begin infinitely far away, takes the source (which
	we arbitrarily assign as the first location requested by the user), and then begins adding to a
	list in order of minimum distance. Once a location has been added to the output, it is no longer
	considered. In order to alternate between cuisine and activities, edges are only added when the
	type of the event (meal or activity) are different.
	We built off of an external library for the graph and priority queue structures, but made
	Dijkstra's from scratch based on the logic discussed in lecture 5. Specifically, we utilize two
	priority queues in our Dijkstra implementation to account for alternating inputs, which is a
	completely different implementation from those used in other classes or for more general
	Dijkstra implementations.

	Assumptions: We assume that distance will always be positive for Dijkstra's to work, and assume
	that travel distance is correlated to absolute distance.

	2. Document Search (aka Information Retrieval): We used JSoup to scrape Yelp in order to
	retrieve the addresses, latitude/longitude coordinates, and names of the top recommended
	venues on Yelp. Upon scraping, we create an ArrayList of Location objects with these properties
	to be used application of Dijkstra's Algorithm.
	
	Assumptions: We assume that Yelp won't change the layout of their HTML tags in future website
	updates. Specifically, we assume that the second address tag will always contain the street
	address of the top Yelp recommendation; the third address tag will always contain the street
	address of the second best Yelp recommendation; the sibling element of the address tag will
	always contain the area/county name; the name of the venue will always be in the previous
	sibling element of the grand parent of the address tag.

	Note: Frequent requests may result in an error; this isn't a bug in the code but Yelp trying to
	prevent robot users.

Work Breakdown: 
- Scott: Graph Algorithms and Implementing Dijkstra's
- Joseph: GUI and getting the coordinates, API Integration
- Daniel: Jsoup to scrape Yelp for relevant information