# -*- coding: utf-8 -*-
import scrapy
from scrapy import signals


class SneakerUrlSpider(scrapy.Spider):
    name = "sneakerUrl"
    start_urls = [
        'https://runrepeat.com/ranking/rankings-of-sneakers',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=2',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=3',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=4',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=5',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=6',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=7',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=8',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=9',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=10',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=11',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=12',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=13',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=14',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=15',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=16',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=17',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=18',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=19',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=20',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=21',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=22',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=23',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=24',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=25',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=26',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=27',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=28',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=29',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=30',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=31',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=32',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=33',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=34',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=35',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=36',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=37',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=38',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=39',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=40',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=41',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=42',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=43',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=44',
        'https://runrepeat.com/ranking/rankings-of-sneakers?page=45'
    ]
    length = len(start_urls)
    visited = 0
    file_name = "links.json"

    @classmethod
    def from_crawler(cls, crawler, *args, **kwargs):
        spider = super(SneakerUrlSpider, cls).from_crawler(crawler, *args, **kwargs)
        crawler.signals.connect(spider.spider_opened, signals.spider_opened)
        crawler.signals.connect(spider.spider_closed, signals.spider_closed)
        return spider

    def spider_opened(self):
        with open(self.file_name, "w") as f:
            f.write("[")

    def spider_closed(self):
        with open(self.file_name, "a") as f:
            f.write("]")

    def parse(self, response):
        xpath = response.xpath('//a[contains(@itemprop, "aggregateRating")]/@href').extract()
        results = list()
        for index, link in enumerate(xpath):
            results.append(link)
        self.visited = self.visited + 1
        with open(self.file_name, 'a') as f:
            for index, item in enumerate(results):
                if index == len(results) - 1 and self.visited == self.length:
                    f.write("\"%s\"\n" % item)
                else:
                    f.write("\"%s\",\n" % item)
