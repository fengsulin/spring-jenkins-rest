package com.example.ci.api;

import com.example.ci.annotion.BodyParser;
import com.example.ci.domain.plugins.Plugins;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 插件管理接口
 */
@HttpExchange("/pluginManager")
public interface PluginManagerApi extends BaseApi{

    @GetExchange("/api/json")
    Plugins plugins(@RequestParam(required = false) String depth, @RequestParam(required = false) String tree);

    @PostExchange(value = "/installNecessaryPlugins",contentType = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<String> installNecessaryPlugins(@RequestBody @BodyParser String pluginXML);

    @PostExchange(value = "/uploadPlugin",contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadPlugin(@RequestPart("file") FilePart file);
}
