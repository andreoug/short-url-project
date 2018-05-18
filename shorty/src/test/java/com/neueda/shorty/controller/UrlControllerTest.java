package com.neueda.shorty.controller;

import com.neueda.shorty.Application;
import com.neueda.shorty.model.Url;
import com.neueda.shorty.repository.UrlRepository;
import com.neueda.shorty.service.UrlService;
import com.neueda.shorty.util.Base62;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gandreou on 04/02/2018.
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations= "classpath:test.properties")
@WebAppConfiguration
public class UrlControllerTest {


    private MockMvc mockMvc;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @Value("${spring.application.host}")
    private String TEST_RESOURCE_URI;


    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void init() throws IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(urlController)
                .build();

        FieldUtils.writeField(urlController, "RESOURCE_URI", TEST_RESOURCE_URI);

    }

    @Test
    public void test__postUrl() throws Exception{
        long shortId = 1;
        String shortUrl = Base62.encode(shortId);
        String longUrl = "http://google.com";

        Url url = new Url(shortId, shortUrl, longUrl);

        when(urlService.addUrl(longUrl)).thenReturn(shortUrl);
        mockMvc.perform(
                post("/shorten/post")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(longUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string(TEST_RESOURCE_URI+"/"+shortUrl))
                .andDo(print());
    }

    @Test
    public void test__multiple_postUrl() throws Exception{
        final String longUrl = "http://google.com";

        IntStream.range(1,100).forEach(i -> {
            log.info("#~#: iteration[i]: " + i);
            try {
                mockMvc.perform(
                        post("/shorten/post")
                                .contentType(MediaType.TEXT_PLAIN)
                                .content(longUrl))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                        .andDo(print());
            } catch (Exception e) {
                log.error("#~#: test_addUrl error while performing multiple add url's", e);
            }
        });
    }

    @Test
    public void test_get_longUrl_from_shortUrl() throws Exception {

        long shortId = 1;
        String shortUrl = Base62.encode(shortId);
        String longUrl = "http://google.com";
        Url url = new Url(shortId, shortUrl,longUrl);

        when(urlService.getUrl(shortUrl)).thenReturn(Optional.of(url));
        this.mockMvc.perform(get("/shorten/" + shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",longUrl))
                .andDo(print());
    }
}
