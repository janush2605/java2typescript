export module modName {

    export interface StringClass {
        someField: string;
    }

    export enum ChangedEnumName {
        VAL1,
        VAL2,
        VAL3,
    }

    export interface TestClass {
        _String: string;
        _boolean: boolean;
        _Boolean: boolean;
        _int: number;
        _float: number;
        stringArray: Array<string>;
        map: { [key: string ]: boolean;};
        recursive: TestClass;
        recursiveArray: Array<TestClass>;
        stringArrayList: Array<string>;
        booleanCollection: Array<boolean>;
        _enum: ChangedEnumName;
        aMethod(param0:boolean, param1:string): string;
    }

}
