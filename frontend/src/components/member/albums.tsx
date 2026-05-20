"use client"

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { GetPhotoAlbumsResponse, memberGetAlbums } from "@/lib/api/memberGetPhotoAlbums";
import styles from "./albums.module.css"

function MemberContentAlbums (){

    const params = useParams();
    const albumUuid = params.albumUuid as string;
    const [data, setData] = useState<GetPhotoAlbumsResponse | null>(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        async function loadAlbum() {
            try {
                const response =
                await memberGetAlbums(albumUuid);
                setData(response);

            }catch (err) {
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
                <p>{data.description}</p>
                <p>By: {data.username}</p>
            </div>

            <div className={styles.grid}>

                {data.photos.map((photo) => (

                    <div
                        key={photo.publicUuid}
                        className={styles.card}
                    >
                        <img
                            src={`${process.env.NEXT_PUBLIC_API_URL}/${photo.photoUrl}`}
                            alt={data.photoAlbumName}
                            className={styles.photo}
                        />
                    </div>

                ))}

            </div>

        </div>
    );

}
export default MemberContentAlbums;