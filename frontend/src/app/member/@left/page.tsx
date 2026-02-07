"use client";

import { useEffect, useState } from "react";
import styles from "./page.module.css"

export default function MemberLeftSidebar() {

    const [username, setUsername] = useState<string | null>(null);
    const [error, setError] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/user/username", { 
            credentials: "include",
        })
            .then((res) => {
                if(!res.ok) throw new Error("Unauthorized");
                return res.json();
            })
            .then((data) => setUsername(data.username))
            .catch(() => setError("Not logged in"));
    },[]);

  return (
  <div>{username ? (
    <div className={styles.wbox}>
        <p className={styles.welcome}> Welcome</p>
        <p className={styles.username}>{username}</p>
    </div>
  ) : error ? (
    <p>{error}</p>
  ) : (
    <p>Loading ...</p>
  )}
  </div>
  );
}
