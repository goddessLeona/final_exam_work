"use client";

import { useEffect, useState } from "react";
import { ContributorMeResponse, getContributorInfo } from "@/lib/api/contributor";
import styles from "./page.module.css"

export default function ContributorPage() {
    const [data, setData] = useState<ContributorMeResponse | null > (null);
    const [error, setError] = useState("");

    useEffect(() => {
        getContributorInfo()
            .then((res) => setData(res))
            .catch(() => setError("Not logged in"));
    }, []);

    if (error) return <p>{error}</p>;
    if (!data) return <p>Loading...</p>;

    return(

     <div className={styles.info}>
            <div>
                <div className={styles.user}>{data.username}</div>
            </div>    
            <div className = {styles.year}>Signed up: {data.yearSignedUp}</div>

            {data.countPhotoAlbums !== null && (
                <div className={styles.album}> Photo-albums: {data.countPhotoAlbums}</div>
            )}
        </div>
    )    
}
