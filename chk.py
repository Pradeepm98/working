import requests


proxy ={
    "http": 'http://103.247.44.147:80',
    "https": 'http://103.247.44.147:80'
}


headers = {
   # 'authority': 'www.delta.com',
    'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
    'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
    'cache-control': 'no-cache',
    'dnt': '1',
    'pragma': 'no-cache',
    'sec-ch-ua': '"Google Chrome";v="113", "Chromium";v="113", "Not-A.Brand";v="24"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': '"Linux"',
    'sec-fetch-dest': 'document',
    'sec-fetch-mode': 'navigate',
    'sec-fetch-site': 'none',
    'sec-fetch-user': '?1',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36',
}

import time

start_time = time.time()
for _ in range(1):
     response = requests.get('https://www.google.com/', headers=headers, proxies=proxy)
     print(response.status_code)
# print(response.cookies)
# print(response.status_code)
# print(response.headers)
# print(response.text)

end_time = time.time()

elapsed_time = end_time - start_time

print("Time elapsed: " + str(elapsed_time))
