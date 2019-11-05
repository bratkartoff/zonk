#!/bin/bash

port=20043

(echo auth $(cat ~/.emulator_console_auth_token)
echo redir add tcp:$port:$port
echo quit) | nc localhost 5554
