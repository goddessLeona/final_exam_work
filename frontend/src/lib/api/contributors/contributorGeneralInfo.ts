
import { ContentType } from "../types/content-types";

export async function getContributorInfo() : Promise<ContributorAlbumStatsResponse>{

    const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/general/info`, { 
            credentials: "include",
        });

    if(!res.ok){
        throw new Error("Unauthorized");
    }    

    return res.json();
            
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