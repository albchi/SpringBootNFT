# list of ETH-RPC commands at https://eth.wiki/json-rpc/API
# eth_getBalance eth_getCode eth_getTransactionCount eth_getStorageAt eth_call

# check eth_accounts on Ganache Ethereum
curl -H "Content-Type: application/json" -X POST \
--data '{"id":1,"jsonrpc":"2.0","method":"eth_accounts","params":[]}' \
http://localhost:7545

#{"id":1,"jsonrpc":"2.0","result":["0x09fcf382a47b8121e77afc13d18d1a5ba2095296","0x44d649b944e0e178575389120322643d0be0c116","0x4979b2f84ace9426bbcbcb7a9b916fc08052e37a","0xcd392fd67b2998060c106a2492516c846feecf2e","0x3feb25230c89596fd5abf2fa5c087bd24a8a8db6","0x89e4212baffcbd542f1d73e7016ae5b8ef430f29","0x4e1e3e40824f19cf8e6d1f53070593950f3dd726","0x210b41fe6112c92112b5e2085a3adeeef8734b3a","0xcd7eb40001d90fe668e1db2ec3d30788db93f2cc","0x9c56a1fbb75120fdb872ae39d8ceb396624ca618"]}

# check eth_accounts on Ganache Ethereum
curl -H "Content-Type: application/json" -X GET \
--data '{"id":1,"jsonrpc":"2.0","method":"eth_getBalance","params":[0x09fcf382a47b8121e77afc13d18d1a5ba2095296]}' \
http://localhost:7545


