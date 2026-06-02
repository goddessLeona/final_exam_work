
import { apiFetch } from "../api-fetch";
import { ContentType } from "@/types/content-type";
import { ContentStatus } from "@/types/content-status";

export async function postUploadPhotos(
    formData : FormData
    ): Promise<UploadPhotoContentResponse> {

        const response = await apiFetch(
            `${process.env.NEXT_PUBLIC_API_URL}/contributor/upload/photo`,
            {
                method: "POST",
                credentials: "include",
                body: formData,
            }
        );

        let json = null;

        try {
            json = await response.json();
        } catch {
            json = null;
        }

        if (!response.ok) {
            throw {
                message: json?.message || "Upload failed",
                errors: json?.errors ?? null,
            };
        }
        
    return json;
}
    

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