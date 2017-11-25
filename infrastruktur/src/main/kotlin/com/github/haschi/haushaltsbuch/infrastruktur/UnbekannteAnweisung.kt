package com.github.haschi.haushaltsbuch.infrastruktur

class UnbekannteAnweisung(command: String) : CommandGatewayBridgeException(ErrorCode.UnbekannteAnweisung, command)
