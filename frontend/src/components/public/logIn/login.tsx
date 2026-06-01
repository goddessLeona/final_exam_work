"use client"

import { useState } from "react";
import { useRouter } from "next/navigation"
import { Inter, Finger_Paint } from "next/font/google"
import { login } from "@/lib/api/login";
import styles from "./login.module.css"

const inter = Inter({
        subsets: ["latin"],
        weight: ["400"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

export default function LogIn() {
    const router = useRouter();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        setError("");

        try {
            const data = await login({ username, password });

            if (data.roles.includes("ROLE_ADMIN")) {
                router.push("/admin");
            } else if (data.roles.includes("ROLE_CONTRIBUTOR")) {
                router.push("/contributor");
            } else if (data.roles.includes("ROLE_MEMBER")) {
                router.push("/member");
            } else {
                setError("No role assigned");
            }
        } catch {
            setError("Login failed");
        }
    };

    return (
        <main className={styles.container}>

            <p className={styles.thanks}>
                Happy to see you back!
            </p>

            <form onSubmit={handleSubmit}>
                <div className={styles.formBox}>

                    <p className={fingerPaint.className}> 
                        LOG IN
                    </p>

                    <div className={styles.field}>
                        <label>Username</label>
                        <input
                            value={username} 
                            onChange={(e) => {
                                setUsername(e.target.value);
                                setError("")
                            }}
                        />
                    </div>

                    <div className={styles.field}>
                        <label>Password</label>
                        <input 
                            type="password" 
                            value={password} 
                            onChange={(e) => { 
                                setPassword(e.target.value);
                                setError("");
                            }}
                        />
                    </div>

                    {error && <p className={styles.error}>{error}</p>}

                    <button className={styles.btn} type="submit">
                        Log in
                    </button>
                </div>
            </form>
        </main>
    );

}
