
//######## Edit title and decription on album ############

export interface EditTitleAndDescriptionRequest {
    photoAlbumName: string;
    description: string;
}

export interface EditTitleAndDescriptionResponse {
    photoAlbumName: string;
    description: string;
}

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