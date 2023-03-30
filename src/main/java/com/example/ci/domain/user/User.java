package com.example.ci.domain.user;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String absoluteUrl;
    private String description;
    private String fullName;
    private String id;

    // TODO: Find a way to support properties, which is a list of different extensions of a base class
     private List<Property> properties;
}
