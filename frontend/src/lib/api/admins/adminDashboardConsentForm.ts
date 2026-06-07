import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";
import { ConsentFormStatus } from "@/types/consent-form-status";
import { ReviewStatus } from "@/types/reviewStatus";

export interface AdminConsentFormItem {

    username: string;

    documentsPending: number;
    documentsApproved: number;
    documentsRejected: number;

    consentFormStatus: ConsentFormStatus;
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

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/admin/dashboard`, { 
            credentials: "include",
    });


    return handleResponse(response);
}

export interface ConsentFormDataResponse {

    username: string;
    documentIdCard: DocumentDto;
    documentIdCardFace : DocumentDto;
    documentFaceFFF : DocumentDto;
    approvedRules : boolean;
    consentFormStatus : ConsentFormStatus;
    consentFormId : string

}

export interface DocumentDto {

    status : ReviewStatus;
}

export async function getConsentFormData (id: string) : Promise <ConsentFormDataResponse> {

    const response = await apiFetch(
        
        `${process.env.NEXT_PUBLIC_API_URL}/admin/consent/${id}`, {
        credentials: "include",
    })

    return handleResponse(response);
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

   
    const response = await apiFetch(
        
        `${process.env.NEXT_PUBLIC_API_URL}/admin/consent/${id}/review`, {

        method: "PATCH",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    return handleResponse(response);

}