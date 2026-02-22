"use client";

import { useEffect, useState } from "react";
import { ContributorMeResponse, getContributorInfo } from "@/lib/api/contributor";

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

     <div>
            <div>Username: {data.username}</div>
            <div>Signed up: {data.yearSignedUp}</div>

            {data.countPhotoAlbums !== null && (
                <div>Albums: {data.countPhotoAlbums}</div>
            )}

            {data.message && (
                <div>{data.message}</div>
            )}
        </div>
    )    
}
