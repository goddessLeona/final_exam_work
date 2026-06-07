
import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";
import { ContentType } from "@/types/content-type";
import { ContentStatus } from "@/types/content-status";

//####### Get all cover photos from all albums that are uploaded #######
export async function getPhotoAlbums(
    status: ContentStatus,
    page: number = 0,
    size: number = 12
) : Promise<PageResponse<ContributorPhotoAlbumResponse>> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/cover-photo?status=${status}&page=${page}&size=${size}`,
         {
        credentials: "include",
    });

    return handleResponse(response);

}

export interface CoverPhotoResponse {
    publicUuid: string;
    coverPhotoUrl: string;
}

export interface ContributorPhotoAlbumResponse {
    publicUuid: string;
    photoAlbumName: string;
    publishedAt: string | null;
    contentType: ContentType;
    contentStatus: ContentStatus;
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
export async function contributorGetAlbums(publicUuid: string): Promise<GetPhotoAlbumsResponse> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${publicUuid}`,
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
    archivedAt: string;

    albumTags: AlbumTagResponse [];
    photos: PhotoResponse [];
    coverPhoto: CoverPhotoResponse | null;
    contentStatus: ContentStatus;
}

