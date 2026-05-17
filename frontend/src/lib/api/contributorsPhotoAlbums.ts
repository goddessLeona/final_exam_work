
export type ContentStatus = "PUBLISHED" | "DRAFT" | "SCHEDULED" | "ARCHIVED";
type ContentType = "PHOTO" | "VIDEO";

export interface CoverPhotoResponse {
    publicUuid: string;
    coverPhotoUrl: string;
}

export interface ContributorPhotoAlbumResponse {
    albumUuid: string;
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
        `http://localhost:8080/contributor/albums/list?status=${status}&page=${page}&size=${size}`,
         {
        credentials: "include",
    });

    if(!response.ok) {
        throw new Error("Failed to fetch albums");
    }

    return response.json();

}