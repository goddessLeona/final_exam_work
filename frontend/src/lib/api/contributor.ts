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


export async function postContributorAgreementForm(formData: FormData){
    const response = await fetch(
        "http://localhost:8080/contributor/consent",
        {
            method: "POST",
            credentials: "include",
            body: formData,
        }
    );    

        if (!response.ok) {
        const errorData = await response.json();
        throw errorData;
        }

    return response.json(); 
    
}

export interface ContributorFormResponse{

    idCardFilePath : string;
    idCardReviewed : boolean | null;
    idFaceFilePath : string;
    idFaceReviewed : boolean | null;
    facefffFilePath : string;
    facefffReviewed : boolean | null;
    approvedRules : boolean;
    consentStatus: string | null;
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