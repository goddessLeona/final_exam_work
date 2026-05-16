"use client"

import {useRef, useState} from "react"
import { postUploadPhotos } from "@/lib/api/uploadPhoto"
import {UploadPhotoContentResponse} from "@/lib/api/uploadPhoto"
import styles from "./uploadPhotosForm.module.css"
import { ChevronLeft, ChevronRight } from "lucide-react";

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
    const [scheduledDate, setScheduledDate] = useState("");
    const [coverPhotoIndex, setCoverPhotoIndex] = useState<number | null> (0);

    const [submitAction, setSubmitAction] = useState<
        "DRAFT" |
        "PUBLISH" |
        "SCHEDULE"
    >("DRAFT");
    

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

    const selectedCoverPhoto = (index: number) => {
        setCoverPhotoIndex(index);
    }

    const handleSubmit = async (e: React.FormEvent <HTMLFormElement>, 
        action: "DRAFT" | "PUBLISH" | "SCHEDULE"
    ) => {
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

    if (action === "DRAFT") {
        data.append("contentStatus", "DRAFT")
    }

    if (action === "PUBLISH") {
        data.append(
            "publishedAt",
            new Date().toISOString()
        );
    }

    if (action === "SCHEDULE") {
       
        data.append(
            "publishedAt",
            new Date(scheduledDate).toISOString()
        );
    }

    if (coverPhotoIndex !== null) {
        data.append(
            "coverphotoIndex",
            coverPhotoIndex.toString()
        );
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
            <form>
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

                    {/**previewGrid + add photos */}
                    <div className={styles.field}>
                        <label htmlFor="photos">Photos</label>

                        <div className={styles.previewGrid}>
                            {formData.photos.map((photo,index) => (
                                <div key={index} className={styles.previewCard}>

                                    {coverPhotoIndex === index && (
                                        <div className={styles.coverBadge}>
                                            Cover Photo
                                        </div>
                                    )}

                                    <img
                                        src={URL.createObjectURL(photo)}
                                        alt={`Preview ${index}`}
                                        className={ `
                                            ${styles.previewImage}
                                            ${selectedPhotos.includes(index)
                                                ? styles.selectedImage
                                                : ""
                                            }

                                            ${coverPhotoIndex === index 
                                                ? styles.coverImage
                                                : ""                                            }
                                            }

                                        `}
                                        onClick={() => togglePhotoSelection(index)}
                                    />

                                    <div className={styles.moveButtons}>

                                        <button
                                            type="button"
                                            className={styles.cover}
                                            onClick={() => selectedCoverPhoto(index)}
                                        >
                                            Set Cover
                                        </button> 

                                        <div>
                                            <button
                                                type="button"
                                                onClick={() => movePhoto(index, index -1)}
                                                className={styles.arrowBtn}
                                            ><ChevronLeft size={18}/></button>

                                            <button
                                                type="button"
                                                onClick={() => movePhoto(index, index +1)}
                                                className={styles.arrowBtn}
                                            ><ChevronRight size={18}/></button>
                                        </div>
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
                            <p>You have to upload between 7 - 30 photos to be able to post</p>
                    </div>

                    <div className={styles.actionButtons}>
                        <button
                            className={styles.btn}
                            type="button"
                            disabled={
                                loading ||
                                !formData.photoAlbumName ||
                                !formData.description ||
                                formData.photos.length < 7
                            }
                            onClick={(e) => handleSubmit(e as any, "DRAFT")}
                        >Save Draft</button> 

                        <button
                            className={styles.btn}
                            type="button"
                            disabled={
                                loading ||
                                !formData.photoAlbumName ||
                                !formData.description ||
                                formData.photos.length < 7
                            }
                            onClick={(e) => handleSubmit(e as any,"PUBLISH")}
                            > Publish Now
                        </button>
 
                        {submitAction === "SCHEDULE" && (
                            <div className={styles.field}>
                                <label htmlFor="scheduledDate">
                                    Schedule publish date
                                </label>

                                <input
                                    id="scheduledDate"
                                    type="datetime-local"
                                    value={scheduledDate}
                                    onChange={(e) =>
                                        setScheduledDate(e.target.value)
                                    }
                                />
                                <button
                                type="button"
                                className={styles.btn}
                                disabled={
                                    loading ||
                                    !formData.photoAlbumName ||
                                    !formData.description ||
                                    formData.photos.length < 7
                                }   
                                onClick={(e) => handleSubmit (e as any, "SCHEDULE")}
                                > Confirm Scedule
                                </button>
                            </div>
                        )}
  
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