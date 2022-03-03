package org.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RootControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    
    @PostConstruct
    void init() {
        baseUrl = "http://localhost:" + port + "/entities";
        restTemplate.getRestTemplate().setErrorHandler(new ExtractingResponseErrorHandler());
    }
    

    @Test
    public void testGetList() throws Exception {
        String resp = restTemplate.getForObject(baseUrl, String.class);
        List<Entity> list = objectMapper.readValue(resp, new TypeReference<List<Entity>>() {});
        assertEquals(Arrays.asList(new Entity("apple"), new Entity("orange")), list);
    }
    
    @Test
    public void testGetOne() throws Exception {
        String name = "orange";
        Entity orange = restTemplate.getForObject(baseUrl + "/" + name, Entity.class);
        assertEquals(new Entity(name), orange);
    }

    @Test
    public void testGetNotFound() throws Exception {
        try {
            String neName = "non_existing";
            restTemplate.getForObject(baseUrl + "/" + neName, Entity.class);
            fail("Should not be found");
        }
        catch (RestClientResponseException e) {
            assertEquals(HttpStatus.NOT_FOUND.value(), e.getRawStatusCode());
        }
    }

    @Test
    public void testCreate() throws Exception {
        String name = "pineapple";
        Entity pineapple = this.restTemplate.postForObject(baseUrl, new Entity(name), Entity.class);
        
        assertEquals(new Entity(name), pineapple);

        String resp = this.restTemplate.getForObject(baseUrl, String.class);
        List<Entity> list = objectMapper.readValue(resp, new TypeReference<List<Entity>>() {});
        assertEquals(Arrays.asList(new Entity("apple"), new Entity("orange"), new Entity("pineapple")), list);
    }

}

