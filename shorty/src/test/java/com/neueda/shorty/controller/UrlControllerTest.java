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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gandreou on 04/02/2018.
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations= "classpath:application.properties")
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
    public void test__addUrl() throws Exception{
        int id = 1;
        String shortUrl = Base62.encode(id);
        //     decodedLongUrl = "http:google.com";
        String encodedLongUrl = "http%3A%2F%2Fgoogle.com";

        Url url = new Url(shortUrl,encodedLongUrl);

        when(urlService.addUrl(encodedLongUrl)).thenReturn(shortUrl);
        this.mockMvc.perform(get("/shorten/add?url=" + encodedLongUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string(TEST_RESOURCE_URI+"/"+shortUrl))
                .andDo(print());
    }

    @Test
    public void test__multiple_addUrl() throws Exception{
        //final      decodedLongUrl = "http:google.com";
        final String encodedLongUrl = "http%3A%2F%2Fgoogle.com";

        IntStream.range(1,100).forEach(i -> {
            log.info("#~#: iteration[i]: " + i);
            try {
                this.mockMvc.perform(get("/shorten/add?url=" + encodedLongUrl))
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

        int id = 1;
        String shortUrl = Base62.encode(id);

        //todo-allemaos: find the Sanitization method of URL's.
        //                 longUrl = "'http://google.com'";
        String encodedInputLongUrl = "'http%3A%2F%2Fgoogle.com'";
        String encodedOutputLongUrl = "http%3A%2F%2Fgoogle.com";

        Url url = new Url(shortUrl,encodedInputLongUrl);

        when(urlService.getUrl(shortUrl)).thenReturn(Optional.of(url));
        when(urlService.getCleanLongUrl(url)).thenReturn(encodedOutputLongUrl);
        this.mockMvc.perform(get("/shorten/" + shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",encodedOutputLongUrl))
                .andDo(print());
    }

    @Test
    public void test_get_error_cases() throws Exception {
        List<Url> url = Arrays.asList(
                new Url("short1","long1"),
                new Url("short2","long2"));

        when(urlRepository.findAll()).thenReturn(url);
        //fixme-allemaos: to catch the error messages
/*
        this.mockMvc.perform(get("/shorten/add?url='https%3A%2F%2Fwww.google.fr%2Fsearch%3Fq%3Dhow%2Bto%2Bfold%2Ba%2Bshirt%26oq%3Dhow%2Bto%2Bfold%2Ba%2Bshirt%26aqs%3Dchrome..69i57.3841j0j8%26sourceid%3Dchrome%26ie%3DUTF-8'"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("John Snow")));
        verify(userService, times(1)).findAllUsers();
        verifyNoMoreInteractions(userService);
*/
    }

}
