"use client";

import { useEffect, useState } from "react";
import { memberGetCoverPhotos, GetCoverPhotoAlbumsResponse, PageResponse } from "@/lib/api/memberGetPhotoAlbums";
import type {ContentType} from "@/lib/api/memberGetPhotoAlbums";
import styles from "./content-menu.module.css"

function MemberMenuContent(){

    const [data, setData] = useState<PageResponse<GetCoverPhotoAlbumsResponse> | null > (null);
    const [error, setError] = useState("");
    const [status, setStatus] = useState<ContentType |null>(null);

    useEffect(() => {
    
            if (!status) return;
    
            const currentStatus = status;
    
            async function loadAlbums() {
    
                try {
                    const response = await memberGetCoverPhotos(currentStatus);
                    setData(response);
    
                }catch (err) {
                    
                    setError("Failed to load albums");
                }
                
            }
            loadAlbums()
        }, [status]);

    return (
        <div className={styles.container}>
            <div className={styles.menu}>

                <button
                    type="button"
                    className={styles.btn}
                    onClick={() => setStatus("PHOTO")}
                >
                    PHOTO
                </button>

                <button
                    type="button"
                    className={styles.btn}
                    onClick={() => setStatus("VIDEO")}
                >
                    VIDEO
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    LATEST
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    NEWS
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    CONTRIBUTORS
                </button>

            </div>

            {error && <p>{error}</p>}

        <div className={styles.grid}>
            {data?.content.map((album) => (
                <div
                key={album.publicUuid}
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
                    
                </div>    
            ))}
        </div>

        </div>
    )

}
export default MemberMenuContent