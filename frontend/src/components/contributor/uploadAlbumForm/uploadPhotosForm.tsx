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

                    <div className={styles.field}>
                        <div className={styles.previewGrid}>
                            {formData.photos.map((photo,index) => (
                                <img
                                    key={index}
                                    src={URL.createObjectURL(photo)}
                                    alt={`Preview ${index}`}
                                    className={styles.previewImage}
                                />
                            ))}
                        </div>
                    </div>

                    <div className={styles.field}>
                        <label htmlFor="photos">Photos</label>

                        <input
                            ref={fileInputRef}
                            id="photos"
                            type="file"
                            multiple
                            accept="image/*"
                            onChange={(e) => setFormData ({
                                ...formData,
                                photos: Array.from(e.target.files || [])
                                })
                            }
                        />  
                        <p>{formData.photos.length} photos selected</p>
                        <p>You have to minimum upload 7 photos to be able to post</p>
                    </div>        

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

                    {success && <p>{success}</p>} 
                    {error.general && (<p className={styles.error}>{error.general}</p>
                )}

                </div>
                
            </form>
        </main>
    );
}
export default UploadPhotosForm;