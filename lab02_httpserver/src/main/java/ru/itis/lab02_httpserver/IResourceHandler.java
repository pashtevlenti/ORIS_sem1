package ru.itis.lab02_httpserver;

import java.util.Map;

public interface IResourceHandler {
    ResponceContent handle(Map<String,String> params);
}
