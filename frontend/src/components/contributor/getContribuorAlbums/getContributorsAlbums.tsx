"use client";

import { useEffect, useState } from "react";
import { 
            getPhotoAlbums,
            ContributorPhotoAlbumResponse,
            PageResponse,
        } from "@/lib/api/contributorsPhotoAlbums";

import type {ContentStatus} from "@/lib/api/contributorsPhotoAlbums"; 

import styles from "./getContributorsAlbums.module.css"

function ContributorAlbums() {

    const [data, setData] = useState<PageResponse<ContributorPhotoAlbumResponse> | null> (null);
    const [error, setError] = useState("");
    const [status, setStatus] =useState<ContentStatus>("PUBLISHED");

    useEffect(() => {
        async function loadAlbums() {

            try {
                const response = await getPhotoAlbums(status);
                setData(response);

            }catch (err) {
                console.error(err);
                setError("Failed to load albums");
            }
            
        }
        loadAlbums()
    }, [status]);

    return (
    <div className={styles.container}>
        <div className={styles.tabs}>

            <button
                type="button"
                className={styles.btn}
                onClick={() => setStatus("DRAFT")}
            >
                Draft
            </button>

            <button
                type="button"
                className={styles.btn}
                onClick={() => setStatus("PUBLISHED")}
            >
                Published
            </button>

            <button
                type="button"
                className={styles.btn}
                onClick={() => setStatus("SCHEDULED")}
            >
                Scheduled
            </button>

            <button
                type="button"
                className={styles.btn}
                onClick={() => setStatus("ARCHIVED")}
            >
                Archived
            </button>

        </div>
        {error && <p>{error}</p>}

        <div className={styles.grid}>
            {data?.content.map((album) => (
                <div
                key={album.albumUuid}
                className={styles.card}
                >
                    <h3>{album.photoAlbumName}</h3>
                    {album.coverPhoto && (
                        <img
                            src={`http://localhost:8080/${album.coverPhoto.coverPhotoUrl}`}
                            width={100}
                            alt={album.photoAlbumName}
                            className={styles.coverPhoto}
                        />
                    )}
                    
                    <p>{album.contentType}</p>
                    {album.publishedAt && (
                        <p>{new Date(album.publishedAt).toLocaleDateString()}</p>
                    )}
                </div>    
            ))}
        </div>
    </div>
    )

}

export default ContributorAlbums;