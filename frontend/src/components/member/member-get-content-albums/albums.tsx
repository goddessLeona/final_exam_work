"use client"

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import { useParams } from "next/navigation";
import { GetPhotoAlbumsResponse, memberGetAlbums } from "@/lib/api/members/memberGetPhotoAlbums";
import styles from "./albums.module.css"

function MemberContentAlbums (){

    const params = useParams();
    const albumUuid = params.albumUuid as string;
    const [data, setData] = useState<GetPhotoAlbumsResponse | null>(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);

    useEffect(() => {

        async function loadAlbum() {
            try {
                const response =
                await memberGetAlbums(albumUuid);
                setData(response);

            }catch (err) {
                if (handleAuthError(err)) return;
                setError("Failed to load album");

            }finally {
                setLoading(false);
            }
        }

        if (albumUuid) {
            loadAlbum();
        }

    }, [albumUuid]);

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>{error}</p>
    }

    if (!data) {
        return <p>Album not found</p>;
    }

    return (
        <div className={styles.container}>

            <div className={styles.header}>
                <h1>{data.photoAlbumName}</h1>
                <p className={styles.p}>{data.description}</p>
                <p>By: {data.username}</p>
                <p>Published:  {new Date(data.publishedAt).toLocaleDateString("en-GB")}</p>
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

            <p>Published:  {new Date(data.publishedAt).toLocaleDateString("en-GB")}</p>

            {selectedIndex !== null && (
    <div className={styles.lightbox}>

        <img
            src={`${process.env.NEXT_PUBLIC_API_URL}/${data.photos[selectedIndex].photoUrl}`}
            alt="full view"
            className={styles.lightboxImage}
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

        </div>
    );

}
export default MemberContentAlbums;