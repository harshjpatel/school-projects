import scrapy
import os
import requests
import logging

class MainSpider(scrapy.Spider):
    name = "MainSpider"
    logging.getLogger("urllib3").setLevel(logging.WARNING)

    custom_settings = {
        'DEPTH_LIMIT': '2',
    }

    def __init__(self, url='', **kwargs):
        self.links=[]
        self.allowed_domains = ['en.wikipedia.org', 'www.iit.edu', 'www.phdessay.com']
        self.start_urls = [url]  # py36
        self.base_url = 'en.wikipedia.org'
        super(MainSpider, self).__init__(**kwargs)  # python3

    save_path = 'htmlFiles'

    def parse(self, response, dest_path = save_path, save_path=save_path):
        init = 0
        for link in response.xpath('//div/p/a'):
            # if init == 2:
            #     break
            yield {
                "link": self.base_url + link.xpath('.//@href').get()
            }
            print(self.base_url + link.xpath('.//@href').get())
            filePath = self.base_url + link.xpath('.//@href').get()
            words = ['tel', 'phone', 'mob', 'mailto', 'Special']
            filePath_upd = filePath.rsplit('/', 1)[-1] + '.html'
            for word in words:
                if word in filePath:
                    filePath = self.base_url
            print(requests.get('https://{}'.format(filePath)))
            if requests.get('https://{}'.format(filePath)).status_code == 200:
                for f in os.listdir(save_path):
                    if init == 0:
                        if not f.endswith(".html"):
                            continue
                        os.remove(os.path.join(save_path, f))
                if os.path.join('htmlFiles', filePath_upd):
                    with open(os.path.join('htmlFiles', filePath_upd), 'w'):
                        pass
                with open(os.path.join('htmlFiles', filePath_upd), "wb") as file:
                    # response = get(self.base_url + link.xpath('.//@href').get())
                    response = requests.get('https://{}'.format(filePath))
                    file.write(response.content)
            # if requests.get('http://{}'.format(filePath)).status_code == 200:
            #     for f in os.listdir(save_path):
            #         if init == 0:
            #             if not f.endswith(".html"):
            #                 continue
            #             os.remove(os.path.join(save_path, f))
            #     if os.path.join('htmlFiles', filePath_upd):
            #         with open(os.path.join('htmlFiles', filePath_upd), 'w'):
            #             pass
            #     with open(os.path.join('htmlFiles', filePath_upd), "wb") as file:
            #         # response = get(self.base_url + link.xpath('.//@href').get())
            #         response = requests.get('http://{}'.format(filePath))
            #         file.write(response.content)
            else:
                pass
            init += 1









