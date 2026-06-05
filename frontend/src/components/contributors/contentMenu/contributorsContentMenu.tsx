"use client";

import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { handleAuthError } from "@/lib/auth/handleAuthError";

import { Inter, Finger_Paint } from "next/font/google"

import { 
            getPhotoAlbums,
            ContributorPhotoAlbumResponse,
            PageResponse,
        } from "@/lib/api/contributors/contributorsGetPhotoAlbums";
 
import type { ContentStatus } from "@/types/content-status";

import styles from "./contributorsContentMenu.module.css"

const inter = Inter({
        subsets: ["latin"],
        weight: ["600"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

type Props = {};

function ContributorAlbumsMenu({
   
}: Props) {

    const [data, setData] = useState<PageResponse<ContributorPhotoAlbumResponse> | null> (null);
    const [error, setError] = useState("");
    const [status, setStatus] = useState<ContentStatus |null>(null);
    const router = useRouter();

    const pathname = usePathname();
    
    const isAlbumPage = pathname.startsWith("/contributor/albums/");
    const showGrid = !isAlbumPage;
    

    useEffect(() => {

        if (!status) return;

        const currentStatus = status;

        async function loadAlbums() {

            try {
                const response = await getPhotoAlbums(currentStatus);
                setData(response);

            }catch (err: any) {
                 
                if (handleAuthError(err)) return;
                setError(err.message || "Failed to load albums");
            }
            
        }
        loadAlbums()

    }, [status]);

    return (
        <div className={styles.bigContainer}>
            
            <div className={styles.titleBox}>
                
                <p className={`${fingerPaint.className} ${styles.title}`}>Your uploaded content</p>
                
            </div>
            
            <div className={styles.container}>
                
                <div className={styles.tabs}>

                    <button
                        type="button"
                        className={`${styles.btn} ${
                            status === "DRAFT" ? styles.activeTab : ""
                        }`}
                        onClick={() => {
                            setStatus("DRAFT");
                            setData(null);
                        }}
                    >
                        Draft
                    </button>

                    <button
                        type="button"
                        className={`${styles.btn} ${
                            status === "PUBLISHED" ? styles.activeTab : ""
                        }`}
                        onClick={() => {
                            setStatus("PUBLISHED");
                        }}
                    >
                        Published
                    </button>

                    <button
                        type="button"
                        className={`${styles.btn} ${
                            status === "SCHEDULED" ? styles.activeTab : ""
                        }`}
                        onClick={() => {
                            setStatus("SCHEDULED");
                        }}
                    >
                        Scheduled
                    </button>

                    <button
                        type="button"
                        className={`${styles.btn} ${
                            status === "ARCHIVED" ? styles.activeTab : ""
                        }`}
                        onClick={() => {
                            setStatus("ARCHIVED");
                        }}
                    >
                        Archived
                    </button>

                </div>
                <p className={styles.currentTab}>
                    {status ?? "Select category"}
                </p>
                {error && <p>{error}</p>}

                <div className={styles.grid}>
                    {data?.content.map((album) => (

                        <div
                            key={album.publicUuid}
                            className={styles.cardLink}
                            onClick={() => {
                                router.push(`/contributor/albums/${album.publicUuid}`);
                            }}
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
                        </div>  
                    ))}
                </div>
            </div>
        </div>    
    )

}

export default ContributorAlbumsMenu;