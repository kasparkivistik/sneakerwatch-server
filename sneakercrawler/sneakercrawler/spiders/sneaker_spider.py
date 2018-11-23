# -*- coding: utf-8 -*-
import json
import re
import time
from scrapy_splash import SplashRequest

import scrapy
from scrapy import signals


class SneakerSpider(scrapy.Spider):
    name = "sneaker"
    start_urls = []
    visited = 0
    data_file_name = "data.json"
    url_file_name = "links.json"
    url_regex = 'http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+'

    # def start_requests(self):
    #     for url in self.start_urls:
    #         print(url)
    #         yield SplashRequest(url, self.parse, endpoint='render.html',args={'wait': 1.0, 'har':1, 'response_body': 1, 'images': 1, 'render_all': 1, 'html5_media':1})

    def spider_opened(self):
        with open(self.data_file_name, "w") as f:
            f.write("[")
        with open(self.url_file_name, "r") as f:
            self.start_urls = json.load(f)

    def spider_closed(self):
        with open(self.data_file_name, "a") as f:
            f.write("]")

    @classmethod
    def from_crawler(cls, crawler, *args, **kwargs):
        spider = super(SneakerSpider, cls).from_crawler(crawler, *args, **kwargs)
        crawler.signals.connect(spider.spider_opened, signals.spider_opened)
        crawler.signals.connect(spider.spider_closed, signals.spider_closed)
        return spider

    def parse(self, response):
        xpath = response.xpath('//div[@class="fact-text"]')
        sneaker = {"name": response.xpath('//h1[contains(@class, "main-shoe-title")]/span/text()').extract_first()}
        for index, fact in enumerate(xpath):
            fact_name = fact.xpath('.//div[@class="fact-title"]/span/text()').extract_first()
            fact_name_text = re.findall("\w+", fact_name, re.MULTILINE)[0]
            fact_name_text = fact_name_text.lower()
            if fact_name_text != 'price':
                fact_value = fact.xpath('.//div[contains(@class, "fact-value")]/div/a/text()').extract_first()
            else:
                fact_value = fact.xpath('.//div[contains(@class, "fact-value")]/text()').extract_first()
                fact_value = ''.join(c for c in fact_value if c.isdigit())

            sneaker[fact_name_text] = fact_value
        sneaker["good"] = []
        sneaker["bad"] = []
        sneaker["description"] = response.xpath('//div[contains(@itemprop, "description")]/p/text()').extract_first()
        sneaker["score"] = response.xpath('//div[@class = "corescore-value"]/text()').extract_first()
        sneaker["brand"] = response.xpath('//a[@class = "brand-image"]/img/@alt').extract_first()
        sneaker["history"] = ' '.join(response.xpath('//div[@class = "product-history"]/p/text()').extract())
        sneaker["feature"] = ' '.join(response.xpath('//div[@class = "product-notable-feature"]/p/text()').extract())
        sneaker["style"] = ' '.join(response.xpath('//div[@class = "product-style"]/p/text()').extract())
        sneaker["additional"] = ' '.join(response.xpath('//div[@class = "product-additional-info"]/ul/li/text()').extract())

        image = response.xpath('//meta[contains(@property, "og:image")]').extract_first()
        sneaker["imageUrl"] = re.findall(self.url_regex, image)[0]
        good_reasons = response.xpath('//div[@id="the_good"]/div/div/ul/li/text()').extract()
        bad_reasons = response.xpath('//div[@id="the_bad"]/div/div/ul/li/text()').extract()
        for index, reason in enumerate(good_reasons):
            sneaker["good"].append(reason)
        for index, reason in enumerate(bad_reasons):
            sneaker["bad"].append(reason)
        self.visited = self.visited + 1

        with open(self.data_file_name, 'a') as f:
            json.dump(sneaker, f, indent=4)
            if self.visited != len(self.start_urls):
                f.write(',')
