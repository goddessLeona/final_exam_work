"use client";

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import { getWelcomeMessage, WelcomeResponse} from "@/lib/api/contributors/contributor-consent-form";
import styles from "./welcome-message.module.css"

function Welcome() {
    const [data, setData] = useState<WelcomeResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        getWelcomeMessage()
            .then(setData)
            .catch((err) => {
                if (handleAuthError(err)) return;
                setError(err.message);
            })
            .finally(() => setLoading(false));
    }, []);

    if(loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;
    if (!data) return null;

    return (
        <div className={styles.main}>
            <div className={styles.container}>
                    <p className={styles.text}>{data.message}</p>
            </div>
        </div>
    );
    
}

export default Welcome;