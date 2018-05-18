package com.neueda.shorty;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.neueda.shorty.util.Base62.encode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class ApplicationRestTestClient {

    public static final String RESOURCE_URI = "http://localhost:8080/shorten";
    public static final String GET_RESOURCE_URI = "http://localhost:8080/shorten/";

    static final String HOW_TO_FOLD_A_SHIRT_LONG_URL_ENCODED = "https%3A%2F%2Fwww.google.fr%2Fsearch%3Fq%3Dhow%2Bto%2Bfold%2Ba%2Bshirt%26oq%3Dhow%2Bto%2Bfold%2Ba%2Bshirt%26aqs%3Dchrome..69i57.3841j0j8%26sourceid%3Dchrome%26ie%3DUTF-8";
    static final String HOW_TO_FOLD_A_SHIRT_LONG_URL = "https://www.google.fr/search?q=how+to+fold+a+shirt&oq=how+to+fold+a+shirt&aqs=chrome..69i57.3841j0j8&sourceid=chrome&ie=UTF-8";


    static final int NUMBER_OF_ITERATIONS = 2;

    protected static final Logger log = LoggerFactory.getLogger(ApplicationRestTestClient.class);

    /* POST */
    private void postLongUrl() {
        final RestTemplate restTemplate = new RestTemplate();
        IntStream.range(1,NUMBER_OF_ITERATIONS).forEach( i -> {
            String url = HOW_TO_FOLD_A_SHIRT_LONG_URL;
            ResponseEntity<String> response
                    = restTemplate.postForEntity(RESOURCE_URI + "/post", url, String.class);
            Assert.assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
            log.info("new url added and the shortCode is: " + response.getBody() );
        });
    }

    private void requestWithShortUrl() {
        final RestTemplate restTemplate = new RestTemplate();
        IntStream.range(1,NUMBER_OF_ITERATIONS).forEach( i -> {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(GET_RESOURCE_URI + i, String.class);
            Assert.assertThat(response.getStatusCode(), is(HttpStatus.SEE_OTHER));

        });

    }

    public static void main(String args[]){
        ApplicationRestTestClient restTest = new ApplicationRestTestClient();
        restTest.postLongUrl();
        restTest.requestWithShortUrl();
    }
}