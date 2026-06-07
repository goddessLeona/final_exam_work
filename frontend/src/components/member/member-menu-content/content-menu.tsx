"use client";

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import Link from "next/link";
import { memberGetCoverPhotos, GetCoverPhotoAlbumsResponse, PageResponse } from "@/lib/api/members/memberGetPhotoAlbums";
import { ContentType } from "@/types/content-type";
import { Inter, Finger_Paint } from "next/font/google"
import styles from "./content-menu.module.css"

const inter = Inter({
        subsets: ["latin"],
        weight: ["400"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

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

                    if (handleAuthError(err)) return;

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

            <div>
                <p className={`${fingerPaint.className} ${styles.title}`}>
                    {status ?? "Select category"}
                </p>
            </div>    

        <div className={styles.grid}>
            {data?.content.map((album) => (

                <Link
                    key={album.publicUuid}
                    href={`/member/albums/${album.publicUuid}`}
                    className={styles.cardLink}
                >

                    <div className={styles.card}>

                        <h3>{album.photoAlbumName}</h3>
                        
                        {album.coverPhoto && (
                            <img
                                src={`${process.env.NEXT_PUBLIC_API_URL}/${album.coverPhoto.coverPhotoUrl}`}
                                alt={album.photoAlbumName}
                                className={styles.coverPhoto}
                            />
                        )}
                        
                    </div>  
                </Link>
            ))}
        </div>

        </div>
    )

}
export default MemberMenuContent