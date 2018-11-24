# Sneakerwatch

### How to Elasticsearch
- Download elasticsearch

https://www.elastic.co/downloads/elasticsearch

Find elasticsearch root (/etc/elasticsearch)

(pro tip: Macbook users can `brew install elasticsearch` it)

Open elastisearch.yml and change the following things:

```yaml
cluster.name: elasticsearch
node.name: node_1
```

### How to crawl

Crawler requires PIP and Python 3.5+


```bash
pip install scrapy
cd sneakercrawler
scrapy crawl sneakerUrl
scrapy crawl sneaker
```
Crawler saves sneaker links to links.json and sneaker data to data.json


### How to save crawled data

In order to speed up development process, we added 300 lines of mock data. Shoes, actually. To set it up to your database, do the following:

_I assume you have received your data.json file from the previous step._

Now, run the Spring Boot application and open up the following URL:

`localhost:${your-port-which-is-8080-by-default}/swagger-ui.html`

Find the endpoint called `/api/public/admin/sneakers/getFromJson` and give it your data.json file.

Press EXECUTE and congrats, you've successfully added the first rows to your database.

Happy hunting!