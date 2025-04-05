import FieldsValidation from "./FieldsValidation"
import {EnterpriseConfig} from "../../entities/Enterprise"
import {CandidateConfig} from "../../entities/Candidate"
import ValidationForms from "../ValidationForms"
import {EmploymentConfig} from "../../entities/Employment"

export default class EmploymentValidation extends FieldsValidation {

    constructor() {
        super()
    }
    
    check(input: EmploymentConfig): boolean {

        if (this.hasEmptyFields(input)) {
            this.showValidationError("Preencha todos os campos obrigatórios!");
            return false;
        }

        const validationErrors = this.getErrors(input);
        if (validationErrors.length > 0) {
            this.showValidationErrors(validationErrors);
            return false;
        }
        
        return true
    }

    private hasEmptyFields(data: EnterpriseConfig): boolean {
        let result = false
        Object.values(data).some(value => {
            if (this.isEmptyValue(value)) {
                result = true
                return
            }
        })
        return result
    }

    private isEmptyValue(value: any): boolean {
        if (Array.isArray(value)) return value.length === 0
        if (typeof value === "number") return value === 0
        return String(value).trim() === ""
    }

    private getErrors(data: EmploymentConfig): string[] {
        return Object.entries(data).reduce((errors: string[], [key, value]) => {
            const errorMessage = this.validateField(key, String(value))
            if (errorMessage) errors.push(errorMessage)
            return errors
        }, [] as string[])
    }

    private validateField(key: string, value: string): string | null {
        const isValid = this.entry(key, value)
        return isValid ? null : FieldsValidation.validationFailMessageEmployment(key)
    }

    private showValidationErrors(errors: string[]): void {
        const combinedErrors = errors.join("\n\n")
        this.showValidationError(combinedErrors)
    }

    private showValidationError(message: string): void {
        alert(message)
    }


}