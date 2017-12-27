import { Injectable } from '@angular/core';
import { CommandGatewayEpicsService } from '../shared/command-gateway/command-gateway-epics.service';

@Injectable()
export class RootEpicsService {

    constructor(private commandBusEpics: CommandGatewayEpicsService) {
    }

    public createEpics() {
        return [
            ...this.commandBusEpics.createEpic(),
        ];
    }
}
