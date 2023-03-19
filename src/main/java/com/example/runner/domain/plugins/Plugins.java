package com.example.runner.domain.plugins;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Plugins {
    private List<Plugin> plugins;
}
