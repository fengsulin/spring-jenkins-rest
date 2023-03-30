package com.example.ci.controller;

import com.example.ci.domain.plugins.Plugins;
import com.example.ci.api.PluginManagerApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;

@Tag(name = "插件管理接口")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pluginManager")
@Lazy
public class PluginsController {

    private final PluginManagerApi pluginManagerApi;

    @GetMapping("/list")
    @Operation(summary = "获取插件列表")
    public Plugins listPlugins(){
       return pluginManagerApi.plugins("1", null);
    }

    @PostMapping("/installNecessary")
    @Operation(summary = "安装给定的插件id，格式：pluginName@version",description = "利用Jenkins服务安装，需要联网")
    @Parameter(name = "pluginId",required = true,description = "插件名称")
    public boolean installNecessaryPlugins(@RequestParam("pluginId") String pluginId){
        ResponseEntity<String> entity = pluginManagerApi.installNecessaryPlugins(pluginId);
        if (entity.getStatusCode().is2xxSuccessful() || entity.getStatusCode().is3xxRedirection()){
            log.info("[" + pluginId +"]:插件安装命令执行成功");
            return true;
        }
        log.error("[" + pluginId +"]:插件安装命令执行失败");
        return false;
    }

    // TODO 待处理spring web 框架与webflux框架对于文件处理的冲突
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "手动上传插件")
    public ResponseEntity<String> uploadPlugin(@RequestPart("file") FilePart file){
        return pluginManagerApi.uploadPlugin(file);
    }
}
