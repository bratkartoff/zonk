#!/bin/bash

port=20043

(echo auth $(cat ~/.emulator_console_auth_token)
echo redir add tcp:$port:$port
echo quit) | telnet 127.0.0.1 5554
