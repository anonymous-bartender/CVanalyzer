# CVanalyzer
Demo Purpose

### Deployment instruction
run `mvn jetty:run -DskipTests`. 
_Server will be running at localhost:3000_

### Usage
Open any browser and hit `localhost:3000/cv-analyzer/`

### Modules used
>1. Jersey (JAX-RS)
>2. Jetty
>3. MaterializeCss HTML templates (http://materializecss.com/)
>4. Chart.js (http://www.chartjs.org/)
>5. Javascript with JQuery

### Work Flow
>1. Browser hit GET call -> Jetty server running at localhost return base html and subordinate static files.
>2. User upload file and hit submit. File get transferred over POST call.
>3. Jersey Servlet accept the file -> `Engine.java` process it and return a `JSON`.
>4. Jersey process the data and returns to client.
>5. Client side Javascript parse the JSON and updates the DOM -> user see the Chart.
