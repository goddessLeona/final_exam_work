"use client"

import { useState } from "react";
import { useRouter } from "next/navigation"
import styles from "./page.module.css";

export default function LogInPage() {

    const router = useRouter();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) =>{
        e.preventDefault();
        setError("");
    

        try{
            const res = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({username, password}),
                credentials: 'include',
            });

            if(!res.ok){
                throw new Error("Invalid credentials");
            }

            const data = await res.json();

            //role-based redirection
            if(data.roles.includes("ROLE_ADMIN")){
                router.push("/admin");
            }else if (data.roles.includes("ROLE_CONTRIBUTOR")){
                router.push("/contributor");
            }else if(data.roles.includes("ROLE_MEMBER")){
                router.push("/member")
            }else{
                setError("No role assigned");
            }
        } catch (err) {
            setError("Login failed");
        }
    };

  return (
    <main className={styles.container}>
        <p>Happy to see you back !</p>

        <form onSubmit={handleSubmit}>
            <div className={styles.formBox}>
                <div className={styles.field}>
                    <label htmlFor="username">Username</label>
                    <input
                        id="username"
                        name="username"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        //placeholder="Enter your username"
                        required
                    />
                </div>
                <div className={styles.field}>
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        //placeholder="Enter your pasword"
                        required
                    />
                </div>

                {error && <p className={styles.error}>{error}</p>}

                <button type="submit" className={styles.btn}>Log in</button>
            </div>
        </form>
        
    </main>
  );
}