import {Request} from "./Request";
export class Person{


    constructor(public data:Request){
    }

    getFullData():String{
    return this.data.number+" "+this.data.itemIndex;
}
}