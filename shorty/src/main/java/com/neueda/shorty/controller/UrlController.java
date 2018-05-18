package com.neueda.shorty.controller;

import com.neueda.shorty.service.UrlService;
import com.neueda.shorty.model.Url;
import com.neueda.shorty.util.CustomErrorType;
import com.neueda.shorty.util.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller
@PropertySource("classpath:application.properties")
@RequestMapping(path="/shorten")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Value("${spring.application.host}")
    public String RESOURCE_URI;

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody String addUrl(@RequestParam(name = "url") String longUrl, HttpServletResponse httpServletResponse) {

        //fixme-allemaos: change the headers from every errors accordingly
        //fixme-allemaos: change the body from every errors accordingly

        //fixme: check the response from an empty request through "throws URISyntaxException"
/*
        {
            "timestamp": "2018-05-18T00:42:09.121+0000",
                "status": 404,
                "error": "Not Found",
                "message": "No message available",
                "path": "/shorten/"
        }
*/


        if(StringUtils.isBlank(longUrl))
            return ErrorMessage.BAD_REQUEST_EMPTY_URL.getMessage();

        String shortUrl = urlService.addUrl(longUrl);
        if(shortUrl != null)
            return RESOURCE_URI +"/"+ shortUrl;

        return ErrorMessage.SERVER_ERROR.getMessage();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public @ResponseBody String postUrl(@RequestBody String longUrl) {

        String shortUrl = urlService.addUrl(longUrl);
        if(shortUrl != null)
            return shortUrl;

        return ErrorMessage.BAD_REQUEST_EMPTY_URL.getMessage();
    }

    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public ResponseEntity<Object> redirect(@PathVariable("url") String shortUrl)  throws URISyntaxException {

        Optional<Url> url = urlService.getUrl(shortUrl);

        if(!url.isPresent())
            return new ResponseEntity<>(new CustomErrorType(ErrorMessage.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND.toString()),
                    HttpStatus.NOT_FOUND);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(urlService.getCleanLongUrl(url.get())));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);


    }
}

