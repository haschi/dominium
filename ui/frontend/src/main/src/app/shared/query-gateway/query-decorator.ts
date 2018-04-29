export function Command(ns: string): any {
    return (target: Function, key: string, d: any): any => {
        const descriptor2 = Object.getOwnPropertyDescriptor(target, key)
        descriptor2.value = ns + "." + key;
        return descriptor2;
    }
}

export function Query(ns: string): any {
    return (target: Function, key: string, d: any): any => {
        const descriptor2 = Object.getOwnPropertyDescriptor(target, key)
        descriptor2.value = ns + "." + key;
        return descriptor2;
    }
}