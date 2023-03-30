package com.example.ci.controller;

import com.example.ci.domain.user.ApiToken;
import com.example.ci.domain.user.User;
import com.example.ci.api.UserApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户管理接口")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Lazy
public class UserController {

    private final UserApi userApi;

    @GetMapping("/{user}")
    @Operation(summary = "获取用户信息")
    public User getUser(@PathVariable String user){
        return userApi.get(user);
    }

    @Operation(summary = "创建用户新的token")
    @Parameter(name = "tokenName",required = true,description = "token名称，英文+数字")
    @PostMapping(value = "/{user}/newToken",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ApiToken createNewToken(@PathVariable String user, @RequestParam("tokenName") String newTokenName){
        return userApi.generateNewToken(user,newTokenName);
    }

    @PostMapping("/{user}/revokeToken")
    @Operation(summary = "删除用户token")
    @Parameter(name = "tokenUuid",required = true,description = "token 唯一id")
    public boolean revokeToken(@PathVariable String user,@RequestParam("tokenUuid") String tokenUuid){
        ResponseEntity<String> entity = userApi.revoke(user, tokenUuid);
        if (entity.getStatusCode().is3xxRedirection() || entity.getStatusCode().is2xxSuccessful()){
            log.info("[" + user + "#" + tokenUuid +"]:token删除成功");
            return true;
        }
        log.error("[" + user + "#" + tokenUuid +"]:token删除失败");
        return false;
    }
}
