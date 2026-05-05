
type consentFormStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";
type ReviewStatus = "NOT_SUBMITTED" | "PENDING" | "APPROVED" | "REJECTED";

export interface AdminConsentFormItem {

    username: string;

    documentsPending: number;
    documentsApproved: number;
    documentsRejected: number;

    consentFormStatus: consentFormStatus;
    consentFormId: string;

}

export interface DashboardSection {
    total: number;
    latest: AdminConsentFormItem[];
}

export interface AdminDashboardResponse {
    total: number;

    pending: DashboardSection;
    approved: DashboardSection;
    rejected: DashboardSection;
    notSubmitted: DashboardSection;
}

export async function getAdminDashboard () : Promise<AdminDashboardResponse> {

    const res = await fetch("http://localhost:8080/admin/dashboard", { 
            credentials: "include",
        });

        if(!res.ok){
        throw new Error("Failed to fetch dashboard");
    }    

    return res.json();

}

export interface ConsentFormDataResponse {

    username: string;
    documentIdCard: DocumentDto;
    documentIdCardFace : DocumentDto;
    documentFaceFFF : DocumentDto;
    approvedRules : boolean;
    consentFormStatus : consentFormStatus;
    consentFormId : string

}

export interface DocumentDto {

    status : ReviewStatus;
}

export async function getConsentFormData (id: string) : Promise <ConsentFormDataResponse> {

    const res = await fetch(`http://localhost:8080/admin/consent/${id}`, {
        credentials: "include",
    })

    if(!res.ok) {
        throw new Error("Failed to fetch consent data");
    }

    return res.json();
}


export interface AdminConsentReviewRequest {

    idCardStatus? : ReviewStatus;
    idFaceStatus? : ReviewStatus;
    facefffStatus? : ReviewStatus;
    idCardMessage? : string;
    idFaceMessage? : string;
    facefffMessage? : string;
}


export async function adminConsentFormResponse (
    id: string,
    request : AdminConsentReviewRequest
) : Promise <void> {

   
    const res = await fetch(`http://localhost:8080/admin/consent/${id}/review`, 
        {
        method: "PATCH",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    if(!res.ok) {
        throw new Error("Failed to update consent form")
    }

}