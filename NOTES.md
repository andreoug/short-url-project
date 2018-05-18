
Notes:

Random Thoughts:
1Q. Why do we encode the requested URL? Scenario and details: Reply with the encoded tiny URL and cache and store the persist the information for the upcomeing shortURL request.
1A. Cryptographic hash keys or what? Analysis: The length of the URL is variant, so We should follow an stantard length output encoding of any given variable length's input. NOPE.

2Q. Do we care so match to check for an already stored shortURL for any requested longURL? Can we reply without with a safe shortURL without asking for the already possibly saved shortURL?

2A. Analysis: It will be faster, but we need more memory/disk resources (see 3Q) and post processing batch and an extra column for indexing of the matching longURL in DB. (see also issue 1). Currently YES

3Q. Do we need InMemory db, RDBMS, or both?
3A. 

4Q. For the phase of shortURL to longURL: we need the classic approach of a proxy, edge and storage capacity (MQ, DB, etc)

Issues to review and also statistical analysis - about any approach 
1. How about the same URL's from the multimedia ecosystem like tw, fb etc? 
    Do the System needs to take care about this: a) proactively, b) using post processing or with a hybrid approach?


ref's:

0. Initial google search:
- https://www.google.ie/search?q=shorturl+how+is+implemented
- https://github.com/subosito/shorturl
- https://gist.github.com/rakeshsingh/64918583972dd5a08012
- https://www.pixelstech.net/article/1341231309-About-short-URL-and-its-implementation
- https://www.quora.com/What-are-the-http-bit-ly-and-t-co-shortening-algorithms
(Bloom filter)
- https://theworldsoldestintern.wordpress.com/2012/11/03/practical-uses-of-bloom-filter-spam-filter-and-url-shortener/
- https://social.technet.microsoft.com/Forums/ie/en-US/114f92e5-6615-4f11-a4c1-37892d00421d/how-is-a-short-url-for-onedrive-implemented?forum=privatecloud

possible read:
1. Queues - DB vs Redis vs RabbitMQ vs SQS [Dec 17, 2017] Dmitry Polyakovsky
http://dmitrypol.github.io/redis/rabbitmq/sqs/2017/12/17/queues.html