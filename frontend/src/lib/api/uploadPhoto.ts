
export async function postUploadPhotos(formData : FormData): Promise<UploadPhotoContentResponse> {

    const response = await fetch(
        "http://localhost:8080/contributor/upload/photo",
        {
            method: "POST",
            credentials: "include",
            body: formData,
        }
    );

    const json = await response.json();

    if (!response.ok) {
        throw {
            message: json.message,
            errors: json.errors ?? null
        };
    }

    return json;
}

type ContentStatus = "PUBLISHED" | "DRAFT" | "SCHEDULED" | "ARCHIVED";
type ContentType = "PHOTO" | "VIDEO";

export interface CoverPhotoResponse {
    publicUuid: string;
    coverPhotoUrl: string;
}

export interface UploadPhotoContentResponse {
    albumUuid: string;
    photoAlbumName: string;
    description: string;
    publishedAt: string | null;
    contentStatus: ContentStatus;
    contentType: ContentType;
    username: string;
    photoUrls: string[];

    coverPhoto: CoverPhotoResponse | null;
}