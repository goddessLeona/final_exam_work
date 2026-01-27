"use client";

import { useEffect, useState } from "react";

export default function MemberPage(){
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        if(!token){
            setError("No token found. Please log in.");
            return;
        }

        fetch("http://localhost:8080/member/test", {
            credentials: "include"
        })

        .then((res) => {
            if(!res.ok){
                throw new Error("Unauthorized");
            }
            return res.text();
        })

        .then((data) => setMessage(data))
        .catch(() => setError("Access denied"));
    }, []);

    return(
        <main style={{ padding: "2rem" }}>
            <h1> Member page</h1>

            {message && <p>{message}</p>}
            {error && <p style = {{ color: "red" }}>{error}</p>}
        </main>
    );

}