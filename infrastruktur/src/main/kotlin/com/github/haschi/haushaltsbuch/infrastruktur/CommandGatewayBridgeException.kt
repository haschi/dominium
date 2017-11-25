package com.github.haschi.haushaltsbuch.infrastruktur

open class CommandGatewayBridgeException(val error: ErrorCode, nachricht: String) : Exception(nachricht)
