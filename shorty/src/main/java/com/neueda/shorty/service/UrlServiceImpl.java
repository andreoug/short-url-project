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
import static com.neueda.shorty.util.UrlStatus.DELETED;

/**
 * Created by gandreou on 15/05/2018.
 */
@Service("urlService")
public class UrlServiceImpl implements UrlService{

    @Autowired
    UrlRepository urlRepository;

    private int SHORT_CODE_LENGTH = 7;
    private int ENCODING_BASE = 62;
    private long SHORT_ID_UPPER_LIMIT = (long)Math.pow((double)ENCODING_BASE,(double)SHORT_CODE_LENGTH);

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String addUrl(String longUrl) {
        Long shortId = getNextAvailableShortId();
        String shortUrl = Base62.encode(shortId);
        if(shortUrl != null) {
            cleanOldShortedId(shortId);
            Url u = new Url(shortId, shortUrl, longUrl);
            u.setCreated(new Date());
            urlRepository.save(u);
            log.info("new url added (codeLength: " + urlRepository.findByShortIdAndStatusNot(decode(shortUrl), DELETED.getStatus()));
        }
        return shortUrl;
    }

    @Override
    public Optional<Url> getUrl(String shortUrl) {
        Optional<Url> url = urlRepository.findByShortIdAndStatusNot(decode(shortUrl), DELETED.getStatus());
        if(url.isPresent()) {
            url.get().setRequested(new Date());
            url.get().setRequests(url.get().getRequests() + 1);
            urlRepository.save(url.get());
        }
        return url;
    }

    private Long getNextAvailableShortId(){
        Optional<Url> lastImportedUrl = urlRepository.findFirstByOrderByIdDesc();
        Long id = (!lastImportedUrl.isPresent()) ? 1 : 1 + lastImportedUrl.get().getId();
        Long shortId = (id < SHORT_ID_UPPER_LIMIT) ? id : (long)(id%SHORT_ID_UPPER_LIMIT);
        return  shortId;

    }

    private void cleanOldShortedId(Long shortId){
        if(shortId > SHORT_ID_UPPER_LIMIT) {
            Optional<Url> optionalUrl = urlRepository.findByShortIdAndStatusNot(shortId, DELETED.getStatus());
            if (optionalUrl.isPresent()) {
                Url oldUrl = optionalUrl.get();
                oldUrl.setStatus(DELETED.getStatus());
                urlRepository.save(oldUrl);
            }
        }
    }

}
