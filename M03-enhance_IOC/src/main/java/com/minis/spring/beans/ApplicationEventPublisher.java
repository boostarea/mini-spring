package com.minis.spring.beans;


public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
