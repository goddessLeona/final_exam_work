"use client"

import { useState } from "react";
import { GetPhotoAlbumsResponse } from "@/lib/api/contributorsPhotoAlbums";
import styles from "./editPhotoAlbum.module.css"

type Props = {
    data: GetPhotoAlbumsResponse;
    onEdit: () => void;
};

function ContributorViewAlbum({ data, onEdit}: Props){
 
    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);
    
    return (
        <div className={styles.container}>

            <div className={styles.header}>
                <h1>{data.photoAlbumName}</h1>
                <p>{data.description}</p>
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

            <p>Published: {data.publishedAt}</p>

            {selectedIndex !== null && (
                <div className={styles.lightbox}>
                    
                    <img
                        src={`${process.env.NEXT_PUBLIC_API_URL}/${data.photos[selectedIndex].photoUrl}`}
                        alt="full view"
                    />

                    <button
                        onClick={() =>
                            setSelectedIndex((prev) =>
                                prev !== null && prev > 0 ? prev - 1 : prev
                            )
                        }
                    >
                        left
                    </button>

                    <button onClick={() => setSelectedIndex(null)}>X</button>

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
            )}

            <button
                onClick ={onEdit}
            >
                Edit
            </button>
        </div>
    );

}

export default ContributorViewAlbum;