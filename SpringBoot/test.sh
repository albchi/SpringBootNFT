echo " ----- 1. test Spring front end -----"
curl -X GET localhost:8083/test

echo ""

echo " ----- 2. test VMBC back end -----"
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_gasPrice","id":1}' --header "Content-Type: application/json" 192.168.59.110:30545
# (expected response is 0x9999999999)
echo ""


