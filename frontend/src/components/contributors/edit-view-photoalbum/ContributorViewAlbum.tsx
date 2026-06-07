"use client"

import { useState } from "react";
import { GetPhotoAlbumsResponse } from "@/lib/api/contributors/contributorsGetPhotoAlbums";
import styles from "./editPhotoAlbum.module.css"
import { ContentStatus } from "@/types/content-status";

type Props = {
    data: GetPhotoAlbumsResponse;
    onEdit: () => void;
    onEditStatus: (status: ContentStatus) => void;
    onEditScheduled: (publishedAt: string | null) => void;
};

function ContributorViewAlbum({ 
    data, 
    onEdit, 
    onEditStatus,
    onEditScheduled 
}: Props){
 
    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);

    const getArchiveCountdown = (archivedAt: string | null) => {
        if (!archivedAt) return null;

        const archivedDate = new Date(archivedAt);
        const deleteDate = new Date(archivedDate);

        deleteDate.setDate(deleteDate.getDate() + 7);

        const now = new Date();

        const diff = deleteDate.getTime() - now.getTime();

        if (diff <= 0) return "Will be deleted soon";

        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);

        return `Deletes in ${days}d ${hours}h`;
    };

    const getPublishText = () => {
        switch (data.contentStatus) {
            case "PUBLISHED":
                return `Published: ${new Date(data.publishedAt).toLocaleString()}`;

            case "SCHEDULED":
                return `Scheduled for: ${new Date(data.publishedAt).toLocaleString()}`;

            case "DRAFT":
                return "Not published";

            case "ARCHIVED":
                return data.archivedAt
                ? `Archived on: ${new Date(data.archivedAt).toLocaleString()} * ${getArchiveCountdown(data.archivedAt)}`
                : "Archived";

            default:
                return "";
        }
    };
    
    return (
        <div className={styles.container}>

            <div className={styles.header}>
                <h1>{data.photoAlbumName}</h1>
                <p className={styles.p}>{data.description}</p>
                <p>By: {data.username}</p>
            </div>

            {selectedIndex === null && (
                <div className={styles.grid}>

                    {data.photos.map((photo, index) => (

                        <div
                            key={photo.publicUuid}
                            className={styles.card}
                        >
                            <img
                                src={`${process.env.NEXT_PUBLIC_API_URL}/${photo.photoUrl}`}
                                alt={data.photoAlbumName}
                                className={styles.photo}
                                onClick={() => setSelectedIndex(index)}
                            />
                        </div>

                    ))}

                </div>
            )}

            <p>{getPublishText()}</p>

            {selectedIndex !== null && (
    <div className={styles.lightbox}>
        
        <img
            src={`${process.env.NEXT_PUBLIC_API_URL}/${data.photos[selectedIndex].photoUrl}`}
            alt="full view"
        />

        <div className={styles.lightboxControls}>
            <button
                onClick={() =>
                    setSelectedIndex((prev) =>
                        prev !== null && prev > 0 ? prev - 1 : prev
                    )
                }
            >
                left
            </button>

            <button onClick={() => setSelectedIndex(null)}>
                X
            </button>

            <button
                onClick={() =>
                    setSelectedIndex((prev) =>
                        prev !== null && prev < data.photos.length - 1
                            ? prev + 1
                            : prev
                    )
                }
            >
                right
            </button>
        </div>

    </div>
)}

            <div className={styles.statusbar}>

                <button
                    className={`${styles.editBtn} ${
                        data.contentStatus === "DRAFT" ? styles.activeStatus : ""
                    }`}
                    disabled={data.contentStatus === "DRAFT"}
                    onClick={() => onEditScheduled(null)}
                >
                    Draft
                </button>

                <button
                    type="button"
                    className={`${styles.editBtn} ${
                        data.contentStatus === "PUBLISHED" ? styles.activeStatus : ""
                    }`}
                    disabled={data.contentStatus === "PUBLISHED"}
                    onClick={() => {
                        onEditScheduled(new Date().toISOString());
                    }}
                >
                    Publish now
                </button>

                <label
                    className={`${styles.editBtn} ${
                        data.contentStatus === "SCHEDULED" ? styles.activeStatus : ""
                    }`}
                    >
                    Scedule:
                    <input
                        type="datetime-local"
                        
                        value={data.publishedAt ? data.publishedAt.slice(0, 16) : ""}
                        disabled={false}
                        onChange={(e) => onEditScheduled(
                            e.target.value
                            ? new Date(e.target.value).toISOString() 
                            : null)
                        }
                    />
                </label>
                
                
                <button
                    type="button"
                    className={`${styles.editBtn} ${
                        data.contentStatus === "ARCHIVED" ? styles.activeStatus : ""
                    }`}
                    disabled={data.contentStatus === "ARCHIVED"}
                    onClick={() => onEditStatus("ARCHIVED")}
                >
                    Archive
                </button>

            </div>

            <button
                type="button"
                className={styles.editBtn}
                onClick ={onEdit}
            >
                Edit
            </button>
        </div>
    );

}

export default ContributorViewAlbum;