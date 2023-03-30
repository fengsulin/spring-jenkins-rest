package com.example.ci.controller;

import com.example.ci.api.CredentialsApi;
import com.example.ci.common.RequestUtils;
import com.example.ci.domain.credential.Credentials;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: FSL
 * @date: 2023/3/29
 * @description: 凭证管理接口
 */
@Tag(name = "凭证管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/credential")
@Slf4j
public class CredentialController {
    private final CredentialsApi credentialsApi;

    @GetMapping("/list")
    @Operation(summary = "获取所有凭证")
    public Credentials listCredentials(){
        return credentialsApi.credentialList();
    }

    @PostMapping("/{id}")
    @Operation(summary = "删除凭证")
    @Parameter(name = "id",required = true,description = "凭证id")
    public boolean deleteCredential(@PathVariable String id){
        ResponseEntity<String> entity = credentialsApi.deleteCredential(id);
        if (entity.getStatusCode().is3xxRedirection() || entity.getStatusCode().is2xxSuccessful()){
            log.info("[{}]:凭证删除成功",id);
            return true;
        }
        throw new RuntimeException("删除凭证异常！");
    }

    @PostMapping(value = "/create")
    @Operation(summary = "新建凭证")
    @Parameters({
            @Parameter(name = "code",required = true,description = "凭证类型码,1-用户名密码，2-secret-text，3-apiToken"),
    })
    public boolean createCredential(int code,HttpServletRequest request){
        // opType为操作类型，1为新建凭证，其他为更新
        ResponseEntity<String> entity = credentialsApi.createCredential(RequestUtils.transCredentialJson(request,code,1));
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info("凭证新建成功");
            return true;
        }
        throw new RuntimeException("凭证新建异常");
    }

    @PostMapping(value = "/{id}/update")
    @Operation(summary = "更新凭证")
    @Parameters({
            @Parameter(name = "code",required = true,description = "凭证类型码,1-用户名密码，2-secret-text，3-apiToken"),
    })
    public boolean updateCredential(int code, @PathVariable String id, HttpServletRequest request){
        ResponseEntity<String> entity = credentialsApi.updateCredential(RequestUtils.transCredentialJson(request,code,0),id);
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info("[{}]:凭证更新成功",id);
            return true;
        }
        throw new RuntimeException("凭证更新异常");
    }

}
