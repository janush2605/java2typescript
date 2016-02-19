export interface RecursiveTestClass {
    recursive: RecursiveTestClass;
    recursiveArray: Array<RecursiveTestClass>;
    returnThis(): RecursiveTestClass;
}


