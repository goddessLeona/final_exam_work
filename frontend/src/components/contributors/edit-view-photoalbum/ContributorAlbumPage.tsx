"use client"

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import { useParams } from "next/navigation";
import { GetPhotoAlbumsResponse, contributorGetAlbums } from "@/lib/api/contributors/contributorsGetPhotoAlbums";
import { EditTitleAndDescriptionRequest } from "@/lib/api/contributors/contributorEditAlbum";
import { editTitleAndDescription } from "@/lib/api/contributors/contributorEditAlbum";
import { editCoverPhoto, deletePhoto,addPhoto, reorderPhotos, changeStatus, editScheduled } from "@/lib/api/contributors/contributorEditAlbum";
import { ContentStatus } from "@/types/content-status";
import ContributorViewAlbum from "./ContributorViewAlbum";
import ContributorEditAlbum from "./ContributorEditPhotoAlbum";

function ContributorContentPage (){

    const params = useParams();
    const albumUuid = params.albumUuid as string;
    const [data, setData] = useState<GetPhotoAlbumsResponse | null>(null);
    const [formData, setFormData] = useState<EditTitleAndDescriptionRequest>({
        photoAlbumName: "",
        description: "",
    });
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {

        async function loadAlbum() {
            try {
                const response =
                await contributorGetAlbums(albumUuid);
                setData(response);

            }catch (err) {

                if (handleAuthError(err)) return;
                setError("Failed to load album");

            }finally {
                setLoading(false);
            }
        }

        if (albumUuid) {
            loadAlbum();
        }

    }, [albumUuid]);

    useEffect(() => {
        if (data) {
            setFormData({
                photoAlbumName: data.photoAlbumName,
                description: data.description,
            });
        }
    }, [data]);

    // save new title and description 
    async function handleSave() {
        try {
            await editTitleAndDescription(albumUuid, formData);

            setData({
                ...data!,
                photoAlbumName: formData.photoAlbumName,
                description: formData.description,
            });

            setIsEditing(false);

        } catch (err) {
            if (handleAuthError(err)) return;
            setError("Failed to update album");
        }
    }

    // change cover photo
    const handleSetCover = async (photoUuid: string) => {
        try {
            const updated = await editCoverPhoto(albumUuid, {
                coverPhotoPublicUuid: photoUuid
            });

            setData((prev) => {
                if (!prev) return prev;

                return {
                    ...prev,
                    coverPhoto: updated.coverPhoto
                };
            });

        } catch (err: any) {

            if (handleAuthError(err)) return;
            setError(err.message || "Failed to update cover photo");
        }
    };

    // delete photo from album
    const handleRemovePhoto = async (photoUuid: string) => {

        try {

            await deletePhoto(albumUuid, {
                photoPublicUuid: photoUuid
            });

            setData((prev) => {

                if (!prev) return prev;

                return {
                    ...prev,
                    photos: prev.photos.filter(
                        (photo) => photo.publicUuid !== photoUuid
                    )
                };
            });

        } catch (err: any) {

            if (handleAuthError(err)) return;
            setError(err.message || "Failed to delete photo");
        }
    };

    //add new photo or photos to album
    async function handleAddPhoto(files: FileList | null) {

        if (!files) return;

        try {
            const data = new FormData();

            for (const file of Array.from(files)) {
                data.append("photos", file);
            }

            const updatedAlbum = await addPhoto(albumUuid, data);

            setData(updatedAlbum);

        }catch (err: any) {
            if (handleAuthError(err)) return;
            setError(err.message || "Failed to update album")
        }
    }

    //Reorder photos in album
    async function handleReorderPhoto(
        photoUuid: string,
        targetPosition: number
    ) {
        try {

            const updatedAlbum = await reorderPhotos(albumUuid, {
                photoPublicUuid: photoUuid,
                targetPosition
            });

            setData(updatedAlbum);

        } catch (err: any) {

            if (handleAuthError(err)) return;
            setError(err.message || "Failed to reorder photos");
        }
    }

    //Change the status on a album
    async function handleStatus(
        status: ContentStatus
    ) {
        try {

            await changeStatus(albumUuid, {
                status
            });

            setData(prev => 
                prev
                ? {
                    ...prev,
                    contentStatus: status
                }
                : prev
            );

        } catch (err: any) {

            if (handleAuthError(err)) return;
            setError(err.message || "Failed to change status on album");
        }
    }

    //Edit or add scheduled date on a album
    async function handleScheduled(publishedAt: string | null){

        try{

            const updatedAlbum = await editScheduled(albumUuid, {
                publishedAt
            });

            setData(updatedAlbum);

        }catch (err: any) {
            if (handleAuthError(err)) return;
            setError(err.message || "Failed to change scheduled date on album");
        }
    }


    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>{error}</p>
    }

    if (!data) {
        return <p>Album not found</p>;
    }

    return isEditing ? (
        <ContributorEditAlbum
            data={data}
            formData={formData}
            setFormData={setFormData}
            onCancel={() => setIsEditing(false)}
            onSave={handleSave}
            onCoverSelect={handleSetCover}
            onRemovePhoto={handleRemovePhoto}
            onAddPhoto={handleAddPhoto}
            onReorderPhoto={handleReorderPhoto}
        />
    ) : (
        <ContributorViewAlbum
            data={data}
            onEdit={() => setIsEditing(true)} 
            onEditStatus={handleStatus}
            onEditScheduled={handleScheduled}
        />
    )

}

export default ContributorContentPage;