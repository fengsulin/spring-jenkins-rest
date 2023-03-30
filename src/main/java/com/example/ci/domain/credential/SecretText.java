package com.example.ci.domain.credential;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: FSL
 * @date: 2023/3/30
 * @description: TODO
 */
@Data
public class SecretText extends BaseCredential{
    @NotBlank
    private String secret;
}
