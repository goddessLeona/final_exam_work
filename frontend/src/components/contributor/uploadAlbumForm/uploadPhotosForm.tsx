"use client"

import {useRef, useState} from "react"
import { postUploadPhotos } from "@/lib/api/uploadPhoto"
import {UploadPhotoContentResponse} from "@/lib/api/uploadPhoto"
import styles from "./uploadPhotosForm.module.css"

const initialFormState = {
    photoAlbumName: "",
    description: "",
    photos: [] as File[],
}

function UploadPhotosForm() {

    const [formData, setFormData] = useState(initialFormState);
    const [serverData, setServerData] = useState<UploadPhotoContentResponse | null>(null);
    const [error, setError] = useState<Record<string, string>>({});
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const fileInputRef = useRef<HTMLInputElement | null>(null);

    const [selectedPhotos, setSelectedPhotos] = useState<number[] >([]);

    const togglePhotoSelection = (index: number) => {
        setSelectedPhotos((prev) =>
            prev.includes(index)
                ? prev.filter((i) => i !== index)
                :[...prev, index]
        );
    };

    const removeSelectedPhotos = () => {
        setFormData({
            ...formData,
            photos: formData.photos.filter(
                (_,index) => !selectedPhotos.includes(index)
            )
        });
        setSelectedPhotos([]);
    }

    const movePhoto = (from: number, to: number) => {

        if (to < 0 || to >= formData.photos.length) return;

        const updatedPhotos = [...formData.photos];
        const [movedPhoto] = updatedPhotos.splice(from, 1);

        updatedPhotos.splice(to, 0, movedPhoto);

        setFormData({
            ...formData,
            photos: updatedPhotos
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setError({});
    setSuccess("");

    const data = new FormData();
    data.append("photoAlbumName", formData.photoAlbumName);
    data.append("description", formData.description);
    data.append("contentType", "PHOTO")

    for (const photo of formData.photos) {
        data.append("photos", photo);
    }

    try {
        const response = await postUploadPhotos(data);
        setServerData(response);
        setSuccess("Submitted successfully!");

        //reset form
        setFormData(initialFormState);
        
        if (fileInputRef.current) {
            fileInputRef.current.value = "";
        }

        setTimeout(() => {
            setSuccess("");
        },3000);
        
    }catch (err:any) {
        if (err.errors) {
            setError(err.errors);
        }else {
            setError({ general: err.message || "Error submitting photos" })
        }
    }finally {
        setLoading(false);
    }
    };

    return (
        <main className={styles.container}>
            <form onSubmit={handleSubmit}>
                <div className={styles.formBox}>

                    <h3>Upload photos</h3>

                    <div className={styles.field}>
                        <label htmlFor="title">Title</label>
                        <input
                            id="title"
                            name="photoAlbumName"
                            type="text"
                            value= {formData.photoAlbumName}
                            onChange={(e) =>
                                setFormData({...formData, photoAlbumName: e.target.value})
                            }
                        />
                    </div>

                    <div className={styles.field}>
                        <label htmlFor="description">Description</label>

                        <textarea
                            id="description"
                            name="description"
                            value={formData.description}
                            onChange={(e) => setFormData({
                                ...formData,
                                description: e.target.value
                                })
                            }
                        />    
                    </div>

                    {/**previewGrid + add and remove photos */}
                    <div className={styles.field}>
                        <label htmlFor="photos">Photos</label>

                        <div className={styles.previewGrid}>
                            {formData.photos.map((photo,index) => (
                                <div key={index} className={styles.previewCard}>
                                    <img
                                        src={URL.createObjectURL(photo)}
                                        alt={`Preview ${index}`}
                                        className={
                                            selectedPhotos.includes(index)
                                                ? styles.selectedImage
                                                : styles.previewImage
                                        }
                                        onClick={() => togglePhotoSelection(index)}
                                    />

                                    <div className={styles.moveButtons}>
                                        <button
                                            type="button"
                                            onClick={() => movePhoto(index, index -1)}
                                        >left</button>

                                        <button
                                            type="button"
                                            onClick={() => movePhoto(index, index +1)}
                                        >right</button>
                                    </div>
                                </div>    
                            ))}
                                <input
                                    ref={fileInputRef}
                                    type="file"
                                    multiple
                                    accept="image/*"
                                    hidden
                                    onChange={(e) => setFormData ({
                                        ...formData,
                                        photos: [
                                            ...formData.photos,
                                            ...Array.from(e.target.files || [])
                                        ] 
                                        })
                                    }
                                />  

                                
                            
                        </div>

                            <div className={styles.uploadOptions}>
                                    <button
                                        type="button"
                                        className={styles.btn}
                                        onClick={() => fileInputRef.current?.click()}
                                    > Upload Photos</button>  

                                    <button
                                        type="button"
                                        className={styles.btn}
                                        onClick={removeSelectedPhotos}
                                    > Remove selected </button>  

                                </div>

                            <p>{formData.photos.length} photos</p>
                            <p>You have to minimum upload 7 photos to be able to post</p>
                    </div>

                    <div>
                        <button
                            className={styles.btn}
                            type="submit"
                            disabled={
                                loading ||
                                !formData.photoAlbumName ||
                                !formData.description ||
                                formData.photos.length < 7
                            }
                        >Post</button>   
                    </div>

                    {success && <p>{success}</p>} 
                    {error.general && (<p className={styles.error}>{error.general}</p>
                )}

                </div>
                
            </form>
        </main>
    );
}
export default UploadPhotosForm;