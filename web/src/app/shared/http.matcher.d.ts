declare module jasmine {
    interface Matchers {
        toPostJson(expected: any): boolean;
    }
}
