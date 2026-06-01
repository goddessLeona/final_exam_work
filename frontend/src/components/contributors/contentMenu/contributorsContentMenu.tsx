"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { 
            getPhotoAlbums,
            ContributorPhotoAlbumResponse,
            PageResponse,
        } from "@/lib/api/contributorsPhotoAlbums";

import type {ContentStatus} from "@/lib/api/contributorsPhotoAlbums"; 

import styles from "./contributorsContentMenu.module.css"

function ContributorAlbums() {

    const [data, setData] = useState<PageResponse<ContributorPhotoAlbumResponse> | null> (null);
    const [error, setError] = useState("");
    const [status, setStatus] = useState<ContentStatus |null>(null);

    useEffect(() => {

        if (!status) return;

        const currentStatus = status;

        async function loadAlbums() {

            try {
                const response = await getPhotoAlbums(currentStatus);
                setData(response);

            }catch (err) {
                
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

                <Link
                    key={album.publicUuid}
                    href={`/contributor/albums/${album.publicUuid}`}
                    className={styles.cardLink}
                >

                    <div
                    className={styles.card}
                    >
                        <h3>{album.photoAlbumName}</h3>
                        {album.coverPhoto && (
                            <img
                                src={`${process.env.NEXT_PUBLIC_API_URL}/${album.coverPhoto.coverPhotoUrl}`}
                                alt={album.photoAlbumName}
                                className={styles.coverPhoto}
                            />
                        )}
                        
                        <h3>{album.contentType}</h3>
                        {album.publishedAt && (
                            <h3>{new Date(album.publishedAt).toLocaleDateString()}</h3>
                        )}
                    </div>  
                </Link>  
            ))}
        </div>
    </div>
    )

}

export default ContributorAlbums;