

type ContributorStatus = "NOT_APPLIED" | "PENDING" | "APPROVED" | "REJECTED" | "TEMP_BANNED" | "BANNED";

export interface WelcomeResponse {
    status: ContributorStatus
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


type ReviewStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";
type ConsentFormStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";

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