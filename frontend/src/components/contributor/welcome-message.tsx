"use client";

import ContributorAgrementForm from "@/components/forms/contributorAgrementForm";

import { useEffect, useState } from "react";
import { getWelcomeMessage, WelcomeResponse} from "@/lib/api/contributor";
import styles from "./welcome-message.module.css"

export default function Welcome() {
    const [data, setData] = useState<WelcomeResponse | null>(null);
    const [error, setError] = useState("");

    useEffect(() => {
        getWelcomeMessage()
            .then(setData)
            .catch(() => setError("Not logged in"));
    }, []);

    if (error) return <p>{error}</p>;
    if (!data) return <p>Loading...</p>;

    return  <div className={styles.container}>
                <p>{data.message}</p>
            </div>;
}