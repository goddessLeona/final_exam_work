
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