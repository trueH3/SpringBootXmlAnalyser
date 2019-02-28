# HTTP API server which can analyse the content of big xml files.

This particular http server is designed to analyze **Stack Exchange Data Dump**
from site https://archive.org/details/stackexchange. Each of the packed file from this site consists Posts.xml which this server can analyze. When launching jar you can pass as an arugment port number, if the argument is ommited the default value will be used, which is 8080.
The main requirements for this exercise are: 

* Create an API with Spring Boot (Java) and Maven
* A POST request should be possible with a url to an XML file (file can be > 1GB)
* The response of the post request should hold an overview of the abalysation of the XML (see example below)
* The code should be unit and component tested
* The code should pass the maven build and be runnable via cli with max of 512MB of memory

**Example request**<br/> 
`curl -i -X POST -H "Content-Type:application/json"  -d "url1=(path to file on your computer or url>)/Posts.xml" http://127.0.0.1:8080/analyze`

**Example response**<br/>
```
{  
   "analyseDate":"2016-04-25T14:52:30+00:00",
   "details" {
       "firstPost":"2016-01-12T18:45:19.963+00:00",
       "lastPost":"2016-03-04T13:30:22.410+00:00",
       "totalPosts":80,
       "totalAcceptedPosts":7,
       "avgScore":13
   }
}
```
 
