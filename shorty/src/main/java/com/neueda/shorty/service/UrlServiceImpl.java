package com.neueda.shorty.service;

import com.neueda.shorty.model.Url;
import com.neueda.shorty.repository.UrlRepository;
import com.neueda.shorty.util.Base62;
import com.neueda.shorty.util.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.neueda.shorty.util.Base62.decode;

/**
 * Created by gandreou on 15/05/2018.
 */
@Service("urlService")
public class UrlServiceImpl implements UrlService{

    @Autowired
    UrlRepository urlRepository;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String addUrl(String longUrl) {
        String shortUrl = getNextAvailableSortUrl();
        if(shortUrl != null) {
            Url u = new Url(shortUrl, longUrl);
            u.setCreated(new Date());
            urlRepository.save(u);
            log.info("new url added: " + urlRepository.findById(decode(shortUrl)));

        }
        return shortUrl;
    }

    @Override
    public Optional<Url> getUrl(String shortUrl) {
//        Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
        Optional<Url> url = urlRepository.findById(decode(shortUrl));
        if(url.isPresent()) {
            url.get().setRequested(new Date());
            url.get().setRequests(url.get().getRequests() + 1);
            urlRepository.save(url.get());
        }
        return url;
    }

    @Override
    public String getCleanLongUrl(Url url) {
        String longUrl = url.getLongUrl();
        return longUrl.substring(1,longUrl.length()-1);
    }

    private String getNextAvailableSortUrl(){
        Optional<Url> lastImportedUrl = urlRepository.findFirstByOrderByIdDesc();
        long nextId = (!lastImportedUrl.isPresent()) ? 1 : 1 + lastImportedUrl.get().getId();
        return Base62.encode(nextId);
    }


}
