import { CoverPhotoResponse, GetPhotoAlbumsResponse } from "./contributorsPhotoAlbums";

//######## Edit title and decription on album ############
export async function editTitleAndDescription (
    albumPublicUuid: string,
    request: EditTitleAndDescriptionRequest
): Promise <EditTitleAndDescriptionResponse>{

    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/title-description`, 
        {
        method: "PATCH",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });
   
    if(!res.ok) {
        throw new Error("Failed to update title or decription")
    }

    return await res.json();
}

export interface EditTitleAndDescriptionRequest {
    photoAlbumName: string;
    description: string;
}

export interface EditTitleAndDescriptionResponse {
    photoAlbumName: string;
    description: string;
}

//######### Change cover photo #######
export async function editCoverPhoto(
    albumPublicUuid: string,
    request: editCoverPhotoRequest
): Promise<editCoverPhotoResponse> {
    
    const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/cover-photo`,
        {
            method: "PATCH",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        }
    );

    const json = await res.json();

    if (!res.ok) {
        throw {
            message: json.message,
            errors: json.errors
        };
    }

    return json;
}

export interface editCoverPhotoRequest {

    coverPhotoPublicUuid: string

}

export interface editCoverPhotoResponse {
    
    coverPhoto: CoverPhotoResponse
}

//######### Delete photo from album #######
export async function deletePhoto (
    albumPublicUuid: string,
    request: DeletePhotoRequest
): Promise<void> {

    const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/photos`,
        {
            method: "DELETE",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        }
    );

    if (!res.ok) {

        const json = await res.json();

        throw {
            message: json.message,
            errors: json.errors
        };
    }

}

export interface DeletePhotoRequest {

    photoPublicUuid: string
}

//######### Add photo to album #######
export async function addPhoto(
    albumPublicUuid: string,
    formData: FormData
): Promise<GetPhotoAlbumsResponse> {
    const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/photos`,
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

//######### Reorder photo in album #######
export async function reorderPhotos(
    albumPublicUuid: string,
    request: ReorderPhotoRequest
): Promise <GetPhotoAlbumsResponse> {

    const response = await fetch (

         `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/reorder`,
         {
            method: "PATCH",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
         }
    );

    const json = await response.json();

    if (!response.ok) {
        throw {
            message: json.message,
            errors: json.errors
        };
    }

    return json;

}

export interface ReorderPhotoRequest {
    photoPublicUuid: string;
    targetPosition: number;
}

//######### Change status on album #######
import type { ContentStatus } from "./types/content-status";

export async function changeStatus(
    albumPublicUuid: string,
    request: EditStatusRequest
): Promise<void> {

    const response = await fetch (

        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/status`,
        {
            method: "PATCH",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        }
    );

    if (!response.ok) {

        const json = await response.json();

        throw {
            message: json.message,
            errors: json.errors
        };
    }
}

export interface EditStatusRequest {
    status: ContentStatus
}

