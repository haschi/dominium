package com.github.haschi.haushaltsbuch.infrastruktur

class UngültigerNachrichtenkopf(nachricht: String) : CommandGatewayBridgeException(ErrorCode.UngültigerNachrichtenkopf, nachricht)
