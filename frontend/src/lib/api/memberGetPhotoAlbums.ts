
//####### Get all cover photos from all albums that are published #######

export type ContentType = "PHOTO" | "VIDEO";

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

export async function memberGetCoverPhotos(
    contentType: ContentType,
    page: number = 0,
    size: number = 12
) : Promise<PageResponse<GetCoverPhotoAlbumsResponse>> {

    const response = await fetch(
        `http://localhost:8080/member/albums?contentType=${contentType}&page=${page}&size=${size}`,
         {
        credentials: "include",
    });

    if(!response.ok) {
        throw new Error("Failed to fetch cover albums");
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
}

export async function memberGetAlbums(publicUuid: string): Promise<GetPhotoAlbumsResponse> {

    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/member/albums/${publicUuid}`,
         {
        credentials: "include",
    });

    if(!response.ok) {
        throw new Error("Failed to fetch album");
    }

    return response.json();
}