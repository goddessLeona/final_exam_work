"use client";

import { useEffect, useState } from "react";
import { ContributorAlbumStatsResponse, getContributorInfo } from "@/lib/api/contributors/contributorGeneralInfo";
import { ContentType } from "@/lib/api/types/content-types";

import styles from "./infoBox.module.css"

function InfoBoxStats() {

    const [data, setData] = useState<ContributorAlbumStatsResponse | null > (null);
    const [error, setError] = useState("");

    useEffect(() => {
        getContributorInfo()
            .then((res) => setData(res))
            .catch(() => setError("Not logged in"));
    }, []);

    if (error) return <p>{error}</p>;
    if (!data) return <p>Loading...</p>;

    const contentTypeLabel: Record<ContentType, string> = {
        PHOTO: "Photo albums",
        VIDEO: "Video albums",
    };

    return(

        <div className={styles.info}>

            <div className={styles.name}>
                <div>
                    <div className={styles.user}>
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