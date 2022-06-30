# gui_op.py
# LiberatedArtists NFT Exchanged : Operator

import requests
import PySimpleGUI as sg

layout = [[sg.Text("NFT Exchange Operator")], 
    [sg.Button("Ping Spring")],
    [sg.Button("Ping VMware Blockchain local")],
    [sg.Button("Ping VMware Blockchain 10.72.222.91:8545 ")],
    [sg.Button("Op Deploy Smart Contract")]
    ]

# Create the window
window = sg.Window("Demo", layout)

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

    if event == "Op Deploy":
        r = requests.get('http://localhost:8083/deploy')
        print ("Op Deploy Smart Contract")
        print (r.text)


window.close()
