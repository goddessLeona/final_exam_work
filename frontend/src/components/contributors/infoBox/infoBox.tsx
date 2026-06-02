"use client";

import { useEffect, useState } from "react";
import { Inter, Finger_Paint } from "next/font/google"
import { ContributorAlbumStatsResponse, getContributorInfo } from "@/lib/api/contributors/contributorGeneralInfo";
import { ContentType } from "@/types/content-type";

import styles from "./infoBox.module.css"

const inter = Inter({
        subsets: ["latin"],
        weight: ["400"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

function InfoBoxStats() {

    const [data, setData] = useState<ContributorAlbumStatsResponse | null > (null);
    const [loading, setLoading] = useState(true);
    const [error] = useState("");

    useEffect(() => {
        getContributorInfo()
            .then((res) => setData(res))
            .catch(() => {})
            .finally(() => setLoading(false))
            
    }, []);

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>
    if (!data) return null;

    const contentTypeLabel: Record<ContentType, string> = {
        PHOTO: "Photo albums",
        VIDEO: "Video albums",
    };

    return(

        <div className={styles.info}>

            <div className={styles.name}>
                <div>
                    <div className= {`${fingerPaint.className} ${styles.title}`}>
                        {data.username}
                    </div>
                </div>

                <div className={styles.year}>
                    Signed up: {data.yearSignedUp}
                </div>
            </div>    

            {data.content?.map((item) => (

                <div key={item.type} className={styles.album}>

                    <div className={styles.type}>
                        {contentTypeLabel[item.type]}: {item.total}
                    </div>

                    <div className={styles.stats}>
                        <span>Published: {item.published}</span>
                        <span>Draft: {item.draft}</span>
                        <span>Archived: {item.archived}</span>
                        <span>Scheduled: {item.scheduled}</span>
                    </div>

                </div>
            ))}

        </div>

    )    
}
export default InfoBoxStats;