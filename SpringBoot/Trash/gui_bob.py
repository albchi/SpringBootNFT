# hello_psg.py
# A simple GUI 

import requests
import PySimpleGUI as sg

layout = [[sg.Text("Bob the NFT collector")], 
    [sg.Button("Bob Balance")]
    ]

# Create the window
window = sg.Window("Bob", layout)

# Create an event loop
while True:
    event, values = window.read()
    # End program if user closes window or
    # presses the OK button
    if event == sg.WIN_CLOSED:
        break
    if event == "Bob Balance":
        r = requests.get('http://localhost:8083/bob/balance')
        print ("Bob Balance")
        print (r.text)


window.close()
