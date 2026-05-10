
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
        throw json
    }

    return json
}

type ContentStatus = "DRAFT" | "PUBLISHED";
type ContentType = "PHOTO" | "VIDEO"

export interface UploadPhotoContentResponse {
    albumUuid: string;
    photoAlbumName: string;
    description: string;
    publishedAt: string | null;
    contentStatus: ContentStatus;
    contentType: ContentType;
    username: string;
    photoUrls: string[];
}