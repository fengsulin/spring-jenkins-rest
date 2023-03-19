package com.example.runner.domain.crumb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Crumb {
    @JsonAlias("Jenkins-Crumb")
    private String crumb;
}
