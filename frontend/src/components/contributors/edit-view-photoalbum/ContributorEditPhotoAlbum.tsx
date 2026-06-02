"use client"

import { useEffect,useRef, useState } from "react";
import { GetPhotoAlbumsResponse } from "@/lib/api/contributors/contributorsGetPhotoAlbums";
import { EditTitleAndDescriptionRequest } from "@/lib/api/contributors/contributorEditAlbum";
import styles from "./editPhotoAlbum.module.css"

type Props = {
    data: GetPhotoAlbumsResponse;
    formData: EditTitleAndDescriptionRequest;
    setFormData: React.Dispatch<
        React.SetStateAction<EditTitleAndDescriptionRequest>
    >;
    onCancel: () => void;
    onSave: () => void;
    onCoverSelect: (photoUuid: string) => void;
    onRemovePhoto: (photoUuid: string) => void;
    onAddPhoto: (files: FileList | null) => void;
    onReorderPhoto:(photoUuid: string, targetPosition: number) => void;
}

function ContributorEditAlbum ({
    data,
    formData,
    setFormData,
    onCancel,
    onSave,
    onCoverSelect,
    onRemovePhoto,
    onAddPhoto,
    onReorderPhoto
}: Props){

    const titleTooLong = formData.photoAlbumName.length > 20;
    const descriptionTooLong = formData.description.length > 50;
    const fileInputRef = useRef<HTMLInputElement | null>(null);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSave();
    }
    
    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);

    return (
        <div className={styles.container}>

            <div className={styles.header}>
                <form onSubmit={handleSubmit}>
                    <input
                        id="title"
                        name="photoAlbumName"
                        type="text"
                        value= {formData.photoAlbumName}
                        onChange={(e) =>
                             setFormData({
                                ...formData, 
                                photoAlbumName: e.target.value
                            })
                        }

                        className={titleTooLong ? styles.inputError : ""}
                    />

                    <p className={titleTooLong ? styles.error : ""}>
                        {formData.photoAlbumName.length}/20
                    </p>

                    <textarea
                            id="description"
                            name="description"
                            value={formData.description}
                            onChange={(e) =>
                                setFormData({
                                    ...formData,
                                    description: e.target.value
                                }) 
                            }

                            className={descriptionTooLong ? styles.inputError : ""}
                        /> 

                    <p className={descriptionTooLong ? styles.error : ""}>
                        {formData.description.length}/50
                    </p> 

                    <button
                        type="submit"
                    >
                        Save
                    </button>

                    <button
                        type="button"
                        onClick={onCancel}
                    >
                        Cancel
                    </button>
                </form>

                {selectedIndex === null && (
                    <div className={styles.grid}>

                        {data.photos.map((photo, index) => (

                            <div
                                key={photo.publicUuid}
                                className={styles.card}
                                >

                                {data.coverPhoto?.publicUuid === photo.publicUuid && (
                                    <div className={styles.coverBadge}>
                                        Cover Photo
                                    </div>    
                                )}    

                                <img
                                    src={`${process.env.NEXT_PUBLIC_API_URL}/${photo.photoUrl}`}
                                    alt={data.photoAlbumName}
                                    className={`
                                        ${styles.photoEdit}
                                        ${data.coverPhoto?.publicUuid === photo.publicUuid
                                            ? styles.coverPhoto
                                            : ""
                                        }
                                    `}
                                    onClick={() => setSelectedIndex(index)}
                                />

                                <button
                                    type="button"
                                    onClick={() => onCoverSelect(photo.publicUuid)}
                                >
                                    Set cover
                                </button>

                                <button
                                    type="button"
                                    onClick={() => onRemovePhoto (photo.publicUuid)}
                                >
                                    Remove photo
                                </button>

                                <button
                                    type="button"
                                    onClick={() =>
                                        onReorderPhoto(photo.publicUuid, index - 1)
                                    }
                                    disabled={index === 0}
                                >
                                    ←
                                </button>

                                <button
                                    type="button"
                                    onClick={() =>
                                        onReorderPhoto(photo.publicUuid, index + 1)
                                    }
                                    disabled={index === data.photos.length - 1}
                                >
                                    →
                                </button>

                            </div>

                        ))}

                    </div>
                )}

                <input
                    type="file"
                    multiple
                    accept="image/*"
                    hidden
                    ref={fileInputRef}
                    onChange={(e) => onAddPhoto(e.target.files)}
                />

                <button
                    type="button"
                    className={styles.addphotobtn}
                    onClick={() => fileInputRef.current?.click()}
                >
                    Add more photos
                </button>
            
            </div>

        </div>
    );

}
export default ContributorEditAlbum;