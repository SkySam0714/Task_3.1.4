package ru.itmentor.task.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.itmentor.task.model.User;

import java.util.List;

@RestController
public class MainController {

    private final RestTemplate restTemplate;

    public MainController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String main(){
        String result = "";
        HttpHeaders headers = new HttpHeaders();
        User user = new User( (long)3,"James","Brown", (byte)18);

        ResponseEntity<User[]> getResponse = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", User[].class);

        String cookie = getResponse.getHeaders().get("Set-Cookie").get(0);
        headers.set("Cookie", cookie);

        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> postResponse = restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.POST, httpEntity, String.class);
        user.setName("Thomas");
        user.setLastName("Shelby");
        ResponseEntity<String> putResponse = restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.PUT, httpEntity, String.class);
        ResponseEntity<String> deleteResponse  = restTemplate.exchange("http://94.198.50.185:7081/api/users/{id}", HttpMethod.DELETE, httpEntity, String.class, user.getId());

        result = postResponse.getBody() + putResponse.getBody() + deleteResponse.getBody();

        return result;
    }
}
