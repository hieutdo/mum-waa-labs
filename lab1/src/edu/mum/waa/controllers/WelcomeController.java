package edu.mum.waa.controllers;

public class WelcomeController extends AbstractController {
    @Override
    public String getTitle() {
        return "WelcomeController";
    }

    @Override
    public String getBody() {
        return "<h1>Welcome to BareBonesHTTPD</h1>";
    }
}
