package com.atm.wallet.controller;

import com.atm.wallet.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Response<String> home() {
        return new Response<>(true, "Welcome to the wallet app, visit docs here : https://wallet-g3d9.onrender.com/swagger-ui/index.html", null);
    }
}