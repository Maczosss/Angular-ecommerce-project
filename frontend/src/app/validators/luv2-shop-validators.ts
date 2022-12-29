import { FormControl, ValidationErrors } from "@angular/forms";

export class Luv2ShopValidators {

    //whitespace validation
    static notOnlyWhiteSpace(control: FormControl): ValidationErrors {
        
        //check if string only contains whitespaces
        if((control.value !=null)&&(control.value.trim().length ===0)){
            return { 'notOnlyWhitespace': true };
        }else{

            //can't return null, so returning null in different way
        return {};
        }
    }
}
