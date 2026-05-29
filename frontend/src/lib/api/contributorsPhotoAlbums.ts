
//####### Get all cover photos from all albums that are uploaded #######

import type { ContentStatus } from "./types/content-status";
type ContentType = "PHOTO" | "VIDEO";

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

export async function getPhotoAlbums(
    status: ContentStatus,
    page: number = 0,
    size: number = 12
) : Promise<PageResponse<ContributorPhotoAlbumResponse>> {

    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/cover-photo?status=${status}&page=${page}&size=${size}`,
         {
        credentials: "include",
    });

    if(!response.ok) {
        throw new Error("Failed to fetch albums");
    }

    return response.json();

}

//######## GET to album from cover photo ############

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

    albumTags: AlbumTagResponse [];
    photos: PhotoResponse [];
    coverPhoto: CoverPhotoResponse | null;
    contentStatus: ContentStatus;
}

export async function contributorGetAlbums(publicUuid: string): Promise<GetPhotoAlbumsResponse> {

    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${publicUuid}`,
         {
        credentials: "include",
    });

    if(!response.ok) {
        throw new Error("Failed to fetch album");
    }

    return response.json();
}