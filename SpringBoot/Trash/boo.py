import os
import requests

r = requests.get('http://localhost:8545')
print (r.json())
