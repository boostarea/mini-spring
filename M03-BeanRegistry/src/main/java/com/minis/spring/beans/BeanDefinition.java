package com.minis.spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeanDefinition {
    private String id;
    private String className;
}
