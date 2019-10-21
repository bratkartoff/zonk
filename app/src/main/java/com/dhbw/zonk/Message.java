package com.dhbw.zonk;

import java.io.Serializable;

public interface Message extends Serializable {
	ClientState process(ClientState state, long playerId);
}
