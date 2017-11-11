import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import * as EventBus from 'vertx3-eventbus-client';
import { LoggerService } from '../shared/logger.service';
import { CommandBusService } from '../shared/command-bus.service';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    private formGroup: FormGroup;

    private model: any;

    constructor(
        private builder: FormBuilder,
        private log: LoggerService,
        private commandbus: CommandBusService) {
    }

    ngOnInit() {
        this.formGroup = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });
    }

    speichern(): void {
        this.model = this.formGroup.value;
        this.log.log('Daten werden gespeichert');


        const beginneInventur = {id: '21a87417-fcc1-4df7-9f05-52b29e6dddd4'};

            this.commandbus
                .send(
                    'command.queue',
                    beginneInventur,
                    {command: 'BeginneInventur'})
                .subscribe(
                    reply => {this.log.log('REPLY: ' + JSON.stringify(reply)); },
                    error => {this.log.log('ERROR: ' + JSON.stringify(error)); });
    }
}
