import { Injectable } from '@angular/core';
import { CommandBusEpicsService } from '../shared/command-bus-epics.service';

@Injectable()
export class RootEpicsService {

    constructor(private commandBusEpics: CommandBusEpicsService) {
    }

    public createEpics() {
        return [
            this.commandBusEpics.createEpic("Hello World")
        ];
    }
}
