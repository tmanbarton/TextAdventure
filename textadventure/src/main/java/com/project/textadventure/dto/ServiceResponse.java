package com.project.textadventure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the shape of the response from the service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse<T> {

    private String status;
    private T data;

}