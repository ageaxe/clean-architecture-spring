package com.example.usercase.port;

public interface PublishEvent<T> {
    void publish(T application);
}
