
import { apiFetch } from "../api-fetch";
import { ContributorStatus } from "@/types/contributor-status";
import { ReviewStatus } from "@/types/reviewStatus";
import { ConsentFormStatus } from "@/types/consent-form-status";

//Welcome mesage on top of page, changing depending on form status
export async function getWelcomeMessage() : Promise<WelcomeResponse>{

    const res = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/welcome`, {
        credentials: "include",
    });

    return res.json();
}

export interface WelcomeResponse {
    status: ContributorStatus
    message: string;
}


//contributor agreement form

//Get form status
export async function getContributorAgreementForm() : Promise<ContributorFormResponse>{
   const res = await apiFetch(
    `${process.env.NEXT_PUBLIC_API_URL}/contributor/consent`, { 
            credentials: "include",
    });

      
    return res.json();
}

export interface ContributorFormResponse{

    idCardFilePath : string;
    idCardReviewed : ReviewStatus | null;
    idCardMessage : string;
    idFaceFilePath : string;
    idFaceReviewed : ReviewStatus | null;
    idFaceMessage : string;
    facefffFilePath : string;
    facefffReviewed : ReviewStatus | null;
    facefffMessage : string;
    approvedRules : boolean;
    consentFormStatus: ConsentFormStatus | null;
    status : ContributorStatus;
}

//Upload documents first time or after rejected or approved
export async function postContributorAgreementForm(formData: FormData){
    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/consent`,
        {
            method: "POST",
            credentials: "include",
            body: formData,
        }
    );    

    const json = await response.json();

    if (!response.ok) {
        throw json;
    }

    return json; 
}