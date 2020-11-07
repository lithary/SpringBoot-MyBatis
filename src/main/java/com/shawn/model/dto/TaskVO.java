package com.shawn.model.dto;

import lombok.Data;

@Data
public class TaskVO<T> {
    private String beanName;
    private String method;
    private T in;
}
