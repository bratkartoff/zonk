#!/bin/bash

port=20043

(echo auth $(cat ~/.emulator_console_auth_token)
echo redir del tcp:$port
echo quit) | nc localhost 5554
