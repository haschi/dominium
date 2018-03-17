import { Injectable } from '@angular/core';

const uuidv4 = require('uuid/v4');

@Injectable()
export class IdGeneratorService {

    constructor() {}

    neu(): any {
        return uuidv4();
    }
}
