import { Injectable } from '@angular/core';

const uuidv1 = require('uuid/v1');

@Injectable()
export class IdGeneratorService {

    constructor() {
    }

    neu(): any {
        return uuidv1();
    }
}
