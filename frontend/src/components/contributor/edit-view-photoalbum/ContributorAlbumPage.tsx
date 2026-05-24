"use client"

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { GetPhotoAlbumsResponse, contributorGetAlbums } from "@/lib/api/contributorsPhotoAlbums";
import { EditTitleAndDescriptionRequest } from "@/lib/api/editAlbum";
import { editTitleAndDescription } from "@/lib/api/editAlbum";
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
            setError("Failed to update album");
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
        />
    ) : (
        <ContributorViewAlbum
            data={data}
            onEdit={() => setIsEditing(true)} 
        />
    )

}

export default ContributorContentPage;