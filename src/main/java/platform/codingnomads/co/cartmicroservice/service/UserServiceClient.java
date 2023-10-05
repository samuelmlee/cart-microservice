package platform.codingnomads.co.cartmicroservice.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import platform.codingnomads.co.cartmicroservice.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    @LoadBalanced
    private final RestTemplate restTemplate;

    private final EurekaClient discoveryClient;

    private static final String USER_MICROSERVICE = "USER-MICROSERVICE";

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceClient.class);


    public Optional<User> fetchUserById(Long userId) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId.toString());

        try {
            InstanceInfo instance = discoveryClient.getNextServerFromEureka(USER_MICROSERVICE, false);
            ResponseEntity<User> response = restTemplate.getForEntity(
                    "http://" + USER_MICROSERVICE + "/user/{userId}", User.class, uriVariables);
            return Optional.ofNullable(response.getBody());
        } catch (ResourceAccessException e) {
            LOGGER.error("Network error while fetching user with ID {}: {}", userId, e.getMessage());
        } catch (RestClientException e) {
            LOGGER.error("Error while fetching user with ID {}: {}", userId, e.getMessage());
        }

        return Optional.empty();
    }


}
