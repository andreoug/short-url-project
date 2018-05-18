Shorty  
--------------

Description

A simplistic recipe of a shorten service is that: For any given URL, there is a unique short code that can be saved for any future redirection to the initial URL. In fact, the shortCode is periodically unique. Also when a viral tweet spreads in the social media ecosystem, there are at millions of shortCodes refer to the same URL.

I built the first part of the shorten service with the periodical unique shortCode's which can be used to redirect to the initial long URL address. 

The encoding of base62 drives the rule which is used to store and overwrite old shortCodes. The "shorty" uses 7 digits for the encoding. The shortCode is the encoded index of a tuple/row that leads to the longURL. The maximum of the Shorty's space is analogous of the 62^7 map of data. So any new shortURL's with be placed in this space accordingly.   

The second part of the shorten service needs a component to cover the statistical gap of knowledge for any given peak of the longURLs' distribution. That was my initial thought about the ShortyStats component which is not implemented yet. 

Notes for further development:
- remove logging functionality and add an external compponent to redirect and store logging data to a separate layer/component  
- Align Error messages with any future requirements.   
- Second-Level Caching, MQ, and NoSQL layers to save power, response time and possibly the queen.
