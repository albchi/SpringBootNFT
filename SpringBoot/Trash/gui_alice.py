# hello_psg.py
# A simple GUI 

import os
import requests
import PySimpleGUI as sg

layout = [[sg.Text("Alice the poet to be")], 
    [sg.Button("Alice Address")],
    [sg.Button("Bob Address")],
    [sg.Button("Alice Balance")],
    [sg.Button("Alice Mint")],
    [sg.Button("Alice Transfer")],
    [sg.Button("Alice Transfer OS")]
    ]

# Create the window
window = sg.Window("Alice", layout)

# Create an event loop
while True:
    event, values = window.read()
    # End program if user closes window or
    # presses the OK button
    if event == sg.WIN_CLOSED:
        break
    if event == "Ping Spring":
        r = requests.get('http://localhost:8083')
        print (r.text)

    if event == "Ping VMware Blockchain 10.72.222.91:8545 ":
        r = requests.get('http://10.72.222.91:8545')
        print (r.json())


    if event == "Ping VMware Blockchain local":
        r = requests.get('http://localhost:8545')
        print (r.json())

    if event == "Alice Address":
        r = requests.get('http://localhost:8083/alice')
        print ("Alice Address:")
        print (r.text)

    if event == "Bob Address":
        r = requests.get('http://localhost:8083/bob')
        print ("Bob Address:")
        print (r.text)

    if event == "Op Deploy":
        r = requests.get('http://localhost:8083/deploy')
        print ("Op Deploy Smart Contract")
        print (r.text)

    if event == "Alice Balance":
        r = requests.get('http://localhost:8083/alice/balance')
        print ("Alice Balance")
        print (r.text)


    if event == "Bob Balance":
        r = requests.get('http://localhost:8083/bob/balance')
        print ("Bob Balance")
        print (r.text)

    if event == "Alice Mint":
       #r = requests.get('http://localhost:8083/mint --data-urlencode "title=my last poem" --data-urlencode "text=to be or not to be" --data-urlencode "author=alice"')
       r = requests.get('http://localhost:8083/mint?title="my first poem"&text="to be or not to be"&author="alice"')
       print ("Alice Mint");
       print (r.text)

    if event == "Alice Transfer OS":
       os.system('curl http://localhost:8083/alice/tokens/0/transfer?transferToAddress="0xf4d5b303a15b04d7c6b7510b24c62d393805b8d7"')
       print ("Alice Transfer to Bob");
       #print (r.text)


    if event == "Alice Transfer":
       r = requests.get('http://localhost:8083/alice/tokens/0/transfer?transferToAddress="0xf4d5b303a15b04d7c6b7510b24c62d393805b8d7"')
       print ("Alice Transfer to Bob");
       #print (r.text)

window.close()
