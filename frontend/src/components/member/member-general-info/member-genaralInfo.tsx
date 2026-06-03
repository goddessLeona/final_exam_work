"use client";

import { useEffect, useState } from "react";
import { handleAuthError } from "@/lib/auth/handleAuthError";
import { getGeneralInfo, memberResponse} from "@/lib/api/members/memberGeneralInfo";
import styles from "./member-GeneralInfo.module.css"

function GetMemberInfo() {

    const [data, setData] = useState<memberResponse | null > (null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        getGeneralInfo()
            .then((res) => {
                setData(res);
            })
            .catch((err) => {

                if (handleAuthError(err)) return;

                setError(err.message || "Something went wrong");
            })
            .finally(() => {
                setLoading(false);
            });

    }, []);

    if (loading) return <p>Loading...</p>
    if (error) return <p>{error}</p>;
    if (!data) return null;

    return (
        <div className={styles.wbox}>
            <p className={styles.welcome}>Welcome</p>
            <p className={styles.username}>{data.username}</p>
        </div>
    );
}

export default GetMemberInfo;

