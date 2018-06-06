import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { InventurService } from './inventur.service';
import { InventarEingabeService } from './shared/inventar-eingabe.service';
import { PositionEingabe } from './shared/inventarposition';

@Component({
    selector: 'app-inventur-eingabe-dialog',
    template: `<h3>Inventur-Posten erfassen</h3>
    <mat-dialog-content>
        <div [formGroup]="form" layout="row">
            <mat-form-field flex="50">
                <input matInput placeholder="Position" type="text" formControlName="name" name="name"/>    
            </mat-form-field>
            
            <div>
                <mat-form-field flex="30">
                    <input matInput placeholder="Betrag" type="text" formControlName="betrag" name="betrag"/>    
                </mat-form-field>
                <mat-form-field flex="20">
                    <input matInput placeholder="Währung" type="text" formControlName="waehrung" name="waehrung"/>    
                </mat-form-field>
            </div>
        </div>
    </mat-dialog-content>
        <mat-dialog-actions>
            <button mat-button mat-dialog-close>Abbrechen</button>
            <button mat-button [mat-dialog-close]="true">Speichern</button>
        </mat-dialog-actions>
    `
})
export class EingabeDialog implements OnInit {

    public form: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<EingabeDialog>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private build: FormBuilder,
        private service: InventarEingabeService)
    {
        dialogRef.beforeClose()
            .filter(result => result === true)
            .subscribe(params => {
                this.service.hinzufügen(this.data.gruppe, this.data.kategorie, this.eingabe)
            })
    }

    get eingabe(): PositionEingabe {
        return {
            position: this.form.value.name,
            waehrungsbetrag: {
                betrag: this.form.value.betrag,
                waehrung: this.form.value.waehrung
            }
        }
    }

    ngOnInit() {
        this.form = this.build.group({
            name: ['', Validators.required],
            betrag: ['', Validators.required],
            waehrung: ['EUR', Validators.required]
        })

        // this.form.valueChanges.subscribe(value => {
        //     console.info(JSON.stringify(value))
        // })
    }

    onNoClick(): void {
        this.dialogRef.close();
    }
}