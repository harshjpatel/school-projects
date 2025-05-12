import scrapy


class TestSpider(scrapy.Spider):
    name = 'test'
    allowed_domains = ['x']
    start_urls = ['http://x/']

    def parse(self, response):
        pass
