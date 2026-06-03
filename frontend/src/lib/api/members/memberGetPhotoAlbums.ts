
import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";
import { ContentType } from "@/types/content-type";
import { ContentStatus } from "@/types/content-status";


//####### Get all cover photos from all albums that are published 
export async function memberGetCoverPhotos(
    contentType: ContentType,
    page: number = 0,
    size: number = 12
) : Promise<PageResponse<GetCoverPhotoAlbumsResponse>> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/member/albums?contentType=${contentType}&page=${page}&size=${size}`,
         {
        credentials: "include",
    });

   return handleResponse(response);

}


export interface CoverPhotoResponse {
    publicUuid: string;
    coverPhotoUrl: string;
}

export interface GetCoverPhotoAlbumsResponse {
    publicUuid: string;
    photoAlbumName: string;
    coverPhoto: CoverPhotoResponse | null;
}

export interface PageResponse<T> {
    content: T[];
    totalPages: number;
    totalEliments: number;
    size: number;
    number: number;
}



//######## GET to album from cover photo ############
export async function memberGetAlbums(publicUuid: string): Promise<GetPhotoAlbumsResponse> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/member/albums/${publicUuid}`,
         {
        credentials: "include",
    });

    return handleResponse(response);
}

export interface AlbumTagResponse {
    publicUuid: string;
    nameTag: string;
}

export interface PhotoResponse  {
    publicUuid: string;
    photoUrl: string;
}

export interface GetPhotoAlbumsResponse {
    publicUuid: string;
    photoAlbumName: string;
    description: string;
    username: string;
    publishedAt: string;
    archivedAt: string | null;
    
    albumTags: AlbumTagResponse [];
    photos: PhotoResponse [];
    coverPhoto: CoverPhotoResponse;
    contentStatus: ContentStatus;
}

