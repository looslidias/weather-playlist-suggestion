package com.looslidias.playlistsuggestion.ws.controller;

import com.looslidias.playlistsuggestion.core.service.weather.WeatherService;
import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;
import com.looslidias.playlistsuggestion.ws.controller.mapping.WeatherURLMapping;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = WeatherURLMapping.CITY_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get weather information regarding city input", nickname = "Get weather information regarding city input")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity getCityWeather(@PathVariable("city") String city, @PathVariable("countryCode") String countryCode) {
        try {
            WeatherDTO response = weatherService.searchWeather(city, countryCode);
            log.info("Successfully processed City Weather request. City: {}, CountryCode: {}", city, countryCode);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for City Weather. City: {}, CountryCode: {}", city, countryCode, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for City Weather. City: {}, CountryCode: {}", city, countryCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @RequestMapping(value = WeatherURLMapping.LAT_LONG_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get weather information regarding latitude/longitude input", nickname = "Get weather information regarding latitude/longitude input")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity getLatLongWeather(@PathVariable("lat") Double lat, @PathVariable("long") Double lon) {
        try {
            WeatherDTO response = weatherService.searchWeather(lat, lon);
            log.info("Successfully processed LatLong Weather request. Lat: {}, Long: {}", lat, lon);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for LatLong Weather. Lat: {}, Long: {}", lat, lon, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for LatLong Weather. Lat: {}, Long: {}", lat, lon, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}
