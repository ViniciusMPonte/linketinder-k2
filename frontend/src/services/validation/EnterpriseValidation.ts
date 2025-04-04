import FieldsValidation from "./FieldsValidation";
import {EnterpriseConfig} from "../../entities/Enterprise";
import {CandidateConfig} from "../../entities/Candidate"

export default class EnterpriseValidation extends FieldsValidation {

    checkRegistrationData(enterpriseData: EnterpriseConfig): boolean {
        if (this.hasEmptyFields(enterpriseData)) {
            this.showValidationError("Preencha todos os campos obrigatórios!");
            return false;
        }

        const validationErrors = this.getValidationErrors(enterpriseData);
        if (validationErrors.length > 0) {
            this.showValidationErrors(validationErrors);
            return false;
        }

        return true;
    }

    checkLoginData(enterpriseData: EnterpriseConfig): boolean {

        const validationErrors = this.getValidationErrors(enterpriseData);
        if (validationErrors.length > 0) {
            this.showValidationErrors(validationErrors);
            return false;
        }

        return true;
    }

    private hasEmptyFields(data: EnterpriseConfig): boolean {
        let result = false
        Object.values(data).some(value => {
            if (this.isEmptyValue(value)) {
                result = true
                return
            }
        });
        return result
    }

    private isEmptyValue(value: any): boolean {
        if (Array.isArray(value)) return value.length === 0;
        if (typeof value === "number") return value === 0;
        return String(value).trim() === "";
    }

    private getValidationErrors(data: EnterpriseConfig): string[] {
        return Object.entries(data).reduce((errors:string[], [key, value]) => {
            const errorMessage = this.validateField(key, String(value));
            if (errorMessage) errors.push(errorMessage);
            return errors;
        }, [] as string[]);
    }

    private validateField(key: string, value: string): string | null {
        const isValid = FieldsValidation.entry(key, value);
        return isValid ? null : FieldsValidation.validationFailMessageEnterprise(key);
    }

    private showValidationErrors(errors: string[]): void {
        const combinedErrors = errors.join("\n\n");
        this.showValidationError(combinedErrors);
    }

    private showValidationError(message: string): void {
        alert(message);
    }

}