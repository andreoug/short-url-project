package com.neueda.shorty.stats.repository;

import com.neueda.shorty.stats.model.Url;
import com.neueda.shorty.stats.util.Base62;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gandreou on 04/02/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application.properties")
@WebAppConfiguration
public class UrlRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlRepository repository;

    @Test
    public void test_url_repo() throws Exception{
        IntStream.range(1,500).forEach(i -> {
            Optional<Url> lastUrl = repository.findFirstByOrderByIdDesc();
            Long shortId = (!lastUrl.isPresent()) ? 1 : 1 + lastUrl.get().getId();
            String shortUrl = Base62.encode(shortId);
            Url url = new Url(shortId, shortUrl,"sboot");
            this.entityManager.persist(url);

            Optional<Url> u = this.repository.findByShortUrl(shortUrl);
            assertThat(u.get().getLongUrl()).isEqualTo("sboot");
            assertThat(u.get().getShortUrl()).isEqualTo(shortUrl);
        });
    }
}
