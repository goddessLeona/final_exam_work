import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";
import { ContentType } from "@/types/content-type";

export async function getContributorInfo() : Promise<ContributorAlbumStatsResponse>{

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/general/info`, { 
            credentials: "include",
    });

    return handleResponse(response);       
}

export interface ContributorAlbumStatsResponse {
    username: string;
    yearSignedUp: number;
    content: ContentStatsResponse[];
}

export interface ContentStatsResponse {
    type: ContentType;

    total: number;
    published: number;
    draft: number;
    archived: number;
    scheduled: number;
}


