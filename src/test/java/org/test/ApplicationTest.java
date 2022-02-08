package org.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RootController controller;

    private String baseUrl;
    
    @PostConstruct
    void init() {
        baseUrl = "http://localhost:" + port + "/entities";
    }
    

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void testGet() throws Exception {
        String resp = this.restTemplate.getForObject(baseUrl, String.class);
        List<Entity> list = objectMapper.readValue(resp, new TypeReference<List<Entity>>() {});
        assertEquals(Arrays.asList(new Entity("apple"), new Entity("orange")), list);
        
        String name = "orange";
        Entity orange = this.restTemplate.getForObject(baseUrl + "/" + name, Entity.class);
        assertEquals(new Entity(name), orange);
    }

    @Test
    public void testPost() throws Exception {
        String name = "pineapple";
        Entity orange = this.restTemplate.postForObject(baseUrl, new Entity(name), Entity.class);
        assertEquals(new Entity(name), orange);

        String resp = this.restTemplate.getForObject(baseUrl, String.class);
        List<Entity> list = objectMapper.readValue(resp, new TypeReference<List<Entity>>() {});
        assertEquals(Arrays.asList(new Entity("apple"), new Entity("orange"), new Entity("pineapple")), list);
    }

}

