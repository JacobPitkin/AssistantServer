package com.jacobpitkin.assistant.discord;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/discord")
public class DiscordController {
    private final DiscordService discordService;

    public DiscordController(DiscordService discordService) {
        this.discordService = discordService;
    }

    @GetMapping
    public String getDiscordMessage() {
        return discordService.getMessage();
    }
    
    // TODO: Make sure this follows the correct standards here https://discord.com/developers/docs/interactions/overview#setting-up-an-endpoint-acknowledging-ping-requests
    @PostMapping("/ping")
    public ResponseEntity<String> ping() {
        // TODO: Call validation function
        return new ResponseEntity<>("{\"type\":1}", HttpStatus.OK);
    }

    // TODO: Implement security header validation https://discord.com/developers/docs/interactions/overview#setting-up-an-endpoint-validating-security-request-headers
    private boolean validate(String signature, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();

        byte[] sigBytes = signature.getBytes(StandardCharsets.UTF_8);
        byte[] timeBytes = timestamp.getBytes(StandardCharsets.UTF_8);

        Signature sig = Signature.getInstance("Ed25519");
        sig.initSign(kp.getPrivate());
        sig.update(sigBytes);
        byte[] sBytes = sig.sign();

        Signature time = Signature.getInstance("Ed25519");
        time.initSign(kp.getPrivate());
        time.update(timeBytes);
        byte[] tBytes = time.sign();

        return sig.verify(sBytes) && time.verify(tBytes);
    }
}
