package com.jacobpitkin.assistant.discord;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/discord")
public class DiscordController {
    
    // TODO: Make sure this follows the correct standards here https://discord.com/developers/docs/interactions/overview#setting-up-an-endpoint-acknowledging-ping-requests
    @PostMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("{\"type\":1}", HttpStatus.OK);
    }

    // TODO: Implement security header validation https://discord.com/developers/docs/interactions/overview#setting-up-an-endpoint-validating-security-request-headers
}
