import { apiFetch } from "../api-fetch";
import { handleResponse } from "../handleResponse";
import { CoverPhotoResponse, GetPhotoAlbumsResponse } from "./contributorsGetPhotoAlbums";
import { ContentStatus } from "@/types/content-status";



//######## Edit title and decription on album ############
export async function editTitleAndDescription (
    albumPublicUuid: string,
    request: EditTitleAndDescriptionRequest
): Promise <EditTitleAndDescriptionResponse>{

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/title-description`, 
        {
        method: "PATCH",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });
   
    return handleResponse(response);
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
    
    const response = await apiFetch(
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

    return handleResponse(response);
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

    const response = await apiFetch(
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

    return handleResponse(response);

}

export interface DeletePhotoRequest {
    photoPublicUuid: string
}



//######### Add photo to album #######
export async function addPhoto(
    albumPublicUuid: string,
    formData: FormData
): Promise<GetPhotoAlbumsResponse> {

    const response = await apiFetch(
        `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/photos`,
        {
            method: "POST",
            credentials: "include",
            body: formData,
        }
    );

    return handleResponse(response);

}





//######### Reorder photo in album #######
export async function reorderPhotos(
    albumPublicUuid: string,
    request: ReorderPhotoRequest
): Promise <GetPhotoAlbumsResponse> {

    const response = await apiFetch (

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

    return handleResponse(response);

}

export interface ReorderPhotoRequest {
    photoPublicUuid: string;
    targetPosition: number;
}




//######### Change status on album #######
export async function changeStatus(
    albumPublicUuid: string,
    request: EditStatusRequest
): Promise<void> {

    const response = await apiFetch (

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

    return handleResponse(response);
}

export interface EditStatusRequest {
    status: ContentStatus
}




//######### Change publishing date #######
export async function editScheduled(
    albumPublicUuid: string,
    request: EditPublishedDateRequest
): Promise<GetPhotoAlbumsResponse>{

    const response = await apiFetch (

         `${process.env.NEXT_PUBLIC_API_URL}/contributor/albums/${albumPublicUuid}/scheduled`,
         {
            method: "PATCH",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
         }
    );

    return handleResponse(response);
}

export interface EditPublishedDateRequest {
    publishedAt: string | null;
}

