# Sneakerwatch

### How to Elasticsearch

täiesti vittus, see sitt hakkas töööle.

https://www.elastic.co/downloads/elasticsearch tõmba see asi
Find elasticsearch root (/etc/elasticsearch)
Open elastisearch.yml and change the following things:

```
cluster.name: elasticsearch
uncomment node.name (that may not be necessary but fuck it)
```

### How to crawl

Crawler requires pip and python 3.5+


```sh
pip install scrapy
cd sneakercrawler
scrapy crawl sneakerUrl
scrapy crawl sneaker
```
Crawler saves sneaker links to links.json and sneaker data to data.json
