import { CoverPhotoResponse } from "./contributorsPhotoAlbums";

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

//######### Edit cover photo #######
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
