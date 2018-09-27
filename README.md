# Weather Playlist Suggestion

Service that suggests playlists according to weather information.
In order to do so, the platform integrates with OpenWeatherMap (https://openweathermap.org) and Spotify (https://www.spotify.com).

## Technologies

In this project, the followign technologies were used: Java, Spring/SpringBoot, Drools, Redis, ActiveMQ, PostreSQL (Postgis), Docker/Docker Compose.

TODO: The PostgreSQL extension (Postgis) was added as it provides spatial analysis, so, if there are any communication issues with Weather provider (OpenWeather), we could check the nearest reference/location according to the requested latitude / longitude.

## Running application under development environment

In order to make it easier to run the application under development environment, docker and docker-compose technologies were applied.

Before starting, please change the following sensitive information on `docker-compose.yml` file:

* `<YOUR_OPEN_WEATHER_KEY>`: This key can be retrieved on OpenWeatherMap (https://openweathermap.org) after registering;
* `<YOUR_SPOTIFY_CLIENT_ID>`: This is the client id data which can be obtained at Spotify for developers (https://developer.spotify.com)
* `<YOUR_SPOTIFY_CLIENT_SECRET>`: This is the client secret data which can be obtained at Spotify for developers (https://developer.spotify.com)

After this, you are ready to start! Just follow the steps to build and run the application under container environment (all the related command lines are supposed to be performed under project's root directory): 
 
* `./mvnw clean install`: This will clean and build the whole application using wrapper;
* `docker-compose build`: This command will build all application container images;
* `docker-compose up`: This command will start all resources needed for Weather Playlist Suggestion application (playlistsuggestion-ws, playlistsuggestion-worker, activemq, redis and postgis)

## Testing application under development environment

In order to make it easier to test the application under development environment, we have added Swagger UI (https://swagger.io), which allows to visualize and interact with Weather Playlist Suggestion API’s without any other tool.

After running the application, just access `http://localhost:8080/swagger-ui.html` on your favorite browser. This will list all Weather Playlist Suggestion endpoints.

Playlist Controller:
* [GET] /playlist/1.0/genre/{genre}: Searches tracks under Spotify plataform according to given genre.

Weather Controller
* [GET] /weather/1.0/city/{city}/country-code/{countryCode}: Searches weather data under OpenWeatherMap plataform according to given city and country code;
* [GET] /weather/1.0/lat/{lat}/long/{long}: Searches weather data under OpenWeatherMap plataform according to given latitude and longitude coordinates.

Playlist Suggestion Controller
* [GET] /playlist-suggestion/1.0/city/{city}/country-code/{countryCode}: Retrieve weather playlist suggestion regarding city input (*synchronously*);
* [GET] /playlist-suggestion/1.0/lat/{lat}/long/{long}: Retrieve weather playlist suggestion regarding latitude/longitude input (*synchronously*);
* [POST] /playlist-suggestion/1.0/async/city: POST weather playlist suggestion request regarding city and country code. A callback url is also needed (*asynchronous*);
* [POST] /playlist-suggestion/1.0/async/lat-long: POST weather playlist suggestion request regarding latitude/longitude. A callback url is also needed (*asynchronous*).

Playlist suggestions can be found on Drools rules file (genres_rules), which can always be changed according to any weather evaluation.
The preconfigured rules are:
* If temperature is above 30 degrees (Celcius), suggests Party tracks;
* If temperature is between 15 and 30 degrees (Celcius), suggests Pop tracks;
* If temperature is between 10 and 14 degrees (Celcius), suggests Rock tracks;
* If temperature is under 10 degrees (Celcius), suggests Classical tracks;

Naturally, any other temperature measurements could be used to suggest playlists.