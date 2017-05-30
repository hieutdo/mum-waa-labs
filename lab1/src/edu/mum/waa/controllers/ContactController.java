package edu.mum.waa.controllers;

public class ContactController extends AbstractController {
    @Override
    public String getTitle() {
        return "ContactController";
    }

    @Override
    public String getBody() {
        return "<h1>Contact Page</h1>";
    }
}
