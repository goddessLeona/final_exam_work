import { promises } from "dns";

export interface WelcomeResponse {
    contributor: boolean;
    message: string;
}

export async function getWelcomeMessage() : Promise<WelcomeResponse>{

    const res = await fetch("http://localhost:8080/contributor/welcome", {
        credentials: "include",
    });

    if(!res.ok){
        throw new Error("Unautorized")
    }

    return res.json();
}


export interface ContributorMeResponse {
    username: string;
    yearSignedUp: number;
    contributor: boolean;
    consentStatus: string | null;
    countPhotoAlbums: number | null;
    message: string | null;
}

export async function getContributorInfo() : Promise<ContributorMeResponse>{

    const res = await fetch("http://localhost:8080/contributor/info", { 
            credentials: "include",
        });

    if(!res.ok){
        throw new Error("Unauthorized");
    }    

    return res.json();
            
}

type ReviewStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";
type ConsentFormStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";

export interface ContributorFormResponse{

    idCardFilePath : string;
    idCardReviewed : ReviewStatus | null;
    idFaceFilePath : string;
    idFaceReviewed : ReviewStatus | null;
    facefffFilePath : string;
    facefffReviewed : ReviewStatus | null;
    approvedRules : boolean;
    consentFormStatus: ConsentFormStatus | null;
    contributor : boolean;
}

export async function getContributorAgreementForm() : Promise<ContributorFormResponse>{
   const res = await fetch("http://localhost:8080/contributor/consent", { 
            credentials: "include",
        });

    if(!res.ok){
        throw new Error("Unauthorized");
    }    

    return res.json();
    
}

export async function postContributorAgreementForm(formData: FormData){
    const response = await fetch(
        "http://localhost:8080/contributor/consent",
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