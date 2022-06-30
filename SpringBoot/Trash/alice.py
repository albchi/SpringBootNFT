import urllib3

#print(urllib3.__version__)

http = urllib3.PoolManager()

url = 'localhost:8083/alice/balance'

resp = http.request('GET', url)
print (resp.status)

print (resp.data)
