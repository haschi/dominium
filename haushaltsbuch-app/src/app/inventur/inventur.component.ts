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
        private http: HttpClient,
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

            this.commandbus.send(
                'command.queue',
                beginneInventur,
                {command: 'BeginneInventur'}).subscribe(
                    reply => {this.log.log('REPLY: ' + JSON.stringify(reply)); },
                    error => {this.log.log('ERROR: ' + JSON.stringify(error)); });

        // const eb = new EventBus('/eventbus');
        // eb.onopen = () => {
        //     this.log.log('Geöffnet');
        //
        //     eb.registerHandler('command.queue', {}, (error, m) => {
        //         this.log.log('NAchricht erhalten');
        //         this.log.log(m);
        //     });
        //
        //     const beginneInventur = {id: '21a87417-fcc1-4df7-9f05-52b29e6dddd4'};
        //
        //     eb.send('command.queue', beginneInventur, {command: 'BeginneInventur'});
        // };


        // eb.state$.subscribe(state => {
        //     console.info('Status des Event Bus erhalten');
        //     console.info(state);
        // });



        // const message = {};
        //
        // eb.send('command.queue', message, {command: 'machmal'});


        // eb.rxSend('command.queue', message).subscribe(
        //     reply => {
        //         console.info('Antwort vom Server erhalten');
        //         console.info(reply);
        //     },
        //     error => {
        //         console.info('Fehler vom Server erhalten');
        //         console.info(JSON.stringify(error));
        //     }
        // );

        // this.http
        //     .post('/api/inventur', this.formGroup.value)
        //     .subscribe();
    }
}
