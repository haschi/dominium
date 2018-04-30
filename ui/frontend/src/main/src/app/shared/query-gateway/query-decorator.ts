export function Query(ns: string): Function {
    return (target: Function, key: string, d: any): PropertyDescriptor => {
        const descriptor = Object.getOwnPropertyDescriptor(target, key)
        descriptor.value = [ns, key].join('.');
        return descriptor;
    }
}