"use client"

import { useEffect, useState } from "react";
import { GetPhotoAlbumsResponse } from "@/lib/api/contributorsPhotoAlbums";
import { EditTitleAndDescriptionRequest } from "@/lib/api/editAlbum";
import styles from "./editPhotoAlbum.module.css"

type Props = {
    data: GetPhotoAlbumsResponse;
    formData: EditTitleAndDescriptionRequest;
    setFormData: React.Dispatch<
        React.SetStateAction<EditTitleAndDescriptionRequest>
    >;
    onCancel: () => void;
    onSave: () => void;
}

function ContributorEditAlbum ({
    data,
    formData,
    setFormData,
    onCancel,
    onSave
}: Props){

    const titleTooLong = formData.photoAlbumName.length > 20;
    const descriptionTooLong = formData.description.length > 50;

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSave();
    }

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
            
            </div>

        </div>
    );

}
export default ContributorEditAlbum;